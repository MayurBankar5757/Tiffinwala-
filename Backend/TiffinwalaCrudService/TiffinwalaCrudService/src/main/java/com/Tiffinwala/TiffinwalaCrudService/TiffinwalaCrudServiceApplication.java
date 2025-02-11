package com.Tiffinwala.TiffinwalaCrudService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TiffinwalaCrudServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiffinwalaCrudServiceApplication.class, args);
	}

}
