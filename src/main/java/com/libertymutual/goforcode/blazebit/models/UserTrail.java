package com.libertymutual.goforcode.blazebit.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_trail")
public class UserTrail implements Serializable {
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO) 
	private Long id;
	
	@ManyToOne 
	private User user;
	
	@ManyToOne 
	private Trail trail;
	
	public UserTrail () {} 
	
	public Long getId () {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public User getUser () {
		return user;
	}
	
	public Trail getTrail () {
		return trail;
	}
	
	public void setUser (User user) {
		this.user=user;
	}
	
	public void setTrail (Trail trail) {
		this.trail=trail;
	}
	

}
