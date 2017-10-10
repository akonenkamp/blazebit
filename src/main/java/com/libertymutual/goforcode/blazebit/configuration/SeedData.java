package com.libertymutual.goforcode.blazebit.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.models.UserTrail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserTrailRepository;

@Configuration
@Profile("development")
public class SeedData {

	public SeedData(UserRepository userRepo, TrailRepository trailRepo, UserTrailRepository userTrailRepo, PasswordEncoder encoder) {
		User user = new User("joshie", encoder.encode("password"));
		Trail trail = new Trail("Mountain");
		
		UserTrail userTrail = new UserTrail();
		
		userRepo.save(user);
		trailRepo.save(trail);
		userTrail.setUser(user);
		userTrail.setTrail(trail);
		userRepo.save(user);
		trailRepo.save(trail);
		userTrailRepo.save(userTrail);
	}
}
