package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.Client;
import com.fininfo.saeopcc.multitenancy.repositories.ClientRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.ClientDTO;
import com.fininfo.saeopcc.shared.domains.Address;
import com.fininfo.saeopcc.shared.domains.Role;
import com.fininfo.saeopcc.shared.repositories.AddressRepository;
import com.fininfo.saeopcc.shared.repositories.RoleRepository;
import com.fininfo.saeopcc.util.TenantContext;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientService {

  @Autowired private ClientRepository clientRepository;
  @Autowired private AddressRepository addressRepo;
  @Autowired private RoleRepository roleRepository;
  @Autowired private ModelMapper modelMapper;

  public Client syncClient(Client client, String tenant) {

    Client synced = null;
    TenantContext.setTenantId(tenant);
    if (client.getId() != null) {
      Set<Address> addressList = client.getAddresses();
      if (!addressList.isEmpty()) {
        addressRepo.saveAll(addressList);
      }
      synced = clientRepository.save(client);
      synced.setAddresses(client.getAddresses());
      createOrUpdateAddresses(synced);

    } else log.error("Synchronize Shareholder Process Cannot Execute without ID !!!");

    return synced;
  }

  public List<ClientDTO> getClientsByIsFundAndCategoryDescription() {
    List<Client> clients = clientRepository.findClientsByIsFundAndClientCategoryDescription();
    return clients.stream()
        .map(client -> modelMapper.map(client, ClientDTO.class))
        .collect(Collectors.toList());
  }

  private void createOrUpdateAddresses(Client client) {
    Role role = roleRepository.getReferenceById(client.getId());
    Set<Address> addressList = client.getAddresses();
    if (!addressList.isEmpty()) {
      for (Address address : addressList) {
        address.setRole(role);
      }
      addressRepo.saveAll(addressList);
    }
  }
}
