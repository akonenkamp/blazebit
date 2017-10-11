package com.libertymutual.goforcode.blazebit.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;

@RestController 
@RequestMapping("/api/trails")
public class TrailApiController {
	
	private TrailRepository trailRepo;
	
	public TrailApiController (TrailRepository trailRepo) {
		this.trailRepo = trailRepo;
	}
	
	@GetMapping ("") 
	public List<Trail> getAll() {
		return trailRepo.findAll();
	}
	
	

}
