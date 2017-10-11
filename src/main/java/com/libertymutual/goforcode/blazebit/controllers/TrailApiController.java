package com.libertymutual.goforcode.blazebit.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
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
//		List<Trail> trails = trailRepo.findAll();
//		System.out.println("trails" + trailRepo.findAll().get(0).getName());
//		return trailRepo.findAll();
//		return Integer.parseInt(trails.size());
		return trailRepo.findAll();
	}
	
	

}
