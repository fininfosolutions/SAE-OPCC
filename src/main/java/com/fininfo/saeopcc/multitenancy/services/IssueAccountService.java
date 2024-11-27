package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.IssueAccount;
import com.fininfo.saeopcc.multitenancy.repositories.IssueAccountRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.IssueAccountDTO;
import com.fininfo.saeopcc.shared.repositories.AssetRepository;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueAccountService {

  @Autowired private IssueAccountRepository issueAccountDTORepository;
  @Autowired private AssetRepository assetRepository;

  @Autowired private ModelMapper modelMapper;

  public IssueAccountDTO save(IssueAccountDTO issueAccountDTO) {
    IssueAccount issueAccount = modelMapper.map(issueAccountDTO, IssueAccount.class);

    IssueAccount savedIssueAccount = issueAccountDTORepository.save(issueAccount);
    return modelMapper.map(savedIssueAccount, IssueAccountDTO.class);
  }

  public Optional<IssueAccountDTO> findOne(Long id) {
    return issueAccountDTORepository
        .findById(id)
        .map(issueAccount -> modelMapper.map(issueAccount, IssueAccountDTO.class));
  }
}
