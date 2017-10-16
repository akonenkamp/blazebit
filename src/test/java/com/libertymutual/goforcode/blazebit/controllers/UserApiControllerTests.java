package com.libertymutual.goforcode.blazebit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.libertymutual.goforcode.blazebit.controllers.SessionApiController.Credentials;
import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.models.UserTrail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserTrailRepository;
import com.libertymutual.goforcode.blazebit.services.UserService;

public class UserApiControllerTests {
	 
	private UserRepository userRepo;
	private UserApiController controller;
	private TrailRepository trailRepo;
	private UserTrailRepository userTrailRepo;
	private UserService userService;

	@Before
	public void setUp() {
		userRepo = mock(UserRepository.class);
		trailRepo = mock(TrailRepository.class);
		userTrailRepo = mock(UserTrailRepository.class);
		controller = new UserApiController(userService, userRepo, trailRepo, userTrailRepo);
		
	}

	@Test
	public void test_that_trail_is_added_to_wishlist_trails() {
		//TODO this unit test - verify save runs on user trail.
		//arrange
		User user = new User();
		user.setId(1l);
		Trail trail = new Trail();
		trail.setId(2l);
		UserTrail userTrail = new UserTrail();
		Credentials credentials = new Credentials();
		List<UserTrail> userTrailList = new ArrayList<UserTrail>();
		
		when(userRepo.findByUsername(credentials.getUsername())).thenReturn(user);
		when(userTrailRepo.findByUserIdAndTrailIdAndIsCompleted(user.getId(), trail.getId(), false)).thenReturn(userTrailList);
		when(trailRepo.findOne(trail.getId())).thenReturn(trail);
		when(userTrailRepo.save(userTrail)).thenReturn(userTrail);
		when(userTrailRepo.findByUserId(user.getId())).thenReturn(userTrailList);
//		when(userRepo.save(user)).thenReturn(user);
		
		
		//act
		controller.addWishlistTrail(credentials, trail.getId());
		
		//assert
		verify(userRepo).findByUsername(credentials.getUsername());
		verify(userTrailRepo).findByUserIdAndTrailIdAndIsCompleted(user.getId(), trail.getId(), false);
		verify(trailRepo).findOne(trail.getId());
//		assertThat(userTrailRepo.save(userTrail).getClass());
		System.out.println(userTrailRepo.save(userTrail).getClass().getName());
		
		verify(userTrailRepo).findByUserId(user.getId());
		verify(userRepo).save(user);
	}

}
