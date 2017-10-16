package com.libertymutual.goforcode.blazebit.models;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

public class UserTrailTests {
	
	@Test
	public void test_convenience_constructors_set_user_and_trail() {
	    User user = new User();
	    Trail trail = new Trail();
	    UserTrail userTrail = new UserTrail(user, trail);
	    //act 
	    User actualUser = userTrail.getUser();
	    Trail actualTrail = userTrail.getTrail();
	    //assert
	    assertThat(user).isEqualTo(actualUser);
	    assertThat(trail).isEqualTo(actualTrail);
	    
	}
	
	@Test
	public void test_getters_and_setters() {
		new BeanTester().testBean(UserTrail.class);
	}
	
	
}
