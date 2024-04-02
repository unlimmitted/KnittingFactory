package ru.unlimmitted.knittingfactorymes

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class KnittingFactoryMesApplication {

	static void main(String[] args) {
		SpringApplication.run(KnittingFactoryMesApplication, args)
	}

}
