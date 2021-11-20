package com.dbp.tools

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@SpringBootApplication
class ToolsApplication	: WebSecurityConfigurerAdapter() {
	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {
		http
			.authorizeRequests(
				Customizer { a ->
					a
						.antMatchers("/core/version").permitAll() // Las rutas publica
						.anyRequest().authenticated()  // El resto se espera autenticación.
				}
			).oauth2Login() // Establece el login
	}
}

fun main(args: Array<String>) {
	System.out.println("Original")
	runApplication<ToolsApplication>(*args)
}