package com.tlyong1992;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class MiEurekaClientApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiEurekaClientApiApplication.class, args);
	}
}
