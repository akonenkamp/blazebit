package com.libertymutual.goforcode.blazebit.models;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "blazebit_user")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	
	@Column(unique = true, nullable = false)
	private String username;

	
	@Transient
	private String password;
	
	@JsonProperty (access = Access.WRITE_ONLY)
	private String encryptedPassword;

	private Double totalDistance;
	private Long totalElevation;
	

	public User() {
		this.username = "";
	}
	
	public User(User user) {
		this.id = user.id;
		this.username = user.username;
		this.encryptedPassword = user.encryptedPassword;
		this.password = user.password;
		
	}

	public User(String username, String encryptedPassword) {
		this.username = username;
		this.encryptedPassword = encryptedPassword;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return encryptedPassword;
	}

	public void setPassword(String password) {
		this.encryptedPassword = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList("USER");
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public Double getTotalMiles() {
		return totalDistance;
	}

	public void setTotalMiles(Double totalMiles) {
		this.totalDistance = totalMiles;
	}

	public Long getTotalElevation() {
		return totalElevation;
	}

	public void setTotalElevation(Long totalElevation) {
		this.totalElevation = totalElevation;
	}

}
