package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.domains.CallEvent;
import com.fininfo.saeopcc.multitenancy.domains.GlobalLiberation;
import com.fininfo.saeopcc.multitenancy.domains.Liberation;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.CallStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.EventStatus;
import com.fininfo.saeopcc.multitenancy.domains.enumeration.LiberationStatus;
import com.fininfo.saeopcc.multitenancy.repositories.CallEventRepository;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.repositories.GlobalLiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.LiberationRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.GlobalLiberationDTO;
import com.fininfo.saeopcc.shared.controllers.errors.BadRequestAlertException;
import com.fininfo.saeopcc.shared.domains.enumeration.AccountType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class GlobalLiberationService {
  @Autowired private ModelMapper modelMapper;
  @Autowired private CallEventRepository callEventRepository;
  @Autowired private GlobalLiberationRepository globalLiberationRepository;
  @Autowired private CallRepository callRepository;
  @Autowired private LiberationRepository liberationRepository;

  @Autowired private SecuritiesAccountRepository securitiesAccountRepository;

  @Transactional
  public List<GlobalLiberationDTO> saveAll(List<GlobalLiberationDTO> globalLiberationDTOs) {
    List<GlobalLiberationDTO> results = new ArrayList<>();

    for (GlobalLiberationDTO dto : globalLiberationDTOs) {
      GlobalLiberation globalLiberation = modelMapper.map(dto, GlobalLiberation.class);

      globalLiberation.setEventStatus(EventStatus.PREVALIDATED);

      CallEvent callEvent =
          callEventRepository
              .findById(dto.getCallEventId())
              .orElseThrow(
                  () ->
                      new BadRequestAlertException("CallEvent not found", "callEvent", "notfound"));
      callEvent.setLiberated(true);
      callEventRepository.save(callEvent);

      globalLiberation = globalLiberationRepository.save(globalLiberation);

      if (globalLiberation.getId() != null) {
        String reference = callEvent.getReference() + "-GL" + globalLiberation.getId();
        globalLiberation.setReference(reference);

        globalLiberation = globalLiberationRepository.save(globalLiberation);
      }

      GlobalLiberationDTO globallibDTOResult =
          modelMapper.map(globalLiberation, GlobalLiberationDTO.class);
      results.add(globallibDTOResult);
    }

    return results;
  }

  public GlobalLiberationDTO updateGlobalLiberation(GlobalLiberationDTO globalLiberationDTO) {
    GlobalLiberation globallib = modelMapper.map(globalLiberationDTO, GlobalLiberation.class);
    return modelMapper.map(globalLiberationRepository.save(globallib), GlobalLiberationDTO.class);
  }

  @Transactional
  public List<GlobalLiberationDTO> validateGlobalLiberations(
      List<GlobalLiberationDTO> globalliberationDTOs) {
    List<GlobalLiberationDTO> validatedGlobalLiberations = new ArrayList<>();

    for (GlobalLiberationDTO globallibDTO : globalliberationDTOs) {
      GlobalLiberation globalLib =
          globalLiberationRepository
              .findById(globallibDTO.getId())
              .orElseThrow(
                  () ->
                      new EntityNotFoundException(
                          "Global Liberation not found for id: " + globallibDTO.getId()));

      globalLib.setEventStatus(EventStatus.VALIDATED);

      globalLib = globalLiberationRepository.save(globalLib);

      GlobalLiberationDTO updatedDTO =
          createliberationsforgloballiberation(
              modelMapper.map(globalLib, GlobalLiberationDTO.class));

      validatedGlobalLiberations.add(updatedDTO);
    }

    return validatedGlobalLiberations;
  }

  public GlobalLiberationDTO createliberationsforgloballiberation(
      GlobalLiberationDTO globallibDto) {
    GlobalLiberation globalLiberation =
        globalLiberationRepository
            .findById(globallibDto.getId())
            .orElseThrow(
                () ->
                    new EntityNotFoundException(
                        "Global Liberation not found for id: " + globallibDto.getId()));
    List<Call> calls =
        callRepository.findByCallEvent_IdAndStatus(
            globalLiberation.getCallEvent().getId(), CallStatus.VALIDATED);

    boolean atLeastOneIncomplete = false;

    for (Call call : calls) {
      Liberation liberation = new Liberation();

      liberation.setCall(call);
      liberation.setGlobalLiberation(globalLiberation);

      BigDecimal percentage =
          globalLiberation.getPercentage() != null
              ? globalLiberation.getPercentage()
              : BigDecimal.ZERO;
      liberation.setPercentage(percentage);

      BigDecimal subAmount =
          call.getCalledAmount() != null ? call.getCalledAmount() : BigDecimal.ZERO;
      BigDecimal calledAmount = percentage.multiply(subAmount);
      liberation.setReleasedAmount(calledAmount);
      BigDecimal subQuantity =
          call.getCalledQuantity() != null ? call.getCalledQuantity() : BigDecimal.ZERO;
      BigDecimal calledQuantity = percentage.multiply(subQuantity);
      liberation.setReleasedQuantity(calledQuantity);
      BigDecimal remainingAmount = subAmount.subtract(calledAmount);
      liberation.setRemainingAmount(remainingAmount);
      BigDecimal remainingQuantity = subQuantity.subtract(calledQuantity);
      liberation.setRemainingQuantity(remainingQuantity);
      liberation.setDescription(globalLiberation.getDescription());
      liberation.setLiberationDate(LocalDate.now());
      SecuritiesAccount secaccount = call.getSecuritiesAccount();

      List<SecuritiesAccount> accounts =
          securitiesAccountRepository
              .findByAsset_IdAndShareholder_IdAndIntermediary_IdAndAccountType(
                  secaccount.getAsset() != null ? secaccount.getAsset().getId() : null,
                  secaccount.getShareholder() != null ? secaccount.getShareholder().getId() : null,
                  secaccount.getIntermediary() != null
                      ? secaccount.getIntermediary().getId()
                      : null,
                  AccountType.LIBERE);

      if (accounts == null || accounts.isEmpty()) {
        liberation.setSecuritiesAccount(null);
        liberation.setMessage("Compte titre inexistant");
        liberation.setStatus(LiberationStatus.INCOMPLETE);
        globalLiberation.setEventStatus(EventStatus.INCOMPLETE);
        atLeastOneIncomplete = true;
      } else {
        liberation.setSecuritiesAccount(accounts.get(0));
        liberation.setStatus(LiberationStatus.PREVALIDATED);
      }
      liberation = liberationRepository.save(liberation);
      if (liberation.getId() != null) {
        liberation.setReference(globalLiberation.getReference() + "-" + liberation.getId());
        liberation = liberationRepository.save(liberation);
      }
    }
    if (!atLeastOneIncomplete) {
      globalLiberation.setEventStatus(EventStatus.VALIDATED);
    }

    GlobalLiberation savedgloballib = globalLiberationRepository.save(globalLiberation);
    return modelMapper.map(savedgloballib, GlobalLiberationDTO.class);
  }

  @Transactional(readOnly = true)
  public Optional<GlobalLiberationDTO> findOne(Long id) {
    log.debug("Request to get GlobalLiberation : {}", id);
    return globalLiberationRepository
        .findById(id)
        .map(globallib -> modelMapper.map(globallib, GlobalLiberationDTO.class));
  }
}
