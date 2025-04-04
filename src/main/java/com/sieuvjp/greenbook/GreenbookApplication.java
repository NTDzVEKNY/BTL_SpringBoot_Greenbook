package com.sieuvjp.greenbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GreenbookApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreenbookApplication.class, args);
	}

}
