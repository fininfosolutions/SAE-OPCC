package com.fininfo.saeopcc.multitenancy.services;

import com.fininfo.saeopcc.multitenancy.domains.enumeration.ExcelContentType;
import com.fininfo.saeopcc.multitenancy.domains.flow.ExcelFlow;
import com.fininfo.saeopcc.multitenancy.domains.flow.Flow;
import com.fininfo.saeopcc.multitenancy.repositories.FlowConfigRepository;
import com.fininfo.saeopcc.multitenancy.services.dto.SubscriptionDTO;
import com.fininfo.saeopcc.shared.domains.enumeration.Sens;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@Slf4j
public class UploadFileService {

  @Autowired private FlowConfigRepository flowConfigRepository;

  @Autowired private FlowConfigService flowConfigService;

  public void extractFlowConfig(
      MultipartFile file, List<SubscriptionDTO> subscriptionsDTOs, ExcelContentType fileType)
      throws NoSuchFieldException, SecurityException {
    Optional<ExcelFlow> excelFlowConfig =
        flowConfigRepository.findAll().stream()
            .filter(ExcelFlow.class::isInstance)
            .map(ExcelFlow.class::cast)
            .filter(
                flow ->
                    flow.getSens().equals(Sens.IN) && flow.getExcelContentType().equals(fileType))
            .findAny();

    if (excelFlowConfig.isPresent()) {
      Flow flowIn = new Flow();
      flowIn.setFlowDate(LocalDate.now());
      flowIn.setSens(Sens.IN);
      flowIn.setTotalRecord(0);
      if (file != null && file.getOriginalFilename() != null) {
        flowIn.setFileType(
            Objects.requireNonNull(file.getOriginalFilename())
                .substring(Objects.requireNonNull(file.getOriginalFilename()).indexOf(".") + 1));
      } else {
        log.error("Original filename is null.");
        return;
      }

      flowConfigService.globalFlowIntegrationProcess(
          excelFlowConfig.get(), flowIn, file, subscriptionsDTOs);
    } else {
      log.error("Pas de flux Excel trouvé avec Type de contenu : \"SUBSCRIPTIONS\" ");
    }
  }
}
