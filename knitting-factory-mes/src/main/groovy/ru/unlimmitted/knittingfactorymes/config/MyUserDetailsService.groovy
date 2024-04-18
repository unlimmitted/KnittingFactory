package ru.unlimmitted.knittingfactorymes.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.unlimmitted.knittingfactorymes.repository.UserRepository

@Service
class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository repository

	@Override
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByUsername(username.trim())
	}
}
