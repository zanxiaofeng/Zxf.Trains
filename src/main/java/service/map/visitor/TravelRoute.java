package service.map.visitor;

import java.util.ArrayList;
import java.util.List;

import service.map.entity.Route;
import service.map.entity.Town;

public class TravelRoute {
	private Town startTown;
	private List<Route> visitedRoutes;

	public TravelRoute(Town startTown) {
		this.startTown = startTown;
		this.visitedRoutes = new ArrayList<>();
	}

	public Town getStartTown() {
		return startTown;
	}

	public List<Route> getVisitedRoutes() {
		return visitedRoutes;
	}

	public Town getLastTown() {
		int lastIndex = visitedRoutes.size() - 1;
		return visitedRoutes.size() > 0 ? visitedRoutes.get(lastIndex).getTo()
				: startTown;
	}

	public int getStops() {
		return visitedRoutes.size();
	}

	public int getDistance() {
		return this.visitedRoutes.stream().mapToInt(route -> route.getWeight())
				.sum();
	}

	public TravelRoute newBranch(Route route) {
		TravelRoute newRoute = new TravelRoute(this.startTown);
		newRoute.visitedRoutes.addAll(this.visitedRoutes);
		newRoute.visitedRoutes.add(route);
		return newRoute;
	}
}
