package service.entity;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class RouteTests {
	@Test
	public void testToString() throws Exception {
		Route route = new Route(Arrays.asList("A", "C", "D", "E"), 0);
		assertEquals("A-C-D-E", route.toString());
	}

	@Test
	public void testgetDistance() throws Exception {
		Route route = new Route(null, 12);
		assertEquals(12, route.getDistance());
	}
}