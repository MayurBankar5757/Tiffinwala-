package com.example.APIGateway;

import java.util.Arrays;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
@Configuration
public class MyBeans {

	 //http://localhost:8080/api1/welcome   --  localhost:8081
	 //http://localhost:8080/api1/welcome   -- localhost:8082
	
	@Bean
	public RouteLocator customRouterLocator(RouteLocatorBuilder builder) {
		return builder.routes() 
				.route("AuthService",r->r.path("/api1/**")
					//.uri("http://localhost:8081"))   
					 .uri("lb://AuthService"))
				.route("CrudService",r->r.path("/api2/**")
					 //.uri("http://localhost:8082"))
					 .uri("lb://CrudService"))	
				.route("TWservice2",r->r.path("/api3/**")
						 .uri("lb://TWservice2"))
				.build();
		
	}
	
	
	@Bean
    public CorsWebFilter corsWebFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        config.setAllowedOrigins(Arrays.asList("http://localhost:3010")); // Frontend URL
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        source.registerCorsConfiguration("/**", config);
        return new CorsWebFilter(source);
    }
}

