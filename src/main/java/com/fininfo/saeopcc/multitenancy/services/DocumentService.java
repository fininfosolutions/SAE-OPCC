package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.flow.Document;
import com.fininfo.saeopcc.multitenancy.repositories.DocumentRepository;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentService {

  @Autowired private DocumentRepository documentRepository;

  public Document saveDocument(
      String title, String mimeType, Long size, byte[] content, LocalDate date) {
    Document document = new Document();
    document.setTitle(title);
    document.setMimeType(mimeType);
    document.setSize(size);
    document.setContent(content);
    document.setDate(date);

    return documentRepository.save(document);
  }
}
