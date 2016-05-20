package service.map.visitor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import service.map.entity.Route;
import service.map.entity.Town;

public class TravelRouteTests {
	@Test
	public void testCreate() throws Exception {
		Town a = new Town("A");

		TravelRoute travelRoute = new TravelRoute(a);
		assertEquals(a, travelRoute.getStartTown());
		assertEquals(a, travelRoute.getLastTown());
		assertEquals(0, travelRoute.getVisitedRoutes().size());
	}

	@Test
	public void testNewBranch() throws Exception {
		Town a = new Town("A"), b = new Town("B");

		TravelRoute travelRoute = new TravelRoute(a);
		assertEquals(0, travelRoute.getVisitedRoutes().size());

		travelRoute = travelRoute.newBranch(new Route(5, b));
		assertEquals(b, travelRoute.getLastTown());
		assertEquals(1, travelRoute.getVisitedRoutes().size());
	}

	@Test
	public void testGetDistance() throws Exception {
		TravelRoute travelRoute = new TravelRoute(null);
		assertEquals(0, travelRoute.getDistance());

		travelRoute = travelRoute.newBranch(new Route(5, null))
				.newBranch(new Route(4, null)).newBranch(new Route(3, null));
		assertEquals(12, travelRoute.getDistance());
	}

	@Test
	public void testGetStops() throws Exception {
		TravelRoute travelRoute = new TravelRoute(new Town("A"));
		assertEquals(0, travelRoute.getStops());

		travelRoute = travelRoute.newBranch(new Route(5, null))
				.newBranch(new Route(4, null)).newBranch(new Route(3, null));
		assertEquals(3, travelRoute.getStops());
	}
}
