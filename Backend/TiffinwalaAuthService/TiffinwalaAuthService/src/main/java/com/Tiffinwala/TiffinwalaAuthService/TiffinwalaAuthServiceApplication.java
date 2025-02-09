package com.Tiffinwala.TiffinwalaAuthService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TiffinwalaAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiffinwalaAuthServiceApplication.class, args);
	}

}
