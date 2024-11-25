package com.fininfo.opcc.cucumber;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import com.fininfo.opcc.cucumber.helpers.AbstractStepDefHelper;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(
    classes = {
      ModelMapper.class,
      RestTemplate.class,
      AbstractStepDefHelper.class,
    })
@PropertySource("classpath:application.properties")
public class CucumberSpringConfiguration {

  private final Logger log = LoggerFactory.getLogger(CucumberSpringConfiguration.class);
  // @Value("${wiremock.port}")
  // private int wireMockPort;

  // @Autowired private WireMockServer wireMockServer;

  // public void setUp() {
  //   log.info("wiremock properties value :{}", wireMockPort);
  //   wireMockServer = new WireMockServer(WireMockConfiguration.options().port(wireMockPort));
  //   wireMockServer.start();
  //   WireMock.configureFor(wireMockPort);
  //   new WireMock().loadMappingsFrom("src/test/resources");
  // }

  // @After
  // public void tearDown() {
  //   wireMockServer.stop();
  // }
}
