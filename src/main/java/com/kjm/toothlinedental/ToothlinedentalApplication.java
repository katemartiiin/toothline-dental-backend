package com.kjm.toothlinedental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.kjm.toothlinedental")
@EnableJpaRepositories(basePackages = "com.kjm.toothlinedental.repository")
@EntityScan(basePackages = "com.kjm.toothlinedental.model")
public class ToothlinedentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToothlinedentalApplication.class, args);
	}

}
