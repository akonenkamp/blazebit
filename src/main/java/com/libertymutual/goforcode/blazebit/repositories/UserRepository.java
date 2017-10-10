package com.libertymutual.goforcode.blazebit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.libertymutual.goforcode.blazebit.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername (String username);
	

}
