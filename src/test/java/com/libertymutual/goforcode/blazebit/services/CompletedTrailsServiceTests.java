package com.libertymutual.goforcode.blazebit.services;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.models.UserTrail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserTrailRepository;

public class CompletedTrailsServiceTests {

	private UserRepository userRepo;
	private TrailRepository trailRepo;
	private UserTrailRepository userTrailRepo;
	private User user;
	private Trail trail;
	private CompletedTrailService completedTrailService;
	
	@Before
	public void setUp() throws Exception {
		user = new User();
		trail = new Trail();
		userRepo = mock(UserRepository.class);
		trailRepo = mock(TrailRepository.class);
		userTrailRepo = mock(UserTrailRepository.class);
	}

	@Test
	public void test_update_completed_trails_adds_a_completed_trail() {
		// arrange
		user.setId(1l);
		trail.setId(2l);
		UserTrail userTrail = new UserTrail(user, trail);
		List<UserTrail> userTrailList = new ArrayList<UserTrail>();
		when(userRepo.findByUsername(user.getUsername())).thenReturn(user);
		when(trailRepo.findOne(2l)).thenReturn(trail);
		when(userTrailRepo.save(any(UserTrail.class))).thenReturn(userTrail);
		when(userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(1l, 2l, false)).thenReturn(userTrail);
		when(userTrailRepo.findByUserId(1l)).thenReturn(userTrailList);
		when(userRepo.save(user)).thenReturn(user);

		// act
		completedTrailService.updateCompletedTrail(user, 2l);

		// assert
		verify(userRepo).findByUsername(user.getUsername());
		verify(trailRepo).findOne(2l);
		verify(userTrailRepo).save(userTrail);
		verify(userTrailRepo).findFirstByUserIdAndTrailIdAndIsCompleted(1l, 2l, false);
		verify(userTrailRepo).findByUserId(1l);
		verify(userRepo).save(user);
	}

}
