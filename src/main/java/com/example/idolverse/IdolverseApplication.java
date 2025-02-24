package com.example.idolverse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IdolverseApplication {

	public static void main(String[] args) {
		SpringApplication.run(IdolverseApplication.class, args);
	}

}
