package com.libertymutual.goforcode.blazebit.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.blazebit.controllers.SessionApiController.Credentials;
import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;

@RestController 
@RequestMapping("/api/trails")
public class TrailApiController {
	
	private TrailRepository trailRepo;
	private UserRepository userRepo;
	
	public TrailApiController (TrailRepository trailRepo, UserRepository userRepo) {
		this.trailRepo = trailRepo;
	}
	
	@GetMapping ("") 
	public List<Trail> getAll() {
		return trailRepo.findAll();
	}
	
	

}
