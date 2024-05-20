package ru.unlimmitted

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class KnittingFactoryApplication {

	static void main(String[] args) {
		SpringApplication.run(KnittingFactoryApplication, args)
	}

}
