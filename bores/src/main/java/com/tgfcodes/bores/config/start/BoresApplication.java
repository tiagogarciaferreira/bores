package com.tgfcodes.bores.config.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"com.tgfcodes.bores.*"})
@EntityScan(basePackages = {"com.tgfcodes.bores.model"})
@EnableJpaRepositories(basePackages = {"com.tgfcodes.bores.repository"})
@EnableTransactionManagement
public class BoresApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(BoresApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BoresApplication.class);
	}

}
