package ru.unlimmitted.knittingfactorymes.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import ru.unlimmitted.knittingfactorymes.entity.User
import ru.unlimmitted.knittingfactorymes.repository.MainRepository


@Service
class AuthService {

	@Autowired
	MainRepository repository

	public User authorizeUser(String login, String password) {
		login = login.trim();
		password = password.trim();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		Set<GrantedAuthority> roles = new HashSet<>();
		roles.add(new SimpleGrantedAuthority("USER"));

		User supporter = repository.findByLoginAndPassword(login, password);
		if (supporter != null) {
			Authentication auth = new UsernamePasswordAuthenticationToken(supporter.getId(), passwordEncoder.encode(password), roles);
			SecurityContextHolder.getContext().setAuthentication(auth);
//          SecurityContext sc = SecurityContextHolder.getContext();
//          System.out.println();
		}
		return supporter;
	}

}
