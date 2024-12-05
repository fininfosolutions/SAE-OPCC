package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.TransactionType;
import com.fininfo.saeopcc.shared.domains.AccountTypeSLA;
import com.fininfo.saeopcc.shared.repositories.AccountTypeSLARepository;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountTypeSLAService {

  @Autowired private AccountTypeSLARepository accountTypeSLARepository;

  public AccountTypeSLA getAccountTypeSLAByOperationType(TransactionType transactionType) {
    Optional<AccountTypeSLA> optional =
        accountTypeSLARepository.findByTransactionType(transactionType);
    return optional.orElse(null);
  }
}
