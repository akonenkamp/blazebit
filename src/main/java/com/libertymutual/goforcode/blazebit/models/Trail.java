package com.libertymutual.goforcode.blazebit.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

public class Trail {
	
	@Id 
	@GeneratedValue (strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column (unique = true, nullable = false)
	private String name;
	
	public Trail() {}
	
	
	public Trail(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}
	


	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	


}
