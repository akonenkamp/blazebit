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
import com.libertymutual.goforcode.blazebit.services.CompletedTrailService;
import com.libertymutual.goforcode.blazebit.services.UserService;
import com.libertymutual.goforcode.blazebit.services.WishlistTrailService;

@RestController
@RequestMapping("/api/users")

public class UserApiController {

	private UserService userService;
	private TrailRepository trailRepo;
	private UserTrailRepository userTrailRepo;
	private CompletedTrailService completedService;
	private WishlistTrailService wishlistService;

	public UserApiController(UserService userService, TrailRepository trailRepo, UserTrailRepository userTrailRepo,
			CompletedTrailService completedTrailService, WishlistTrailService wishlistService) {
		this.userService = userService;
		this.trailRepo = trailRepo;
		this.userTrailRepo = userTrailRepo;
		this.completedService = completedTrailService;
		this.wishlistService = wishlistService;
	}

	@PostMapping("/new")
	public User registerUser(@RequestBody User user, HttpServletResponse response) {
		try {
			return userService.signupAndLogin(user.getUsername(), user.getPassword(),
					SecurityContextHolder.getContext());
		} catch (DataIntegrityViolationException diva) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
		}
		return null;
	}

	@PutMapping("/trails/{trail_id}/add/completed")
	public User addCompletedTrail(Authentication auth, @PathVariable long trail_id) {
		User user = (User) auth.getPrincipal();
		return completedService.updateCompletedTrail(user, trail_id);

	}

	@PutMapping("/trails/{trail_id}/add/wishlist")
	public User addWishlistTrail(Authentication auth, @PathVariable long trail_id) {
		User user = (User) auth.getPrincipal();
		return wishlistService.updateWishlist(user, trail_id, false);
	}

	@DeleteMapping("/trails/{trail_id}/remove/wishlist")
	public User removeWishlistTrail(Authentication auth, @PathVariable long trail_id, HttpServletResponse response) {
		User user = (User) auth.getPrincipal();
		return wishlistService.updateWishlist(user, trail_id, true);
	}
}
