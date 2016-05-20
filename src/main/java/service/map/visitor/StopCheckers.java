package service.map.visitor;

public class StopCheckers {
	public static StopChecker noStop() {
		return new StopChecker() {
			@Override
			public Boolean shouldStop(TravelRoute travelRoute) {
				return false;
			}
		};
	}

	public static StopChecker stopWhenThroughaTownTwice() {
		return new StopChecker() {

			@Override
			public Boolean shouldStop(TravelRoute travelRoute) {
				if (travelRoute.getStops() < 1) {
					return false;
				}

				if (travelRoute.getStartTown()
						.equals(travelRoute.getLastTown())) {
					return true;
				}

				if (travelRoute
						.getVisitedRoutes()
						.stream()
						.limit(travelRoute.getVisitedRoutes().size() - 1)
						.anyMatch(
								route -> route.getTo().equals(
										travelRoute.getLastTown()))) {
					return true;
				}

				return false;
			}
		};
	}

	public static StopChecker stopWhenReachMaxStops(final int maxStops) {
		return new StopChecker() {
			@Override
			public Boolean shouldStop(TravelRoute travelRoute) {
				return travelRoute.getStops() >= maxStops;
			}
		};
	}

	public static StopChecker stopWhenReachMaxDistance(final int maxDistance) {
		return new StopChecker() {
			@Override
			public Boolean shouldStop(TravelRoute travelRoute) {
				return travelRoute.getDistance() >= maxDistance;
			}
		};
	}

	public static interface StopChecker {
		Boolean shouldStop(TravelRoute travelRoute);
	}
}