package com.libertymutual.goforcode.blazebit.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.libertymutual.goforcode.blazebit.models.User;

@Controller
@RequestMapping("/session")
public class SessionApiController {

	@PostMapping("/new")
	public User create(@RequestBody User user) {
		return user;

	}

}
