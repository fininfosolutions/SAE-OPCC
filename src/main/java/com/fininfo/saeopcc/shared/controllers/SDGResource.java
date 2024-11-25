package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.SDGRepository;
import com.fininfo.saeopcc.shared.services.dto.SDGDTO;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SDGResource {

  @Autowired SDGRepository sdgRepository;
  @Autowired private ModelMapper modelMapper;

  @GetMapping("/sdgs")
  public ResponseEntity<List<SDGDTO>> getAllSDG() {
    List<SDGDTO> sdgDTOs =
        (sdgRepository.findAll())
            .stream().map(x -> modelMapper.map(x, SDGDTO.class)).collect(Collectors.toList());
    return ResponseEntity.ok(sdgDTOs);
  }

  @GetMapping("/sdg/{id}")
  public ResponseEntity<SDGDTO> getSDGById(@PathVariable Long id) {
    Optional<SDGDTO> sdgDTO = sdgRepository.findById(id).map(x -> modelMapper.map(x, SDGDTO.class));

    return ResponseUtil.wrapOrNotFound(sdgDTO);
  }
}
