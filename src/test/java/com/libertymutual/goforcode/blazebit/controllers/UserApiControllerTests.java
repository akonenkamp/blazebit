package com.libertymutual.goforcode.blazebit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.models.UserTrail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserTrailRepository;
import com.libertymutual.goforcode.blazebit.services.CompletedTrailService;
import com.libertymutual.goforcode.blazebit.services.UserService;
import com.libertymutual.goforcode.blazebit.services.WishlistTrailService;

public class UserApiControllerTests {

	private UserRepository userRepo;
	private UserApiController controller;
	private TrailRepository trailRepo;
	private UserTrailRepository userTrailRepo;
	private UserService userService;
	private Authentication auth;
	private HttpServletResponse response;
	private User user;
	private Trail trail;
	private CompletedTrailService completedTrailService;
	private WishlistTrailService wishlistService; 

	@Before
	public void setUp() {
		user = new User();
		trail = new Trail();
		response = mock(HttpServletResponse.class);
		userRepo = mock(UserRepository.class);
		trailRepo = mock(TrailRepository.class);
		userTrailRepo = mock(UserTrailRepository.class);
		auth = mock(Authentication.class);
		userService = mock(UserService.class);
		controller = new UserApiController(userService, trailRepo, userTrailRepo, completedTrailService, wishlistService);

	}

	@Test
	public void test_that_trail_is_added_to_wishlist_trails_where_trail_does_not_exist_in_wishlist() {
		// arrange
		user.setId(1l);
		trail.setId(2l);
		UserTrail userTrail = new UserTrail();
		List<UserTrail> userTrailList = new ArrayList<UserTrail>();
		when(auth.getPrincipal()).thenReturn(user);
		when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
		when(userTrailRepo.findByUserIdAndTrailIdAndIsCompleted(1l, 2l, false)).thenReturn(userTrailList);
		when(trailRepo.findOne(2l)).thenReturn(trail);
		when(userTrailRepo.save(userTrail)).thenReturn(userTrail);
		when(userTrailRepo.findByUserId(1l)).thenReturn(userTrailList);
		when(userRepo.save(user)).thenReturn(user);

		// act
		controller.addWishlistTrail(auth, 2l);

		// assert
		verify(userRepo).findByUsername(user.getUsername());
		verify(trailRepo).findOne(2l);
		verify(userTrailRepo).findByUserIdAndTrailIdAndIsCompleted(1l, 2l, false);
		verify(userTrailRepo).findByUserId(1l);
		verify(userRepo).save(user);
	}

	@Test
	public void test_that_trail_is_added_to_wishlist_trails_where_trail_does_exist_in_wishlist() {
		// arrange
		user.setId(1l);
		trail.setId(2l);
		UserTrail userTrail = new UserTrail(user, trail);
		userTrail.setCompleted(false);
		List<UserTrail> userTrailList = new ArrayList<UserTrail>();
		userTrailList.add(userTrail);
		when(auth.getPrincipal()).thenReturn(user);
		when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
		when(userTrailRepo.findByUserIdAndTrailIdAndIsCompleted(1l, 2l, false)).thenReturn(userTrailList);
		when(userTrailRepo.findByUserId(1l)).thenReturn(userTrailList);
		when(userRepo.save(user)).thenReturn(user);

		// act
		controller.addWishlistTrail(auth, 2l);

		// assert
		assertThat(user.getWishlistTrails().size()).isEqualTo(1);
		verify(auth).getPrincipal();
		verify(userRepo).findByUsername(user.getUsername());
		verify(userTrailRepo).findByUserIdAndTrailIdAndIsCompleted(1l, 2l, false);
		verify(userTrailRepo).findByUserId(1l);
		verify(userRepo).save(user);
	}

	@Test
	public void test_if_trail_is_added_to_completed_list_and_in_wishlist() {
//		// arrange
//		user.setId(1l);
//		trail.setId(2l);
//		UserTrail userTrail = new UserTrail(user, trail);
//		List<UserTrail> userTrailList = new ArrayList<UserTrail>();
//		when(auth.getPrincipal()).thenReturn(user);
//		when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
//		when(trailRepo.findOne(2l)).thenReturn(trail);
//		when(userTrailRepo.save(any(UserTrail.class))).thenReturn(userTrail);
//		when(userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(1l, 2l, false)).thenReturn(userTrail);
//		when(userTrailRepo.findByUserId(1l)).thenReturn(userTrailList);
//		when(userRepo.save(user)).thenReturn(user);
//
//		// act
//		controller.addCompletedTrail(auth, 2l);
//
//		// assert
//		verify(auth).getPrincipal();
//		verify(userRepo).findByUsername(user.getUsername());
//		verify(trailRepo).findOne(2l);
//		verify(userTrailRepo).save(userTrail);
//		verify(userTrailRepo).findFirstByUserIdAndTrailIdAndIsCompleted(1l, 2l, false);
//		verify(userTrailRepo).findByUserId(1l);
//		verify(userRepo).save(user);
	}

