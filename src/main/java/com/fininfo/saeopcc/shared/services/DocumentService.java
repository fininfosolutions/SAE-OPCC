package com.fininfo.saeopcc.shared.services;

import com.fininfo.saeopcc.shared.domains.flow.Document;
import com.fininfo.saeopcc.shared.repositories.DocumentRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DocumentService {

  @Autowired private DocumentRepository documentRepository;

  public Document findByReference(String reference) {
    Optional<Document> document = documentRepository.findByReferenceNotif(reference);
    if (document.isPresent()) return document.get();
    else return null;
  }
}
