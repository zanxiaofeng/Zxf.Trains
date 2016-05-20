package service.map.visitor;

import java.util.ArrayList;
import java.util.List;

import service.map.entity.RailroadMap;
import service.map.entity.Route;
import service.map.entity.Town;
import service.map.visitor.Selectors.Selector;
import service.map.visitor.StopCheckers.StopChecker;

public class Visitor {
	private Selector selector;
	private StopChecker stopChecker;

	public Visitor(Selector selector, StopChecker stopChecker) {
		this.selector = selector;
		this.stopChecker = stopChecker;
	}

	public List<TravelRoute> visit(RailroadMap map) {
		Town startTown = selector.selectStartTown(map);
		if (startTown == null) {
			return new ArrayList<>();
		}
		return visit(new TravelRoute(startTown));
	}

	private List<TravelRoute> visit(TravelRoute travelRoute) {
		List<TravelRoute> returnRoutes = new ArrayList<>();

		if (selector.isEndTown(travelRoute)) {
			returnRoutes.add(travelRoute);
		}

		if (stopChecker.shouldStop(travelRoute)) {
			return returnRoutes;
		}

		for (Route route : selector.selectRoutes(travelRoute)) {
			returnRoutes.addAll(visit(travelRoute.newBranch(route)));
		}

		return returnRoutes;
	}
}
