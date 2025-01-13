package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.SecPosition;
import com.fininfo.saeopcc.multitenancy.domains.SecuritiesAccount;
import com.fininfo.saeopcc.multitenancy.repositories.SecPositionRepository;
import com.fininfo.saeopcc.multitenancy.repositories.SecuritiesAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.SecPositionDTO;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecPositionService {
  @Autowired private SecPositionRepository secpositionRepository;
  @Autowired private ModelMapper modelMapper;
  @Autowired private SecuritiesAccountRepository secAccountRepository;

  public Boolean findSecPositionQuantity(
      Long assetId, BigDecimal transQuantity, Long accountId, Integer transDirection) {
    Optional<SecPosition> mappOpt;

    mappOpt = secpositionRepository.findLastPosition(accountId, assetId);

    SecPosition mapping = (mappOpt.isPresent() ? mappOpt.get() : null);
    Boolean available = false;
    BigDecimal quantity = null;
    if (mapping == null) {
      return transDirection == 1;
    }
    quantity = mapping.getQuantity();
    if (transQuantity != null) {

      if (transQuantity.compareTo(quantity) <= 0) {

        available = true;
      }
    }

    return available;
  }

  public List<SecPositionDTO> getSecPositionByDateAndParameters(
      Long shareholderId,
      Long intermediaryId,
      Long accountId,
      LocalDate date,
      Boolean isValueDate,
      Long assetId) {

    List<SecPosition> positions;

    if (isValueDate) {
      positions =
          secpositionRepository.findSecPositionsByValueDateAndDynamicFilters(
              shareholderId, intermediaryId, accountId, assetId, date);
    } else {
      Instant positionDateEndOfDay =
          date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
      positions =
          secpositionRepository.findSecPositionsByPositionDateAndDynamicFilters(
              shareholderId, intermediaryId, accountId, assetId, positionDateEndOfDay);
    }

    return positions.stream()
        .map(
            position -> {
              SecPositionDTO dto = modelMapper.map(position, SecPositionDTO.class);
              if (dto.getAccountId() != null) {
                Optional<SecuritiesAccount> secAccountOpt =
                    secAccountRepository.findById(dto.getAccountId());
                secAccountOpt.ifPresent(
                    secAccount -> {
                      if (secAccount.getShareholder() != null) {
                        dto.setShareholderDescription(secAccount.getShareholder().getDescription());
                      }
                      if (secAccount.getAccountType() != null) {
                        dto.setAccountAccountType(secAccount.getAccountType());
                      }

                      if (secAccount.getIntermediary() != null) {
                        dto.setIntermediaryDescription(
                            secAccount.getIntermediary().getDescription());
                      }
                    });
              }
              return dto;
            })
        .sorted(Comparator.comparing(SecPositionDTO::getId).reversed())
        .collect(Collectors.toList());
  }

  // public SecPositionDTO findLastSecPosition(Long assetId, Long clientId, Long accountId) {
  //   Optional<SecPosition> lastPositionOpt =
  //       secpositionRepository.findLastPosition(assetId, clientId, accountId);

  //   return lastPositionOpt
  //       .map(
  //           position -> {
  //             SecPositionDTO dto = modelMapper.map(position, SecPositionDTO.class);
  //             return dto;
  //           })
  //       .orElse(null);
  // }
}
