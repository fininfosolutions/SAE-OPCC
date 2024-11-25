package com.fininfo.saeopcc.multitenancy.controllers;

import com.fininfo.saeopcc.configuration.PaginationUtil;
import com.fininfo.saeopcc.configuration.ResponseUtil;
import com.fininfo.saeopcc.multitenancy.repositories.ClientRepository;
import com.fininfo.saeopcc.multitenancy.services.ClientQueryService;
import com.fininfo.saeopcc.multitenancy.services.ClientService;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientCriteria;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientDTO;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1")
public class ClientController {

  @Autowired ClientRepository clientRepository;
  @Autowired ModelMapper modelMapper;
  @Autowired ClientQueryService clientQueryService;
  @Autowired private ClientService clientService;

  @GetMapping("/clients")
  public ResponseEntity<List<ClientDTO>> getAllClients(ClientCriteria criteria, Pageable pageable) {
    Page<ClientDTO> page = clientQueryService.findByCriteria(criteria, pageable);
    HttpHeaders headers =
        PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(), page);
    return ResponseEntity.ok().headers(headers).body(page.getContent());
  }

  @GetMapping("/clients/{id}")
  public ResponseEntity<ClientDTO> getclient(@PathVariable Long id) {
    Optional<ClientDTO> clientDTO =
        clientRepository.findById(id).map(x -> modelMapper.map(x, ClientDTO.class));
    return ResponseUtil.wrapOrNotFound(clientDTO);
  }

  @GetMapping("/clients/saeopcc-funds")
  public ResponseEntity<List<ClientDTO>> getClientsByIsFundAndCategoryDescription() {
    List<ClientDTO> clients = clientService.getClientsByIsFundAndCategoryDescription();
    return ResponseEntity.ok(clients);
  }
}
