package com.libertymutual.goforcode.blazebit.models;

import java.io.Serializable;
import java.util.Date;

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
	
	private boolean isCompleted;
	private String dateCompleted;
	
	
	public UserTrail () {} 
	
	public UserTrail(User user, Trail trail) {
		this.setUser(user);
		this.setTrail(trail);
	}
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

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getDateCompleted() {
		return dateCompleted;
	}

	public void setDateCompleted(String dateCompleted) {
		this.dateCompleted = dateCompleted;
	}
	

}
