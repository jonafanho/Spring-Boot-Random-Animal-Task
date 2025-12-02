package org.animal

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Main {
	public static final String CROSS_ORIGIN = "http://localhost:4200"

	static void main(String[] args) {
		SpringApplication.run(Main, args)
	}
}
