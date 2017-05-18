package com.tlyong1992;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
public class MiEurekaServerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MiEurekaServerApplication.class).web(true).run();
	}
}
