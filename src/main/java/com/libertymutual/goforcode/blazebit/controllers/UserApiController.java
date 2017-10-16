package com.libertymutual.goforcode.blazebit.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public User registerUser(@RequestBody User user, HttpServletResponse response) {
		try {
		return userService.signupAndLogin(user.getUsername(), user.getPassword(), SecurityContextHolder.getContext());
		} catch (DataIntegrityViolationException diva) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
		}
		return null;
	}
	
	@PutMapping("/trails/{trail_id}/add/completed")
	public User addCompletedTrail(Authentication auth, @PathVariable long trail_id) {
		User user = (User) auth.getPrincipal();
		user = userRepo.findByUsername(user.getUsername());
		Trail theTrail = trailRepo.findOne(trail_id);
		UserTrail userTrail = new UserTrail(user, theTrail);
		userTrail.setCompleted(true);
		userTrail = userTrailRepo.save(userTrail);	
		UserTrail trailInWishListNowCompleted = userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), trail_id, false);

		if (trailInWishListNowCompleted != null) {
			userTrailRepo.delete(trailInWishListNowCompleted);
		} 
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

	@DeleteMapping("/trails/{trail_id}/remove/wishlist")
	public User removeWishlistTrail (@RequestBody Credentials credentials, @PathVariable long trail_id, HttpServletResponse response) {
		User user = userRepo.findByUsername(credentials.getUsername());
		try {
		UserTrail userTrail = userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), trail_id, false);
			userTrailRepo.delete(userTrail);
		} catch (InvalidDataAccessApiUsageException ida) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
		List<UserTrail> userTrails = userTrailRepo.findByUserId(user.getId());
		user.refreshTrails(userTrails);
		return userRepo.save(user);
	}
}
