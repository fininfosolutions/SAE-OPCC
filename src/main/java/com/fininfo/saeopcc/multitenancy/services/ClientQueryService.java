package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.configuration.QueryService;
import com.fininfo.saeopcc.multitenancy.domains.Client;
import com.fininfo.saeopcc.multitenancy.domains.Client_;
import com.fininfo.saeopcc.multitenancy.repositories.ClientRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientDTO;
import com.fininfo.saeopcc.shared.domains.Role_;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ClientQueryService extends QueryService<Client> {

  @Autowired(required = false)
  private ClientRepository clientRepository;

  @Autowired(required = false)
  private ModelMapper modelMapper;

  @Transactional(readOnly = true)
  public List<ClientDTO> findByCriteria(ClientCriteria criteria) {
    log.debug("find by criteria : {}", criteria);
    final Specification<Client> specification = createSpecification(criteria);
    List<Client> entities = clientRepository.findAll(specification);
    return entities.stream()
        .map(entity -> modelMapper.map(entity, ClientDTO.class))
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public Page<ClientDTO> findByCriteria(ClientCriteria criteria, Pageable page) {
    log.debug("find by criteria : {}, page: {}", criteria, page);
    final Specification<Client> specification = createSpecification(criteria);
    return clientRepository
        .findAll(specification, page)
        .map(entity -> modelMapper.map(entity, ClientDTO.class));
  }

  @Transactional(readOnly = true)
  public long countByCriteria(ClientCriteria criteria) {
    log.debug("count by criteria : {}", criteria);
    final Specification<Client> specification = createSpecification(criteria);
    return clientRepository.count(specification);
  }

  protected Specification<Client> createSpecification(ClientCriteria criteria) {
    Specification<Client> specification = Specification.where(null);

    if (criteria != null) {
      if (criteria.getId() != null) {
        specification = specification.and(buildRangeSpecification(criteria.getId(), Client_.id));
      }
      if (criteria.getMandateManager() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getMandateManager(), Client_.mandateManager));
      }
      if (criteria.getDescription() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getDescription(), Client_.description));
      }

      if (criteria.getMandateManagerFax() != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getMandateManagerFax(), Client_.mandateManagerFax));
      }
      if (criteria.getMandateManagerTel() != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getMandateManagerTel(), Client_.mandateManagerTel));
      }
      if (criteria.getMandateManagerMail() != null) {
        specification =
            specification.and(
                buildStringSpecification(
                    criteria.getMandateManagerMail(), Client_.mandateManagerMail));
      }
      if (criteria.getEbanking() != null) {
        specification =
            specification.and(buildSpecification(criteria.getEbanking(), Client_.ebanking));
      }
      if (criteria.getExternalReference() != null) {
        specification =
            specification.and(
                buildStringSpecification(criteria.getExternalReference(), Role_.externalReference));
      }
      if (criteria.getBankDomiciliation() != null) {
        specification =
            specification.and(
                buildStringSpecification(
                    criteria.getBankDomiciliation(), Client_.bankDomiciliation));
      }
      if (criteria.getSubjectTax() != null) {
        specification =
            specification.and(buildSpecification(criteria.getSubjectTax(), Client_.subjectTax));
      }
      if (criteria.getClientType() != null) {
        specification =
            specification.and(buildSpecification(criteria.getClientType(), Client_.clientType));
      }
      if (criteria.getCustomerAnalyticsSegment() != null) {
        specification =
            specification.and(
                buildRangeSpecification(
                    criteria.getCustomerAnalyticsSegment(), Client_.customerAnalyticsSegment));
      }
      if (criteria.getFiscalStatus() != null) {
        specification =
            specification.and(buildSpecification(criteria.getFiscalStatus(), Client_.fiscalStatus));
      }

      if (criteria.getIsFund() != null) {
        specification = specification.and(buildSpecification(criteria.getIsFund(), Client_.isfund));
      }
    }
    return specification;
  }
}
