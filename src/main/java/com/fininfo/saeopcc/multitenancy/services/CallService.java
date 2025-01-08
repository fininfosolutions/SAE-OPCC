package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Call;
import com.fininfo.saeopcc.multitenancy.repositories.CallRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.CallDTO;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CallService {
  @Autowired private ModelMapper modelMapper;

  @Autowired private CallRepository callRepository;

  @Transactional(readOnly = true)
  public Optional<CallDTO> findOne(Long id) {
    log.debug("Request to get Call : {}", id);
    return callRepository.findById(id).map(call -> modelMapper.map(call, CallDTO.class));
  }

  public CallDTO save(CallDTO callDTO) {
    Call call = modelMapper.map(callDTO, Call.class);

    Call savedCall = callRepository.save(call);
    return modelMapper.map(savedCall, CallDTO.class);
  }
}
