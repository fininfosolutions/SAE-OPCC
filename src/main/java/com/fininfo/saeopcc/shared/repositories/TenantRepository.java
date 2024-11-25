package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Tenant;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, String> {

  @Query(value = "select * from tenant t where t.issuer = ?1 limit 1", nativeQuery = true)
  Optional<Tenant> findByIssuer(String issuer);

  Optional<Tenant> findBySchema(String schema);
}
