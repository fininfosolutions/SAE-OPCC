package com.fininfo.saeopcc.shared.controllers;

import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.shared.repositories.AccountCategoryRepository;
import com.fininfo.saeopcc.shared.services.dto.AccountCategoryDTO;
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
public class AccountCategoryRessource {

  @Autowired AccountCategoryRepository accountCategoryRepository;
  @Autowired ModelMapper modelMapper;

  @GetMapping("/accountCategories")
  public ResponseEntity<List<AccountCategoryDTO>> getAllAccountCategoryDTOs() {
    List<AccountCategoryDTO> AccountCategoryDTOs =
        accountCategoryRepository.findAll().stream()
            .map(x -> modelMapper.map(x, AccountCategoryDTO.class))
            .collect(Collectors.toList());
    return ResponseEntity.ok(AccountCategoryDTOs);
  }

  @GetMapping("/accountCategories/{id}")
  public ResponseEntity<AccountCategoryDTO> getAccountCategory(@PathVariable Long id) {
    Optional<AccountCategoryDTO> AccountCategoryDTO =
        accountCategoryRepository
            .findById(id)
            .map(x -> modelMapper.map(x, AccountCategoryDTO.class));
    return ResponseUtil.wrapOrNotFound(AccountCategoryDTO);
  }
}
