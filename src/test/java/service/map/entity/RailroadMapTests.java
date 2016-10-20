package service.map.entity;

import static org.junit.Assert.*;

import org.junit.Test;

import service.map.entity.RailroadMap;

public class RailroadMapTests {
	@Test
	public void testCreate() throws Exception {
		RailroadMap map = RailroadMap.create("AB15,BC4");
		assertNotNull(map);
		assertEquals(3, map.getTowns().size());

		assertEquals(1, map.getTowns().get("A").getRoutes().size());
		assertEquals(15, map.getTowns().get("A").getRoutes().get(0).getWeight());
		assertEquals(map.getTowns().get("B"), map.getTowns().get("A")
				.getRoutes().get(0).getTo());

		assertEquals(1, map.getTowns().get("B").getRoutes().size());
		assertEquals(4, map.getTowns().get("B").getRoutes().get(0).getWeight());
		assertEquals(map.getTowns().get("C"), map.getTowns().get("B")
				.getRoutes().get(0).getTo());

		assertEquals(0, map.getTowns().get("C").getRoutes().size());
	}

	@Test
	public void testDuplicateRoute() throws Exception {
		try {
			RailroadMap.create("AB15,AB4");
		} catch (Exception ex) {
			assertEquals("The route AB is duplicate.", ex.getMessage());
		}
	}

	@Test
	public void testSameTownRoute() throws Exception {
		try {
			RailroadMap.create("AB15,AA4");
		} catch (Exception ex) {
			assertEquals("The start town and end town of route AA are same.",
					ex.getMessage());
		}
	}

	@Test
	public void testSelectAnExistedTownByName() throws Exception {
		RailroadMap map = RailroadMap.create("AB15,BC4");
		Town townB = map.getTownByName("B");
		assertNotNull(townB);
	}

	@Test
	public void testSelectANotExistedTownByName() throws Exception {
		RailroadMap map = RailroadMap.create("AB15,BC4");
		Town townB = map.getTownByName("D");
		assertNull(townB);
	}
}
