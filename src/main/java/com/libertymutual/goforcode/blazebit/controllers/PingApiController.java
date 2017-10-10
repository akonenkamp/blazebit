package com.libertymutual.goforcode.blazebit.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
public class PingApiController {

	@GetMapping()
	public Object ping() {
		return "{}";
	}
	
}
