package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Compartement;
import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.repositories.CompartementRepository;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartementDTO;
import com.fininfo.saeopcc.shared.domains.Fund;
import com.fininfo.saeopcc.shared.domains.enumeration.UnitCategory;
import com.fininfo.saeopcc.shared.services.dto.FundDTO;
import com.fininfo.saeopcc.util.TenantContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

@Slf4j
@Service
public class CompartementService {

  @Autowired private CompartementRepository compartementRepository;
  @Autowired private IssueAccountRepository issueaccountRepository;
  @Autowired private ModelMapper modelMapper;

  public Compartement syncCompartement(Compartement compartement, String tenant) {

    Compartement synced = null;
    TenantContext.setTenantId(tenant);
    if (compartement.getId() != null) {
      synced = compartementRepository.save(compartement);

    } else log.error("Synchronize Compartement Process Cannot Execute without ID !!!");

    return synced;
  }
    public Page<CompartementDTO> findcompartementswithoutissueaccount(Pageable pageable) {
    List<Long> compartimentIds =
        issueaccountRepository.findAll().stream()
            .map(IssueAccount::getCompartement)
            .map(Compartement::getId)
            .collect(Collectors.toList());

    return compartimentIds.isEmpty()
        ? compartementRepository
            .findAll(pageable)
            .map(x -> modelMapper.map(x, CompartementDTO.class))
        : compartementRepository
            .findByIdNotIn(
                compartimentIds, pageable)
            .map(x -> modelMapper.map(x, CompartementDTO.class));
  }

}
