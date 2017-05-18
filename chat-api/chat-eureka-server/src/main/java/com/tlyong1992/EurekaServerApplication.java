package com.tlyong1992;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class EurekaServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(EurekaServerApplication.class).web(true).run();
	}
}
