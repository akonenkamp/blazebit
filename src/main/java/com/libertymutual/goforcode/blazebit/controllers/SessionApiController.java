package com.libertymutual.goforcode.blazebit.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.models.UserTrail;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserTrailRepository;


@RestController

@RequestMapping("/api/session")

public class SessionApiController {

	private UserDetailsService userDetails;
	private AuthenticationManager authenticator;
	private UserRepository userRepo;
	private UserTrailRepository userTrailRepo;
	
	public SessionApiController (UserDetailsService userDetails, AuthenticationManager authenticator, UserRepository userRepo, UserTrailRepository userTrailRepo) {
		this.userDetails = userDetails;
		this.authenticator = authenticator;
		this.userRepo = userRepo;
		this.userTrailRepo = userTrailRepo;
	}

	@PutMapping("/mine")
	public User login(@RequestBody Credentials credentials) {
		
		UserDetails details = userDetails.loadUserByUsername(credentials.getUsername());
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details,
				credentials.password, details.getAuthorities());
		authenticator.authenticate(token);
		
		if (token.isAuthenticated()) {
			//TODO add catch for if our login password is wrong
			SecurityContextHolder.getContext().setAuthentication(token);
			User user = userRepo.findByUsername(credentials.getUsername());
			List<UserTrail> userTrail = userTrailRepo.findByUserId(user.getId());
			user.refreshTrails(userTrail);
			return user;
		}

		return null;
	}

	@DeleteMapping("/mine")
	public Boolean logout(Authentication auth, HttpServletRequest request, HttpServletResponse response) {
		new SecurityContextLogoutHandler().logout(request, response, auth);
		return true;
	}

	static class Credentials {
		private String username;
		private String password;
		public String getUsername() {
			
			return username;

		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;

		}

	}

}
