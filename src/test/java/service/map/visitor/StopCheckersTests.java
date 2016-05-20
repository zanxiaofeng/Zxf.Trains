package service.map.visitor;

import static org.junit.Assert.*;

import org.junit.Test;

import service.map.entity.Route;
import service.map.entity.Town;
import service.map.visitor.StopCheckers.StopChecker;

public class StopCheckersTests {
	@Test
	public void testNoStop() throws Exception {
		StopChecker stopChecker = StopCheckers.noStop();
		Boolean shouldStop = stopChecker.shouldStop(null);
		assertFalse(shouldStop);
	}

	@Test
	public void testStopWhenThroughaTownTwice() {
		Town a = new Town("A"), b = new Town("B"), c = new Town("C");
		Route a2b = new Route(5, b), b2c = new Route(3, c), b2a = new Route(4,
				a);

		StopChecker stopCheker = StopCheckers.stopWhenThroughaTownTwice();

		// Test 0 stops
		TravelRoute travelRouteWith0Stops = new TravelRoute(a);
		assertEquals(0, travelRouteWith0Stops.getStops());
		Boolean shouldStopAtAWith0Stops = stopCheker
				.shouldStop(travelRouteWith0Stops);
		assertEquals(false, shouldStopAtAWith0Stops);

		// Test 2 stops without twice through
		TravelRoute travelRouteWith2Stops = new TravelRoute(a).newBranch(a2b)
				.newBranch(b2c);
		assertEquals(2, travelRouteWith2Stops.getStops());
		Boolean shouldStopAtAWith2Stops = stopCheker
				.shouldStop(travelRouteWith0Stops);
		assertEquals(false, shouldStopAtAWith2Stops);

		// Test 2 stops with twice through
		TravelRoute travelRouteWith2StopsWithTwiceThrough = new TravelRoute(a)
				.newBranch(a2b).newBranch(b2a);
		assertEquals(2, travelRouteWith2StopsWithTwiceThrough.getStops());
		Boolean shouldStopAtAWith2StopsWithTwiceThrough = stopCheker
				.shouldStop(travelRouteWith2StopsWithTwiceThrough);
		assertEquals(true, shouldStopAtAWith2StopsWithTwiceThrough);
	}

	@Test
	public void testStopWhenReachMaxStops() {
		StopChecker stopCheker = StopCheckers.stopWhenReachMaxStops(3);

		TravelRoute travelRouteWith2Stops = new TravelRoute(new Town("A"))
				.newBranch(new Route(5, null)).newBranch(new Route(4, null));
		Boolean shouldStop1 = stopCheker.shouldStop(travelRouteWith2Stops);
		assertEquals(false, shouldStop1);

		TravelRoute travelRouteWith3Stops = new TravelRoute(new Town("A"))
				.newBranch(new Route(5, null)).newBranch(new Route(4, null))
				.newBranch(new Route(3, null));
		Boolean shouldStop2 = stopCheker.shouldStop(travelRouteWith3Stops);
		assertEquals(true, shouldStop2);
	}
	
	@Test
	public void testStopWhenReachMaxDistance() {
		StopChecker stopCheker = StopCheckers.stopWhenReachMaxDistance(15);

		TravelRoute travelRouteWith2Stops = new TravelRoute(new Town("A"))
				.newBranch(new Route(5, null)).newBranch(new Route(4, null));
		Boolean shouldStop1 = stopCheker.shouldStop(travelRouteWith2Stops);
		assertEquals(false, shouldStop1);

		TravelRoute travelRouteWith3Stops = new TravelRoute(new Town("A"))
				.newBranch(new Route(5, null)).newBranch(new Route(4, null))
				.newBranch(new Route(20, null));
		Boolean shouldStop2 = stopCheker.shouldStop(travelRouteWith3Stops);
		assertEquals(true, shouldStop2);
	}
}
