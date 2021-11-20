package com.dbp.tools

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication

import org.springframework.boot.runApplication
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.factory.PasswordEncoderFactories

@SpringBootApplication()
class ToolsApplication	: WebSecurityConfigurerAdapter() {

	@Autowired
	lateinit var env: Environment;

	@Throws(java.lang.Exception::class)
	override fun configure(auth: AuthenticationManagerBuilder) {

		if(env.acceptsProfiles(Profiles.of("test"))) {
			val encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()
			auth
				.inMemoryAuthentication()
				.withUser("user")
				.password(encoder.encode("password"))
				.roles("USER")
				.and()
				.withUser("admin")
				.password(encoder.encode("admin"))
				.roles("USER", "ADMIN")
		}
	}

	@Throws(Exception::class)
	override fun configure(http: HttpSecurity) {
		http
			.authorizeRequests(
				Customizer { a ->
					   a.antMatchers("/core/version").permitAll() // Las rutas publica
						.anyRequest().authenticated()  // El resto se espera autenticación.
				}
			).oauth2Login()
			.and()
			.httpBasic()
	}
}

fun main(args: Array<String>) {
	runApplication<ToolsApplication>(*args)
}