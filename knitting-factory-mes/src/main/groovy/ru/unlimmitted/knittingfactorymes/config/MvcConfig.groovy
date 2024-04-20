package ru.unlimmitted.knittingfactorymes.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig implements WebMvcConfigurer {

	@Override
	void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login")
		registry.addViewController("/").setViewName("main")
		registry.addViewController("/orders").setViewName("main")
		registry.addViewController("/recipes").setViewName("main")
		registry.addViewController("/warehouse").setViewName("main")
	}
}