	@Test

	public void test_that_removeWishlistTrail_returns_error_when_not_in_wishlist() {
		User user = new User("josh", "password");
		UserTrail userTrail = new UserTrail(user, new Trail());
		user.setId(1l);
		when(auth.getPrincipal()).thenReturn(user);
		when(userRepo.findByUsername("josh")).thenReturn(user);
		when(userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), 1l, false))
				.thenThrow(new InvalidDataAccessApiUsageException(""));
		when(userRepo.save(user)).thenReturn(user);

		User actualUser = controller.removeWishlistTrail(auth, 1l, response);

		assertThat(user).isEqualTo(actualUser);
		verify(auth).getPrincipal();
		verify(userRepo).findByUsername("josh");
		verify(userTrailRepo).findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), 1l, false);
		verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
		verify(userRepo).save(user);
	}

	@Test
	public void test_that_addCompletedTrail_adds_trail_when_not_in_wishlist() {
		User user = new User("josh", "password");
		Trail trail = new Trail("newTrail", 1, 2, 3, 4);
		UserTrail userTrail = new UserTrail(user, trail);
		user.setId(1l);
		trail.setId(1l);

		when(auth.getPrincipal()).thenReturn(user);
		when(userRepo.findByUsername("josh")).thenReturn(user);
		when(trailRepo.findOne(1l)).thenReturn(trail);
		when(userTrailRepo.save(any(UserTrail.class))).thenReturn(userTrail);
		when(userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), trail.getId(), false))
				.thenReturn(null);
		when(userRepo.save(user)).thenReturn(user);

		User actualUser = controller.addCompletedTrail(auth, 1l);

		assertThat(user).isEqualTo(actualUser);
		verify(auth).getPrincipal();
		verify(userRepo).findByUsername("josh");
		verify(trailRepo).findOne(1l);
		verify(userTrailRepo).save(userTrail);
		verify(userTrailRepo).findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), trail.getId(), false);
		verify(userRepo).save(user);
	}
	
	@Test
	public void test_that_registerUser_calls_signupAndLogin() {
	    user = new User("josh", "password");
	    when(userService.signupAndLogin("josh", "password", SecurityContextHolder.getContext())).thenReturn(user);
	    
	    User actualUser = controller.registerUser(user, response);

	    assertThat(user).isEqualTo(actualUser);
	    verify(userService).signupAndLogin("josh", "password", SecurityContextHolder.getContext());
	}

	@Test
	public void test_that_registerUser_returns_SC_CONFLICT_when_registering_duplicate_user() {
	    User user = new User("josh", "password");
	    when(userService.signupAndLogin("josh", "password", SecurityContextHolder.getContext())).thenThrow(new DataIntegrityViolationException(""));

	    User actualUser = controller.registerUser(user, response);

	    verify(userService).signupAndLogin("josh", "password", SecurityContextHolder.getContext());
	    verify(response).setStatus(HttpServletResponse.SC_CONFLICT);
	}



	@Test
	public void test_that_removeWishlistTrail_removes_trail_when_in_wishlist() {
	    User user = new User("josh", "password");
	    UserTrail userTrail = new UserTrail(user, new Trail());
	    user.setId(1l);
	    when(auth.getPrincipal()).thenReturn(user);
	    when(userRepo.findByUsername("josh")).thenReturn(user);
	    when(userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), 1l, false)).thenReturn(userTrail);
	    when(userRepo.save(user)).thenReturn(user);

	    User actualUser = controller.removeWishlistTrail(auth, 1l, response);
	    
	    assertThat(user).isEqualTo(actualUser);
	    verify(auth).getPrincipal();
	    verify(userRepo).findByUsername("josh");
	    verify(userTrailRepo).findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), 1l, false);
	    verify(userTrailRepo).delete(userTrail);
	    verify(userRepo).save(user);
	}
}
