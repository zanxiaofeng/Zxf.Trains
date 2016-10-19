package service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import service.entity.Route;
import service.map.entity.RailroadMap;
import service.map.visitor.RouteSelectors;
import service.map.visitor.StopCheckers;
import service.map.visitor.TravelRoute;
import service.map.visitor.Visitor;

public class RailroadService {
	private RailroadMap map;

	public RailroadService(String mapData) throws Exception {
		map = RailroadMap.create(mapData);
	}

	public Route getRoute(String routePath) {
		Visitor visitor = new Visitor(RouteSelectors.by(routePath), StopCheckers.noStop());
		TravelRoute travelRoute = visitor.visit(map).stream().findFirst().orElse(null);
		return Route.fromTravelRoute(travelRoute);
	}

	public List<Route> getRoutesByMaxStops(String startTown, String endTown, int maxStops) {
		Visitor visitor = new Visitor(RouteSelectors.by(startTown, endTown),
				StopCheckers.stopWhenReachMaxStops(maxStops));
		Stream<TravelRoute> travelRoutes = visitor.visit(map).stream();
		return travelRoutes.map(Route::fromTravelRoute)
				.collect(Collectors.toList());
	}

	public List<Route> getRoutesByFixedStops(String startTown, String endTown,
			int endStops) {
		Visitor visitor = new Visitor(RouteSelectors.by(startTown, endTown, endStops),
				StopCheckers.stopWhenReachMaxStops(endStops));
		Stream<TravelRoute> travelRoutes = visitor.visit(map).stream();
		return travelRoutes.map(Route::fromTravelRoute)
				.collect(Collectors.toList());
	}

	public List<Route> getRoutesByMaxDistance(String startTown, String endTown,
			int maxDistance) {
		Visitor visitor = new Visitor(RouteSelectors.byMaxDistance(startTown, endTown, maxDistance),
				StopCheckers.stopWhenReachMaxDistance(maxDistance));
		Stream<TravelRoute> travelRoutes = visitor.visit(map).stream();
		return travelRoutes.map(Route::fromTravelRoute)
				.collect(Collectors.toList());
	}

	public Route getShortestRoute(String startTown, String endTown) {
		Visitor visitor = new Visitor(RouteSelectors.by(startTown, endTown),
				StopCheckers.stopWhenThroughaTownTwice());
		TravelRoute travelRoute = visitor.visit(map).stream()
				.min(TravelRoute::compareByDistance).orElse(null);
		return Route.fromTravelRoute(travelRoute);
	}
}
