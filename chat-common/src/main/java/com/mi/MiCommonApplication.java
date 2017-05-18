package com.tlyong1992;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MiCommonApplication extends SpringBootServletInitializer{

	private static Logger log = LoggerFactory.getLogger(MiCommonApplication.class);

	public static void main(String[] args) {
		ConfigurableApplicationContext context =  SpringApplication.run(MiCommonApplication.class, args);
		String[] activeProfiles = context.getEnvironment().getActiveProfiles();
		for (String profile : activeProfiles){
			log.info("String active profile:{}" ,profile);
		}
		log.info("应用程序启动完毕");
	}
}
