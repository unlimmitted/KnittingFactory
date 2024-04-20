package ru.unlimmitted.knittingfactorymes.controller


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping

@Controller
@CrossOrigin
class MainController {

	@GetMapping("{path:[^\\\\.]*}")
	String catchAllPath() {
		return "main"
	}

	@GetMapping("/")
	String getMainPage() {
		return "main"
	}
}
