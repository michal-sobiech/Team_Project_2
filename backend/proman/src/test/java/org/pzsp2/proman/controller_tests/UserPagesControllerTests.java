package org.pzsp2.proman.controller_tests;

import lombok.Getter;
import lombok.Setter;
import org.pzsp2.proman.database_management.tables.inverter.dto.InverterDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.pzsp2.proman.controllers.UserPagesController.UserOverviewResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.pzsp2.proman.TestUtils;
import org.pzsp2.proman.TestUtils.MockUser;
import org.springframework.http.HttpStatus;
import org.pzsp2.proman.database_management.tables.inverter_a_data.dto.FrontendInverterADataDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserPagesControllerTests {

    @Autowired @Getter @Setter
	  private TestRestTemplate restTemplate;

    @Autowired
    @Getter @Setter
    private TestUtils testUtils;

    @Test
    public void testUserOverviewPage() {
        String URL = testUtils.getBackendURL() + "/user_home/overview";

        String name = testUtils.getMockUserData(MockUser.USER).name();
        List<FrontendInverterADataDTO> invertersData = testUtils.getMockInverterDataUserOverview();
        List<InverterDTO> inverterDTOs = testUtils.getMockInverterDTOs();
        
        HttpEntity<String> requestEntity = testUtils.getRequestEntityWithMockJWT(MockUser.USER);

        ResponseEntity<UserOverviewResponse> responseEntity = restTemplate.exchange(
            URL, HttpMethod.GET, requestEntity,UserOverviewResponse.class);
        UserOverviewResponse responseBody = responseEntity.getBody();

        assert responseBody != null; // Intellij requirement
        assertThat(responseBody.usersName()).isEqualTo(name);
        assertThat(responseBody.devices().size()).isEqualTo(inverterDTOs.size());
        assertThat(responseBody.records().size()).isEqualTo(invertersData.size());
    }

    @Test
    public void testUserAnalysisPage() {
        String URL = testUtils.getBackendURL() + "/user_home/analysis";
        
        HttpEntity<String> requestEntity = testUtils.getRequestEntityWithMockJWT(MockUser.USER);

        ResponseEntity<UserOverviewResponse> responseEntity = restTemplate.exchange(
            URL, HttpMethod.GET, requestEntity,UserOverviewResponse.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    public void testUserDevicesPage() {
        String URL = testUtils.getBackendURL() + "/user_home/devices";

        HttpEntity<String> requestEntity = testUtils.getRequestEntityWithMockJWT(MockUser.USER);

        ResponseEntity<List<InverterDTO>> responseEntity = restTemplate.exchange(
            URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
          });
        // UserOverviewResponse responseBody = responseEntity.getBody();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assertThat(responseBody.devices().size()).isEqualTo(inverterDTOs.size());
    }
}
