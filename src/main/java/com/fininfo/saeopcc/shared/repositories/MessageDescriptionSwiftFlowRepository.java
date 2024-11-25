package com.fininfo.saeopcc.shared.repositories;

import com.fininfo.saeopcc.shared.domains.swift.MessageDescriptionSwiftFlow;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MessageDescriptionSwiftFlowRepository
    extends JpaRepository<MessageDescriptionSwiftFlow, Long>,
        JpaSpecificationExecutor<MessageDescriptionSwiftFlow> {

  List<MessageDescriptionSwiftFlow> findByCode(String code);
}
