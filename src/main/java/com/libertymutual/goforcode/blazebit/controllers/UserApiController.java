package com.libertymutual.goforcode.blazebit.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.blazebit.controllers.SessionApiController.Credentials;
import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.models.UserTrail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserTrailRepository;
import com.libertymutual.goforcode.blazebit.services.UserService;

@RestController
@RequestMapping("/api/users")

public class UserApiController {

	private UserService userService;
	private UserRepository userRepo;
	private TrailRepository trailRepo;
	private UserTrailRepository userTrailRepo;
	
	public UserApiController(UserService userService, UserRepository userRepo, TrailRepository trailRepo, UserTrailRepository userTrailRepo) {
		this.userService = userService;
		this.userRepo = userRepo;
		this.trailRepo = trailRepo;
		this.userTrailRepo = userTrailRepo;
	}

	@PostMapping("/new")
	public User registerUser(@RequestBody User user) {
		return userService.signupAndLogin(user.getUsername(), user.getPassword(), SecurityContextHolder.getContext());
	}
	
	@PutMapping("/trails/{trail_id}/add/completed")
	public User addCompletedTrail(@RequestBody Credentials credentials, @PathVariable long trail_id) {
		User user = userRepo.findByUsername(credentials.getUsername());
		Trail theTrail = trailRepo.findOne(trail_id);
		UserTrail userTrail = new UserTrail(user, theTrail);
		userTrail.setCompleted(true);
		userTrail = userTrailRepo.save(userTrail);		
		List<UserTrail> userTrails = userTrailRepo.findByUserId(user.getId());
		user.refreshTrails(userTrails);
		user.updateStats(userTrailRepo.save(userTrail).getTrail());
		return userRepo.save(user);
	}

	@PutMapping("/trails/{trail_id}/add/wishlist")
	public User addWishlistTrail(@RequestBody Credentials credentials, @PathVariable long trail_id) {
		User user = userRepo.findByUsername(credentials.getUsername());
		if (userTrailRepo.findByUserIdAndTrailIdAndIsCompleted(user.getId(), trail_id, false).size() == 0) {
			Trail theTrail = trailRepo.findOne(trail_id);
			UserTrail userTrail = new UserTrail(user, theTrail);
			userTrail = userTrailRepo.save(userTrail);
		}
		List<UserTrail> userTrails = userTrailRepo.findByUserId(user.getId());
		user.refreshTrails(userTrails);
		return userRepo.save(user);
	}

}
