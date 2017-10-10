package com.libertymutual.goforcode.blazebit.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;
import com.libertymutual.goforcode.blazebit.services.UserService;

@RestController
@RequestMapping("/api/users")

public class UserApiController {

	private UserService userService;

	public UserApiController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/new")
	public User registerUser(@RequestBody User user) {
		return userService.signupAndLogin(user.getUsername(), user.getPassword(), SecurityContextHolder.getContext());
	}

}
