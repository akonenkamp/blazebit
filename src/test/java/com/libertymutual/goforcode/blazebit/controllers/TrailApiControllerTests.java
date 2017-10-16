package com.libertymutual.goforcode.blazebit.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import com.libertymutual.goforcode.blazebit.models.Trail;
import com.libertymutual.goforcode.blazebit.repositories.TrailRepository;

public class TrailApiControllerTests {

	private TrailRepository trailRepo;
	private TrailApiController trailApiController;
	private HttpServletResponse response;

	@Before
	public void setup() {
		trailRepo = mock(TrailRepository.class);
		trailApiController = new TrailApiController(trailRepo);
		response = mock(HttpServletResponse.class);
	}

	@Test
	public void test_getAll_returns_list_of_trails() {
		Trail trail1 = new Trail();
		Trail trail2 = new Trail();
		List<Trail> trails = new ArrayList<Trail>();
		trails.add(trail1);
		trails.add(trail2);
		when(trailRepo.findAll()).thenReturn(trails);
		
		List<Trail> testTrails = trailApiController.getAll(null);
		
		assertThat(testTrails).size().isEqualTo(2);
		verify(trailRepo).findAll();
	}

	@Test
	public void test_getAll_with_partial_returns_trails_with_partial_name() {
		Trail trail1 = new Trail();
		trail1.setName("Beta");
		List<Trail> trails = new ArrayList<Trail>();
		trails.add(trail1);
		when(trailRepo.findByNameContainingIgnoreCase("Bet")).thenReturn(trails);
		
		List<Trail> testTrails = trailApiController.getAll("Bet");

		assertThat(testTrails).size().isEqualTo(1);
		verify(trailRepo).findByNameContainingIgnoreCase("Bet");
	}

	@Test
	public void test_fail_returns_null() {
		String test = trailApiController.fail(response);

		assertThat(test).isEqualTo(null);
		verify(response).setStatus(403);
	}
}