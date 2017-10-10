package com.libertymutual.goforcode.blazebit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.blazebit.models.UserTrail;

@Repository
public interface UserTrailRepository extends JpaRepository<UserTrail, Long>{

}
