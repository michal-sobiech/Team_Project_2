package org.pzsp2.proman.controller_tests;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.pzsp2.proman.TestUtils;
import org.pzsp2.proman.TestUtils.MockUser;
import org.pzsp2.proman.TestUtils.MockUserData;
import org.pzsp2.proman.controllers.LoginController.LoginResponse;
import org.pzsp2.proman.security.TokenService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LogInControllerTests {

    @Autowired @Getter @Setter
    private TestRestTemplate restTemplate;

    @Autowired @Getter @Setter
    private TestUtils testUtils;

    @Autowired @Getter @Setter
    private TokenService tokenService;

    @Test
    public void testLogInForAllUserTypes() throws Exception {
        String URL = testUtils.getBackendURL() + "/log_in";

        for (MockUser mockUser : MockUser.values()) {
            MockUserData mockUserData = testUtils.getMockUserData(mockUser);

            String username = mockUserData.username();
            String password = mockUserData.unhashedPassword();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            JSONObject requestBody = new JSONObject();
            requestBody.put("username", username);
            requestBody.put("password", password);

            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody.toString(), headers);
            ResponseEntity<LoginResponse> responseEntity = restTemplate.exchange(
                URL, HttpMethod.POST, requestEntity, LoginResponse.class);
            LoginResponse responseBody = responseEntity.getBody();

            assertThat(responseBody != null).isEqualTo(true);
            assert responseBody != null; // Intellij requires
            assertThat(responseBody.role()).isEqualTo(mockUser.getRole());
            assertThat(tokenService.isTokenValid(responseBody.token())).isEqualTo(true);
        }
    }
}
