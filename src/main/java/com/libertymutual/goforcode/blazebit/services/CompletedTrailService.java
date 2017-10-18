package com.libertymutual.goforcode.blazebit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.models.UserTrail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserTrailRepository;

@Service
public class CompletedTrailService {
	
	private TrailRepository trailRepo;
	private UserTrailRepository userTrailRepo;
	private UserRepository userRepo;
	
	public CompletedTrailService (TrailRepository trailRepo, UserTrailRepository userTrailRepo, UserRepository userRepo) {
		this.trailRepo = trailRepo;
		this.userTrailRepo = userTrailRepo;
		this.userRepo = userRepo;
	}
	
	public User updateCompletedTrail(User user, long trail_id) {
		user = userRepo.findByUsername(user.getUsername());
		Trail theTrail = trailRepo.findOne(trail_id);
		UserTrail userTrail = new UserTrail(user, theTrail);
		userTrail.setCompleted(true);
		userTrail = userTrailRepo.save(userTrail);
		UserTrail trailInWishListNowCompleted = userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(),
				trail_id, false);
		if (trailInWishListNowCompleted != null) {
			userTrailRepo.delete(trailInWishListNowCompleted);
		}
		List<UserTrail> userTrails = userTrailRepo.findByUserId(user.getId());
		user.refreshTrails(userTrails);
		user.updateStats(userTrailRepo.save(userTrail).getTrail());
		return userRepo.save(user);
	}

}
