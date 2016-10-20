package service.map.visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

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

    @Test
    public void testDoesReachStops() throws Exception {
        TravelRoute travelRoute = new TravelRoute(new Town("A"));
        travelRoute = travelRoute.newBranch(new Route(5, null))
                .newBranch(new Route(4, null)).newBranch(new Route(3, null));

        assertFalse(travelRoute.doesReachStops(2));
        assertTrue(travelRoute.doesReachStops(3));
        assertFalse(travelRoute.doesReachStops(4));
    }

    @Test
    public void testDoesReachOrExceedStops() throws Exception {
        TravelRoute travelRoute = new TravelRoute(new Town("A"));
        travelRoute = travelRoute.newBranch(new Route(5, null))
                .newBranch(new Route(4, null)).newBranch(new Route(3, null));

        assertTrue(travelRoute.doesReachOrExceedStops(2));
        assertTrue(travelRoute.doesReachOrExceedStops(3));
        assertFalse(travelRoute.doesReachOrExceedStops(4));
    }

    @Test
    public void testDoesReachOrExceedDistance() throws Exception {
        TravelRoute travelRoute = new TravelRoute(new Town("A"));
        travelRoute = travelRoute.newBranch(new Route(5, null))
                .newBranch(new Route(4, null)).newBranch(new Route(3, null));

        assertTrue(travelRoute.doesReachOrExceedDistance(11));
        assertTrue(travelRoute.doesReachOrExceedDistance(12));
        assertFalse(travelRoute.doesReachOrExceedDistance(13));
    }

    @Test
    public void testIsEndWith() throws Exception {
        TravelRoute travelRoute = new TravelRoute(new Town("A"));
        travelRoute = travelRoute.newBranch(new Route(5, null))
                .newBranch(new Route(4, null)).newBranch(new Route(3, new Town("B")));
        assertTrue(travelRoute.isEndWith("B"));
        assertFalse(travelRoute.isEndWith("A"));
    }

    @Test
    public void testHasThroughedAtLeastOneTown() throws Exception {
        TravelRoute travelRoute = new TravelRoute(new Town("A"));
        assertFalse(travelRoute.hasThroughedAtLeastOneTown());

        travelRoute = travelRoute.newBranch(new Route(5, null));
        assertTrue(travelRoute.hasThroughedAtLeastOneTown());

        travelRoute = travelRoute.newBranch(new Route(4, null));
        assertTrue(travelRoute.hasThroughedAtLeastOneTown());
    }
}
