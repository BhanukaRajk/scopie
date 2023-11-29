package com.scopie.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	RouteLocator routeLocator(RouteLocatorBuilder builder){
		return  builder.routes()
				.route((r)-> (Buildable<Route>) r.path("/auth/**").uri("lb://AUTH-SERVICE"))
				.route((r)-> (Buildable<Route>) r.path("/movie/**").uri("lb://MAIN-SERVICE"))
				.route((r)-> (Buildable<Route>) r.path("/reservation/**").uri("lb://MAIN-SERVICE"))
				.build();
	}
	@Bean
	DiscoveryClientRouteDefinitionLocator dynamicRoutes(ReactiveDiscoveryClient discoveryClient, DiscoveryLocatorProperties locatorProperties){
		return new DiscoveryClientRouteDefinitionLocator(discoveryClient,locatorProperties);
	}
}
