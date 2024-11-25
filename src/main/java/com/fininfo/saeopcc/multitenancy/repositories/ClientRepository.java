package com.fininfo.saeopcc.multitenancy.repositories;

import com.fininfo.saeopcc.multitenancy.domains.Client;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ClientRepository
    extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
  Client getClientByExternalReference(String s);

  Optional<Client> findOneById(Long id);

  @Query(
      "SELECT c FROM Client c JOIN c.clientCategory cc WHERE c.isfund = true AND cc.description = 'saeopcc'")
  List<Client> findClientsByIsFundAndClientCategoryDescription();
}
