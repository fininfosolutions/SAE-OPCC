package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.Address;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/** Spring Data repository for the Address entity. */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository
    extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Address> {}
