package com.fininfo.opcc.cucumber.helpers;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

public class AbstractStepDefHelper {

  private final Logger log = LoggerFactory.getLogger(AbstractStepDefHelper.class);

  private WireMockServer wireMockServer;
  private int wireMockPort = 0;

  public void request(String endpoint, HttpMethod method, String body)
      throws UnsupportedEncodingException {
    wireMockServer = new WireMockServer(WireMockConfiguration.options().dynamicPort());
    wireMockServer.start();
    wireMockPort = wireMockServer.port();
    WireMock.configureFor(wireMockPort);

    String responseBody = prepareResponseBody(body);

    if (HttpMethod.GET.equals(method)) {
      stubFor(
          get(endpoint)
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody(responseBody)));
    } else if (HttpMethod.POST.equals(method)) {
      stubFor(
          post(endpoint)
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody(responseBody)));
    } else if (HttpMethod.PUT.equals(method)) {
      stubFor(
          put(endpoint)
              .willReturn(
                  aResponse()
                      .withStatus(200)
                      .withHeader("Content-Type", "application/json")
                      .withBody(responseBody)));
    } else {
      log.info("Please configure the HttpMethod in AbstractStepDefHelper class!");
    }
  }

  public String prepareResponseBody(String body) {
    try {
      JSONObject jsonObject = new JSONObject(body);
      String operationType = jsonObject.getString("operationType");

      long id = 0;
      String reference = "";

      switch (operationType) {
        case "BUY":
          id = 1L;
          reference = "A000000000000001";
          break;
        case "SELL":
          id = 2L;
          reference = "V000000000000002";
          break;
        case "BLOCK":
          id = 3L;
          reference = "B000000000000003";
          break;
        case "UNBLOCK":
          id = 4L;
          reference = "D000000000000004";
          break;
        case "SUBSCRIPTION":
          id = 5L;
          reference = "S000000000000005";
          break;
        case "REDEMPTION":
          id = 6L;
          reference = "R000000000000006";
          break;
        case "CONVERSION_PUR_ADMIN":
          id = 7L;
          reference = "CPA0000000000007";
          break;
        case "CONVERSION_ADMIN_PUR":
          id = 8L;
          reference = "CAP0000000000008";
          break;
        case "PLEDGE":
          id = 9L;
          reference = "P000000000000009";
          break;
        case "UNPLEDGE":
          id = 10L;
          reference = "U000000000000010";
          break;
        case "TRANSFER_IN":
          id = 11L;
          reference = "TI00000000000011";
          break;
        case "TRANSFER_OUT":
          id = 12L;
          reference = "TO00000000000012";
          break;
        default:
          log.error("operation type '{}' not found!", operationType);
      }

      jsonObject.put("id", id);
      jsonObject.put("reference", reference);

      String stringBody = jsonObject.toString();
      return stringBody;
    } catch (JSONException e) {
      log.error("Error parsing JSON: {}", e.getMessage());
      return null;
    }
  }

  public int getWireMockPort() {
    return wireMockPort;
  }
}
