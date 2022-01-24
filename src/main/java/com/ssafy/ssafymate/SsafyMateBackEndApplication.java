package com.ssafy.ssafymate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SsafyMateBackEndApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SsafyMateBackEndApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(SsafyMateBackEndApplication.class, args);
	}

}
