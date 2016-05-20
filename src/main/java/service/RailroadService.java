package service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import service.entity.Route;
import service.map.entity.RailroadMap;
import service.map.visitor.Selectors;
import service.map.visitor.StopCheckers;
import service.map.visitor.TravelRoute;
import service.map.visitor.Visitor;

public class RailroadService {
	private RailroadMap map;

	public RailroadService(String mapData) throws Exception {
		map = RailroadMap.create(mapData);
	}

	public Route getRoute(String routePath) {
		Visitor visitor = new Visitor(Selectors.by(routePath),
				StopCheckers.noStop());
		return transformFromTravelRoute(visitor.visit(map).stream().findFirst()
				.orElse(null));
	}

	public List<Route> getRoutesByMaxStops(String startTown, String endTown,
			int maxStops) {
		Visitor visitor = new Visitor(Selectors.by(startTown, endTown),
				StopCheckers.stopWhenReachMaxStops(maxStops));
		return visitor.visit(map).stream().map(this::transformFromTravelRoute)
				.collect(Collectors.toList());
	}

	public List<Route> getRoutesByFixedStops(String startTown, String endTown,
			int endStops) {
		Visitor visitor = new Visitor(
				Selectors.by(startTown, endTown, endStops),
				StopCheckers.stopWhenReachMaxStops(endStops));
		return visitor.visit(map).stream().map(this::transformFromTravelRoute)
				.collect(Collectors.toList());
	}

	public List<Route> getRoutesByMaxDistance(String startTown, String endTown,
			int maxDistance) {
		Visitor visitor = new Visitor(Selectors.byMaxDistance(startTown,
				endTown, maxDistance),
				StopCheckers.stopWhenReachMaxDistance(maxDistance));
		return visitor.visit(map).stream().map(this::transformFromTravelRoute)
				.collect(Collectors.toList());
	}

	public Route getShortestRoute(String startTown, String endTown) {
		Visitor visitor = new Visitor(Selectors.by(startTown, endTown),
				StopCheckers.stopWhenThroughaTownTwice());
		return transformFromTravelRoute(visitor.visit(map).stream()
				.min(this::compareTravelRoute).orElse(null));
	}

	private Route transformFromTravelRoute(TravelRoute travelRoute) {
		if (travelRoute == null) {
			return null;
		}
		Stream<String> startTown = Stream.of(travelRoute.getStartTown()
				.getName());
		Stream<String> restTowns = travelRoute.getVisitedRoutes().stream()
				.map(route -> route.getTo().getName());

		List<String> towns = Stream.concat(startTown, restTowns).collect(
				Collectors.toList());

		List<Integer> weights = travelRoute.getVisitedRoutes().stream()
				.map(route -> route.getWeight()).collect(Collectors.toList());
		return new Route(towns, weights);
	}

	private int compareTravelRoute(TravelRoute route1, TravelRoute route2) {
		return Integer.compare(route1.getDistance(), route2.getDistance());
	}
}
