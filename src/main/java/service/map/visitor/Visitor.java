package service.map.visitor;

import java.util.ArrayList;
import java.util.List;

import service.map.entity.RailroadMap;
import service.map.entity.Route;
import service.map.entity.Town;
import service.map.visitor.RouteSelectors.RouteSelector;
import service.map.visitor.StopCheckers.StopChecker;

public class Visitor {
	private RouteSelector routeSelector;
	private StopChecker stopChecker;

	public Visitor(RouteSelector routeSelector, StopChecker stopChecker) {
		this.routeSelector = routeSelector;
		this.stopChecker = stopChecker;
	}

	public List<TravelRoute> visit(RailroadMap map) {
		Town startTown = routeSelector.selectStartTown(map);
		if (startTown == null) {
			return new ArrayList<>();
		}
		return visit(new TravelRoute(startTown));
	}

	private List<TravelRoute> visit(TravelRoute travelRoute) {
		List<TravelRoute> returnRoutes = new ArrayList<>();

		if (routeSelector.isEndTown(travelRoute)) {
			returnRoutes.add(travelRoute);
		}

		if (stopChecker.shouldStop(travelRoute)) {
			return returnRoutes;
		}

		for (Route route : routeSelector.selectNextRoutes(travelRoute)) {
			TravelRoute newBranch = travelRoute.newBranch(route);
			returnRoutes.addAll(visit(newBranch));
		}

		return returnRoutes;
	}
}
