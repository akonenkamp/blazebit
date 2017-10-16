package com.libertymutual.goforcode.blazebit.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.security.core.userdetails.UserDetails;

import com.libertymutual.goforcode.blazebit.models.User;
import com.libertymutual.goforcode.blazebit.repositories.UserRepository;

public class BlazebitUserDetailsServiceTests {

	@Test 
	public void test_loadByUsername_calls_the_repo() {
			User user = new User();
			UserRepository repo = mock(UserRepository.class);
			when(repo.findByUsername("curtis")).thenReturn(user);
			BlazebitUserDetailsService service = new BlazebitUserDetailsService(repo);
			
			UserDetails actual = service.loadUserByUsername("curtis");
			
			assertThat(actual).isSameAs(user);
		}
	

	
}
