package com.libertymutual.goforcode.blazebit.models;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;
import org.springframework.security.core.GrantedAuthority;

public class UserTests {
	
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
		//arrange 
		List<UserTrail> userTrail = new ArrayList<UserTrail>();
		UserTrail complete = new UserTrail();
		UserTrail wishlist = new UserTrail();
		complete.setCompleted(true);
		userTrail.add(complete);
		userTrail.add(wishlist);
		
		//act
		user.refreshTrails(userTrail);
		
		//assert
		assertThat(user.getCompletedTrails().size()).isEqualTo(1);
		assertThat(user.getWishlistTrails().size()).isEqualTo(1);
		
 	}
	
	@Test
	public void test_update_stats_updates_the_stats_of_user() {
		Trail trail = new Trail("Test Trail", 1, 2, 3, 4);

		user.updateStats(trail);

		assertThat(user.getTotalDistance()).isEqualTo(1);
		assertThat(user.getTotalElevation()).isEqualTo(2);
		assertThat(user.getTotalTrails()).isEqualTo(1);
	}
	
	@Test
	public void test_convenience_constructors_set_username_and_password() {
	    User testUser = new User("luka", "password");
	    user = new User(testUser);
	    String username = user.getUsername();
	    String password = user.getPassword();

	    assertThat(username).isEqualTo("luka");
	    assertThat(password).isEqualTo("password");
	}
	
	@Test
    public void test_isAccountNonExpired_returns_true() {
        assertThat(user.isAccountNonExpired()).isTrue();
    }
	
    @Test
    public void test_isAccountNonLocked_returns_true() {
        assertThat(user.isAccountNonLocked()).isTrue();
    }
    
    @Test
    public void test_isCredentialNonExpired_returns_true() {
        assertThat(user.isCredentialsNonExpired()).isTrue();
    }
    
    @Test
    public void test_isEnabled_returns_true() {
        assertThat(user.isEnabled()).isTrue();
    }
}
