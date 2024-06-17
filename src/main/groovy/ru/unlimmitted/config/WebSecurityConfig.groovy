package ru.unlimmitted.config


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import ru.unlimmitted.service.MyUserDetailsService

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class WebSecurityConfig {

	@Bean
	UserDetailsService userDetailsService() {
		return new MyUserDetailsService()
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12)
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider()
		provider.setUserDetailsService(userDetailsService())
		provider.setPasswordEncoder(passwordEncoder())
		return provider
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		HttpSessionRequestCache requestCache = new HttpSessionRequestCache()
		requestCache.setMatchingRequestParameterName(null)
		return http
				.csrf { it.disable() }
				.authorizeHttpRequests { it.requestMatchers("/js/", "/css/").permitAll()
						.anyRequest().permitAll()}
				.requestCache((cache) -> cache
						.requestCache(requestCache))
				.cors { it.disable() }
				.formLogin(form -> form
						.defaultSuccessUrl("/", true))
				.logout { it.permitAll() }
				.build()
	}
}