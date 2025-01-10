package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallDTO;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CallService {
  @Autowired private ModelMapper modelMapper;

  @Autowired private CallRepository callRepository;
  @Autowired private LiberationRepository liberationRepository;
  @Autowired private MovementService movementService;
  @Autowired private GlobalLiberationRepository globalLiberationRepository;
  @Autowired private SecuritiesAccountRepository securitiesAccountRepository;

  @Transactional(readOnly = true)
  public Optional<CallDTO> findOne(Long id) {
    log.debug("Request to get Call : {}", id);
    return callRepository.findById(id).map(call -> modelMapper.map(call, CallDTO.class));
  }

  public CallDTO save(CallDTO callDTO) {
    Call call = modelMapper.map(callDTO, Call.class);

    Call savedCall = callRepository.save(call);
    return modelMapper.map(savedCall, CallDTO.class);
  }

  @Transactional
  public List<CallDTO> validatecalls(List<CallDTO> callDtos) {

    return callDtos.stream()
        .map(
            dto -> {
              Call call =
                  callRepository
                      .findById(dto.getId())
                      .orElseThrow(
                          () ->
                              new EntityNotFoundException("Call not found for id: " + dto.getId()));

              call.setStatus(CallStatus.VALIDATED);
              call = callRepository.save(call);

              movementService.handleMovementsAndPositionsfromcall(dto);

              List<GlobalLiberation> globalLiberations =
                  globalLiberationRepository.findByEventStatusAndCallEvent_Id(
                      EventStatus.VALIDATED, dto.getCallEventId());

              if (!globalLiberations.isEmpty()) {
                for (GlobalLiberation globalLib : globalLiberations) {

                  Liberation liberation = new Liberation();
                  liberation.setLiberationEvent(globalLib);
                  liberation.setCall(call);

                  BigDecimal globalPercentage =
                      Optional.ofNullable(globalLib.getPercentage()).orElse(BigDecimal.ZERO);

                  BigDecimal calledAmount =
                      Optional.ofNullable(call.getCalledAmount()).orElse(BigDecimal.ZERO);
                  BigDecimal calledQuantity =
                      Optional.ofNullable(call.getCalledQuantity()).orElse(BigDecimal.ZERO);

                  BigDecimal releasedAmount = calledAmount.multiply(globalPercentage);
                  BigDecimal releasedQuantity = calledQuantity.multiply(globalPercentage);

                  liberation.setPercentage(globalPercentage);
                  liberation.setReleasedAmount(releasedAmount);
                  liberation.setReleasedQuantity(releasedQuantity);
                  liberation.setRemainingAmount(calledAmount.subtract(releasedAmount));
                  liberation.setRemainingQuantity(calledQuantity.subtract(releasedQuantity));

                  liberation.setStatus(LiberationStatus.VALIDATED);
                  liberation.setLiberationDate(LocalDate.now());

                  SecuritiesAccount secaccount = call.getSecuritiesAccount();
                  if (secaccount != null) {
                    List<SecuritiesAccount> accounts =
                        securitiesAccountRepository
                            .findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountType(
                                secaccount.getAsset() != null
                                    ? secaccount.getAsset().getId()
                                    : null,
                                secaccount.getShareholder() != null
                                    ? secaccount.getShareholder().getId()
                                    : null,
                                secaccount.getIntermediary() != null
                                    ? secaccount.getIntermediary().getId()
                                    : null,
                                AccountType.LIBERE);

                    if (accounts == null || accounts.isEmpty()) {
                      liberation.setSecuritiesAccount(null);
                      liberation.setMessage("Compte titre inexistant");
                      liberation.setStatus(LiberationStatus.INCOMPLETE);
                      globalLib.setEventStatus(EventStatus.INCOMPLETE);
                      globalLiberationRepository.save(globalLib);
                    } else {
                      liberation.setSecuritiesAccount(accounts.get(0));
                      liberation.setStatus(LiberationStatus.VALIDATED);
                    }
                    liberation = liberationRepository.save(liberation);
                    if (liberation.getId() != null) {
                      liberation.setReference(globalLib.getReference() + "-" + liberation.getId());
                      liberation = liberationRepository.save(liberation);
                    }
                  }
                }
              }

              return modelMapper.map(call, CallDTO.class);
            })
        .collect(Collectors.toList());
  }
}
