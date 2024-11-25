// package com.fininfo.saeopcc.shared.services;

// import com.fininfo.saeopcc.multitenancy.services.dto.IRLDTO;
// import com.fininfo.saeopcc.shared.domains.enumeration.ExcelContentType;
// import com.fininfo.saeopcc.shared.domains.enumeration.Sens;
// import com.fininfo.saeopcc.shared.domains.flow.ExcelFlow;
// import com.fininfo.saeopcc.shared.domains.flow.Flow;
// import com.fininfo.saeopcc.shared.repositories.FlowConfigRepository;
// import java.time.LocalDate;
// import java.util.List;
// import java.util.Optional;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;
// import org.springframework.web.multipart.MultipartFile;

// @Service
// @Transactional
// @Slf4j
// public class UploadFileService {

//   @Autowired private FlowConfigRepository flowConfigRepository;

//   @Autowired private FlowConfigService flowConfigService;

//   public void extractFlowConfig(MultipartFile file, List<IRLDTO> irldtos)
//       throws NoSuchFieldException, SecurityException {
//     Optional<ExcelFlow> excelFlowConfig =
//         flowConfigRepository.findAll().stream()
//             .filter(ExcelFlow.class::isInstance)
//             .map(ExcelFlow.class::cast)
//             .filter(
//                 flow ->
//                     flow.getSens().equals(Sens.IN)
//                         && flow.getExcelContentType().equals(ExcelContentType.OPERATIONS))
//             .findAny();

//     if (excelFlowConfig.isPresent()) {
//       Flow flowIn = new Flow();
//       flowIn.setFlowDate(LocalDate.now());
//       flowIn.setSens(Sens.IN);
//       flowIn.setTotalRecord(0);
//       flowIn.setFileType(
//           file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".") + 1));

//       flowConfigService.globalFlowIntegrationProcess(excelFlowConfig.get(), flowIn, file,
// irldtos);
//     } else {
//       log.error("Pas de flux Excel trouvé avec Type de contenu : \"OPERATION\" ");
//     }
//   }
// }
