package com.example.mova;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MovaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovaApplication.class, args);
	}

}
