package com.libertymutual.goforcode.blazebit.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import com.libertymutual.goforcode.blazebit.services.BlazebitUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private BlazebitUserDetailsService users;
	
	public SecurityConfig(BlazebitUserDetailsService users) {
		this.users = users;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers(HttpMethod.GET, "/api/ping", "/api/trails/**").permitAll()
				.antMatchers(HttpMethod.POST, "/api/users/new").permitAll()
				.antMatchers(HttpMethod.PUT, "/api/session/mine").permitAll()	
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.anyRequest().authenticated()
//				.and()
//				.formLogin()
//				.loginPage("/api/session/mine")
//				.usernameParameter("username")
//				.passwordParameter("password")
//				.loginProcessingUrl("/api/session/mine")
				.and()
				.addFilterAfter(new CsrfIntoCookieFilter(), CsrfFilter.class)
			// remove the .disable() and then uncomment the csrfTokenRepository line to implement csrf checking
				.csrf().disable();
//				.csrfTokenRepository(tokenRepository());
				
				}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder builder) throws Exception {
		builder
		.userDetailsService(users)
		.passwordEncoder(passwordEncoder());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	public CsrfTokenRepository tokenRepository() {
		HttpSessionCsrfTokenRepository tokenRepository = new HttpSessionCsrfTokenRepository();
		tokenRepository.setHeaderName("X-XSRF-TOKEN");
		tokenRepository.setParameterName("_csrf");
		return tokenRepository;
	}
		

	
	@Override
	public UserDetailsService userDetailsService() {
		return users;
	}
	
	
}
