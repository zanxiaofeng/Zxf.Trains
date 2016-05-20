package service.map.visitor;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import service.map.entity.RailroadMap;
import service.map.entity.Route;
import service.map.entity.Town;
import service.map.visitor.Selectors.Selector;

public class SelectorsTests {
	@Test
	public void testSelectStartTown() throws Exception {
		Town a = new Town("A"), b = new Town("B"), c = new Town("C"), d = new Town(
				"D"), e = new Town("E");
		RailroadMap map = new RailroadMap(Arrays.asList(a, b, c, d, e).stream()
				.collect(Collectors.toMap(t -> t.getName(), t -> t)));

		Selector selStartA = Selectors.by("A", "");
		Town startTownA = selStartA.selectStartTown(map);
		assertEquals(a, startTownA);

		Selector selStartB = Selectors.by("B", "", 0);
		Town startTownH = selStartB.selectStartTown(map);
		assertEquals(b, startTownH);

		Selector selStartC = Selectors.by("C-B-A");
		Town startTownC = selStartC.selectStartTown(map);
		assertEquals(c, startTownC);

		Selector selStartD = Selectors.byMaxDistance("C", "", 0);
		Town startTownD = selStartD.selectStartTown(map);
		assertEquals(c, startTownD);

		Selector selStartZ = Selectors.by("Z", "", 0);
		Town startTownZ = selStartZ.selectStartTown(map);
		assertNull(startTownZ);
	}

	@Test
	public void testIsEndTown() throws Exception {
		Town a = new Town("A"), b = new Town("B"), c = new Town("C"), d = new Town(
				"D"), e = new Town("E");

		Selector selA2B = Selectors.by("A", "B");
		// End town is B.
		Boolean isEndTown1 = selA2B.isEndTown(new TravelRoute(a)
				.newBranch(new Route(5, b)));
		assertEquals(true, isEndTown1);
		// End town is not B.
		Boolean isEndTown2 = selA2B.isEndTown(new TravelRoute(a)
				.newBranch(new Route(4, c)));
		assertEquals(false, isEndTown2);

		Selector selA2CWithMaxStops = Selectors.by("A", "C", 2);
		// Stops is less than 2 and end town is C.
		Boolean isEndTown3 = selA2CWithMaxStops.isEndTown(new TravelRoute(a)
				.newBranch(new Route(5, c)));
		assertEquals(false, isEndTown3);
		// Stops is 2 and end town is C.
		Boolean isEndTown4 = selA2CWithMaxStops.isEndTown(new TravelRoute(a)
				.newBranch(new Route(5, b)).newBranch(new Route(4, c)));
		assertEquals(true, isEndTown4);
		// Stop is greater than 2 and end town is C.
		Boolean isEndTown5 = selA2CWithMaxStops.isEndTown(new TravelRoute(a)
				.newBranch(new Route(5, b)).newBranch(new Route(4, d))
				.newBranch(new Route(3, c)));
		assertEquals(false, isEndTown5);

		Selector selA2B2C = Selectors.by("A-B-C");
		// Stops is less than 3 and end town is not C.
		Boolean isEndTown6 = selA2B2C.isEndTown(new TravelRoute(a)
				.newBranch(new Route(5, d)));
		assertEquals(false, isEndTown6);
		// Stops is 3 and end town is C.
		Boolean isEndTown7 = selA2B2C.isEndTown(new TravelRoute(a).newBranch(
				new Route(5, d)).newBranch(new Route(4, c)));
		assertEquals(true, isEndTown7);
		// Stops is greater than 3 and end town is C.
		Boolean isEndTown8 = selA2B2C.isEndTown(new TravelRoute(a)
				.newBranch(new Route(5, d)).newBranch(new Route(5, e))
				.newBranch(new Route(4, c)));
		assertEquals(false, isEndTown8);

		Selector selA2CWithMaxDistance = Selectors.byMaxDistance("A", "C", 8);
		// End town is C and distance is less than 8
		Boolean isEndTown9 = selA2CWithMaxDistance.isEndTown(new TravelRoute(a)
				.newBranch(new Route(5, c)));
		assertEquals(true, isEndTown9);

		// End town is C and distance is greater than 8
		Boolean isEndTown10 = selA2CWithMaxDistance
				.isEndTown(new TravelRoute(a).newBranch(new Route(5, b))
						.newBranch(new Route(4, c)));
		assertEquals(false, isEndTown10);

		// End town is not C and distance is less than 8
		Boolean isEndTown11 = selA2CWithMaxDistance
				.isEndTown(new TravelRoute(a).newBranch(new Route(5, b))
						.newBranch(new Route(2, d)));
		assertEquals(false, isEndTown11);
	}

	@Test
	public void testSelectRoutes() {
		Town a = new Town("A"), b = new Town("B"), c = new Town("C");
		Route ab = new Route(5, b), ac = new Route(4, c), bc = new Route(3, c);
		a.getRoutes().add(ab);
		a.getRoutes().add(ac);
		b.getRoutes().add(bc);

		Selector sel1 = Selectors.by("", "");
		List<Route> routes1 = sel1.selectRoutes(new TravelRoute(a));
		assertEquals(a.getRoutes(), routes1);

		List<Route> routes2 = sel1.selectRoutes(new TravelRoute(a)
				.newBranch(ab));
		assertEquals(b.getRoutes(), routes2);

		Selector sel2 = Selectors.by("", "", 0);
		List<Route> routes3 = sel2.selectRoutes(new TravelRoute(a));
		assertEquals(a.getRoutes(), routes3);

		List<Route> routes4 = sel2.selectRoutes(new TravelRoute(a)
				.newBranch(ab));
		assertEquals(b.getRoutes(), routes4);

		Selector sel3 = Selectors.byMaxDistance("", "", 0);
		List<Route> routes5 = sel3.selectRoutes(new TravelRoute(a));
		assertEquals(a.getRoutes(), routes5);

		List<Route> routes6 = sel3.selectRoutes(new TravelRoute(a)
				.newBranch(ab));
		assertEquals(b.getRoutes(), routes6);
	}
}
