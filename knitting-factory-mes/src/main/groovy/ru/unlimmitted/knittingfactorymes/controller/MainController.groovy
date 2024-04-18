package ru.unlimmitted.knittingfactorymes.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import ru.unlimmitted.knittingfactorymes.service.AuthService

@Controller
@RequestMapping
@CrossOrigin
class MainController {

	@Autowired
	AuthService authService

	@GetMapping("/*")
	public String getRootRequest() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication()
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			return "redirect:/"
		} else {
			return "login"
		}
	}

	@PostMapping("/login")
	String login(
			@RequestParam("username") String login,
			@RequestParam("password") String password
	) {

		if (authService.authorizeUser(login, password) != null) {
			return "main"
		} else {
			return "login"
		}

	}

	@GetMapping("/")
	public String getMainPage() {
		return "main"
	}
}
