package com.libertymutual.goforcode.blazebit.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.blazebit.models.UserTrail;

@Repository
public interface UserTrailRepository extends JpaRepository<UserTrail, Long>{

	List<UserTrail> findByUserId(long id);
	
	List<UserTrail> findByUserIdAndTrailIdAndIsCompleted (long userId, long trailId, boolean isComplete);
	
	UserTrail findFirstByUserIdAndTrailIdAndIsCompleted (long userId, long trailId, boolean isComplete);

}

