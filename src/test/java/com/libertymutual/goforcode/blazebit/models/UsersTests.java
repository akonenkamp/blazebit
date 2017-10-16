package com.libertymutual.goforcode.blazebit.models;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.springframework.security.core.GrantedAuthority;

public class UsersTests {
	
	private User user;
	
	@Before
	public void setup() {
		user = new User();
	}
	
	@Test
	public void test_getters_and_setters() {
		new BeanTester().testBean(User.class);
	}
	
	@Test

	public void test_getAuthorities_returns_proper_authorities() {
		List<? extends GrantedAuthority> actual = user.getAuthorities().stream().collect(Collectors.toList());

		assertThat(actual).hasSize(1);
		assertThat(actual.get(0).getAuthority()).isEqualTo("USER");
	}
	
	@Test
	public void test_refresh_trail() {
		
	}
	
	
}
