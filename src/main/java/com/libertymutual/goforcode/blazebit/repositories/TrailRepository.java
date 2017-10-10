package com.libertymutual.goforcode.blazebit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.blazebit.models.Trail;

@Repository
public interface TrailRepository extends JpaRepository<Trail, Long>{

}
