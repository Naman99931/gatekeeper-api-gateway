package com.gatekeeper.smart_gateway_service.client;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import com.gatekeeper.smart_gateway_service.dto.GatewayRoute;
import com.gatekeeper.smart_gateway_service.dto.LoginRequest;
import com.gatekeeper.smart_gateway_service.dto.LoginResponse;

@Component 
public class ManagementApiClient {
  private final RestClient restClient;
  private final String username;
  private final String password;
  private String token;
  private Instant expirationTime;

  public ManagementApiClient(@Value("${management.api.base-url}") String baseUrl,
                               @Value("${management.api.username}") String username,
                               @Value("${management.api.password}") String password) {
    this.restClient = RestClient.builder().baseUrl(baseUrl).build();
    this.username = username;
    this.password = password;
  }

  public List<GatewayRoute> fetchActiveRoutes() {
    ensureToken();
    try {
        return requestRoutes();
    }
    catch(HttpClientErrorException exception) {
        if(exception.getStatusCode() != HttpStatus.UNAUTHORIZED) {
            throw exception;
        }
        authenticate();
        return requestRoutes();
    }
  }

  private void ensureToken() {
    if (token == null || expirationTime == null || Instant.now().plusSeconds(30).isAfter(expirationTime)) {
      authenticate();
    }
  }

  private void authenticate() {
    LoginResponse response = restClient.post()
                            .uri("/api/auth/login")
                            .body(new LoginRequest(username, password))
                            .retrieve()
                            .body(LoginResponse.class);

    if (response == null || response.getAccessToken() == null || response.getExpiresIn() <= 0) {
      throw new IllegalStateException("API Management did not return a GATEWAY_SERVICE token");
    }

    this.token = response.getAccessToken();
    this.expirationTime = Instant.now().plusSeconds(response.getExpiresIn());
  }

  private List<GatewayRoute> requestRoutes() {
    return restClient.get()
                     .uri("/api/gateway/routes")
                     .header("Authorization", "Bearer " + token)
                     .retrieve()
                     .body(new ParameterizedTypeReference<List<GatewayRoute>>() { });
  }
}
