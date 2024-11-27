package com.fininfo.saeopcc.multitenancy.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.multitenancy.services.CompartementService;
import com.fininfo.saeopcc.multitenancy.services.dto.CompartementDTO;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/v1")

public class CompartementController {

    @Autowired
    private CompartementService compartementService;

    @GetMapping("/compartements/without-issue-account")
    public ResponseEntity<List<CompartementDTO>> getAllFundsByUnitCategory(Pageable pageable) {
        Page<CompartementDTO> page = compartementService.findcompartementswithoutissueaccount(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
                ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
