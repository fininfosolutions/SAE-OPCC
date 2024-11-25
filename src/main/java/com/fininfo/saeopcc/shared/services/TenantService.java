package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.Tenant;
import com.fininfo.saeopcc.shared.repositories.TenantRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

  private final TenantRepository repository;

  @Autowired
  public TenantService(TenantRepository repository) {
    this.repository = repository;
  }

  public Iterable<Tenant> getAll() {
    return repository.findAll();
  }

  public Optional<Tenant> getById(String id) {
    return repository.findById(id);
  }

  public Optional<Tenant> getByIssuer(String issuer) {
    return repository.findByIssuer(issuer);
  }

  public Optional<Tenant> getBySchema(String issuer) {
    return repository.findBySchema(issuer);
  }
}
