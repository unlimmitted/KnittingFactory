package ru.unlimmitted.knittingfactorymes.controller

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping

@Controller
@CrossOrigin
class MainController {

	@GetMapping("/**")
	String getRootRequest() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication()
		if (auth instanceof AnonymousAuthenticationToken) {
			return "login"
		} else {
			return "redirect:/main"
		}
	}

	@GetMapping("/main")
	String getMainPage() {
		return "main"
	}
}
