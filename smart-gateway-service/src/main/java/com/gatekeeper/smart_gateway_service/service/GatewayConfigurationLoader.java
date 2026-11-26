package com.gatekeeper.smart_gateway_service.service;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.gatekeeper.smart_gateway_service.client.ManagementApiClient;
import com.gatekeeper.smart_gateway_service.dto.GatewayRoute;

@Service 
public class GatewayConfigurationLoader implements ApplicationRunner{

  private final ManagementApiClient managementApiClient;

  public GatewayConfigurationLoader(ManagementApiClient managementApiClient) {
    this.managementApiClient = managementApiClient;
  }

  @Override 
  public void run(ApplicationArguments arguments) {
    refreshGatewayConfiguration();
  }

  private void refreshGatewayConfiguration() {
    List<GatewayRoute> routes = managementApiClient.fetchActiveRoutes();

    for(GatewayRoute route : routes) {
      System.out.println("Route : " + route.getTargetPathPrefix());
    }
  }
}
