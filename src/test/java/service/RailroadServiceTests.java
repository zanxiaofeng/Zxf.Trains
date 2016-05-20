package service;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import service.entity.Route;

public class RailroadServiceTests {
	private RailroadService service;

	public RailroadServiceTests() throws Exception {
		service = new RailroadService("AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");
	}

	@Test
	public void testGetRoute() throws Exception {
		assertEquals(9, service.getRoute("A-B-C").getDistance());

		assertEquals(5, service.getRoute("A-D").getDistance());
		assertEquals(13, service.getRoute("A-D-C").getDistance());
		assertEquals(22, service.getRoute("A-E-B-C-D").getDistance());
		assertNull(service.getRoute("A-E-D"));
	}

	@Test
	public void testGetRoutesByMaxStops() throws Exception {
		List<Route> routes1 = service.getRoutesByMaxStops("C", "C", 3);
		assertEquals(2, routes1.size());
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-D-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-E-B-C")));
	}

	@Test
	public void testGetRoutesByFixedStops() throws Exception {
		List<Route> routes1 = service.getRoutesByFixedStops("A", "C", 4);
		assertEquals(3, routes1.size());
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("A-B-C-D-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("A-D-C-D-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("A-D-E-B-C")));
	}

	@Test
	public void testGetRoutesByMaxDistance() throws Exception {
		List<Route> routes1 = service.getRoutesByMaxDistance("C", "C", 30);
		assertEquals(7, routes1.size());

		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-D-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-E-B-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-E-B-C-D-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-D-C-E-B-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-D-E-B-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-E-B-C-E-B-C")));
		Assert.assertTrue(routes1.stream().anyMatch(
				route -> route.toString().equals("C-E-B-C-E-B-C-E-B-C")));
	}

	@Test
	public void testGetShortestRoute() throws Exception {
		Route route = service.getShortestRoute("A", "C");
		assertEquals(9, route.getDistance());

		Route route1 = service.getShortestRoute("B", "B");
		assertEquals(9, route1.getDistance());
	}

}