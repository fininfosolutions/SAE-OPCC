package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Asset;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Asset entity. */
@SuppressWarnings("unused")
@Repository
public interface AssetRepository
    extends JpaRepository<Asset, Long>, JpaSpecificationExecutor<Asset> {

  List<Asset> findByIsin(String isin);

  Page<Asset> findByIsinIsNotNull(Pageable pageable);

  Optional<Asset> getByIsin(String isin);
}
