package com.libertymutual.goforcode.blazebit.services;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;

@Service
public class UserService {
	
	private PasswordEncoder encoder;
	private UserRepository userRepo;
	private BlazebitUserDetailsService userDetails;
	
	public UserService (UserRepository userRepo, BlazebitUserDetailsService userDetails, PasswordEncoder encoder) {
		this.encoder = encoder;
		this.userRepo = userRepo;
		this.userDetails = userDetails;
	
	}
	
public User signupAndLogin (String username, String password, SecurityContext context) throws DataIntegrityViolationException {
	User user = new User (username, encoder.encode(password));
	user = userRepo.save(user);

	UserDetails details = userDetails.loadUserByUsername(user.getUsername());
	UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(details, user.getPassword(), details.getAuthorities());
	context.setAuthentication(token);
	
	return user;
}

}
