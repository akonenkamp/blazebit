package com.libertymutual.goforcode.blazebit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.models.UserTrail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;

@SpringBootApplication
public class BlazebitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlazebitApplication.class, args);

	}
}
