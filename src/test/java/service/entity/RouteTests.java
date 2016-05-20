package service.entity;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class RouteTests {
	@Test
	public void testToString() throws Exception {
		Route route = new Route(Arrays.asList("A", "C", "D", "E"), null);
		assertEquals("A-C-D-E", route.toString());
	}

	@Test
	public void testgetDistance() throws Exception {
		Route route = new Route(null, Arrays.asList(5, 4, 3));
		assertEquals(12, route.getDistance());
	}
}