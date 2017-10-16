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
	private double distance;
	private long elevation;
	private Double lattitude;
	private Double longitude;
	
	public Trail() {}
	

	public Trail(String name, double distance, long elevation, double lattitude, double longitude) {
		this.name = name;
		this.distance = distance;
		this.elevation = elevation;
		this.lattitude = lattitude;
		this.longitude = longitude;
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

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public long getElevation() {
		return elevation;
	}

	public void setElevation(long elevation) {
		this.elevation = elevation;
	}

	public Double getLattitude() {
		return lattitude;
	}

	public void setLattitude(Double lattitude) {
		this.lattitude = lattitude;
	}


	public Double getLongitude() {
		return longitude;
	}


	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	


}
