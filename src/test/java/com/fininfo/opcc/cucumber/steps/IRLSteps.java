// package com.fininfo.saeopcc.cucumber.steps;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import com.fininfo.saeopcc.cucumber.helpers.AbstractStepDefHelper;
// import com.fininfo.saeopcc.multitenancy.domains.enumeration.OperationStatus;
// import com.fininfo.saeopcc.multitenancy.services.dto.IRLDTO;
// import io.cucumber.java.en.Then;
// import io.cucumber.java.en.When;
// import java.io.UnsupportedEncodingException;
// import java.net.URISyntaxException;
// import org.modelmapper.ModelMapper;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.HttpMethod;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.client.RestTemplate;

// public class IRLSteps {

//   private final Logger log = LoggerFactory.getLogger(IRLSteps.class);
//   private IRLDTO irlDTO;

//   @Autowired ModelMapper modelMapper;

//   @Autowired AbstractStepDefHelper stepDefHelper;

//   @When("I save an IRL entity with operationType {word} and details:")
//   public void iSaveAnIRLEntityWithDetails(String operationType, String irlDetails)
//       throws URISyntaxException, UnsupportedEncodingException {
//     stepDefHelper.request("/api/v1/irls", HttpMethod.POST, irlDetails);
//     ResponseEntity<Object> responseEntity =
//         new RestTemplate()
//             .postForEntity(
//                 "http://localhost:" + stepDefHelper.getWireMockPort() + "/api/v1/irls",
//                 irlDetails,
//                 Object.class);

//     if (responseEntity.getStatusCode().is2xxSuccessful()) {
//       this.irlDTO = modelMapper.map(responseEntity.getBody(), IRLDTO.class);
//     } else {
//       log.error("Failed to save IRL entity. Status code: " +
// responseEntity.getStatusCodeValue());
//     }
//   }

//   @Then("the IRL entity should be saved successfully")
//   public void theIRLEntityShouldBeSavedSuccessfully() {
//     assertNotNull(this.irlDTO.getId());
//   }

//   @When("I {word} the IRL:")
//   public void iChangeIRLEntityStatus(String action, String irlDetails) {
//     this.irlDTO = modelMapper.map(irlDetails, IRLDTO.class);
//     switch (action) {
//       case "validate":
//         this.irlDTO.setStatus(OperationStatus.VALIDATED);
//         break;
//       case "settle":
//         this.irlDTO.setStatus(OperationStatus.SETTLED);
//         break;
//       case "cancel":
//         this.irlDTO.setStatus(OperationStatus.REJECTED);
//         break;
//       default:
//         throw new IllegalArgumentException("Invalid action: " + action);
//     }

//     assertNotNull(irlDTO);
//   }

//   @Then("the IRLs should be marked as {word}")
//   public void theIRLStatusChange(String status) {
//     assertEquals(this.irlDTO.getStatus().toString(), status);
//   }
// }
