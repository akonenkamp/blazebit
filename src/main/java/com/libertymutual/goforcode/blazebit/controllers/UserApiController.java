package com.libertymutual.goforcode.blazebit.controllers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;

@RestController
@RequestMapping("/api/users")

public class UserApiController {

	private UserRepository userRepo;

	public UserApiController(UserRepository userRepo) {
		this.userRepo = userRepo;
	}

	@PostMapping("/new")
	public User registerUser(@RequestBody User user) {
		try {
			return userRepo.save(user);
		} catch (DataIntegrityViolationException diva) {
			return null;
		}

	}

}
