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
public class WishlistTrailService {
	private UserRepository userRepo;
	private TrailRepository trailRepo;
	private UserTrailRepository userTrailRepo;

	public WishlistTrailService(UserRepository userRepo, TrailRepository trailRepo, UserTrailRepository userTrailRepo) {
		this.userRepo = userRepo;
		this.trailRepo = trailRepo;
		this.userTrailRepo = userTrailRepo;
	}

	public User updateWishlist(User user, long trailId, boolean removeTrail) {
		user = userRepo.findByUsername(user.getUsername());
		UserTrail userTrail = userTrailRepo.findFirstByUserIdAndTrailIdAndIsCompleted(user.getId(), trailId, false);
		if (removeTrail) {
			if (userTrail != null) {
				userTrailRepo.delete(userTrail);
			}

		} else {
			if (userTrail == null) {
				Trail trail = trailRepo.findOne(trailId);
				UserTrail newUserTrail = new UserTrail(user, trail);
				newUserTrail = userTrailRepo.save(newUserTrail);
			}
		}

		List<UserTrail> userTrails = userTrailRepo.findByUserId(user.getId());
		user.refreshTrails(userTrails);
		return userRepo.save(user);
	}
}