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
                return travelRoute.doesThroughaTownTwice();
            }
        };
    }

    public static StopChecker stopWhenReachMaxStops(final int maxStops) {
        return new StopChecker() {
            @Override
            public Boolean shouldStop(TravelRoute travelRoute) {
                return travelRoute.doesReachOrExceedStops(maxStops);
            }
        };
    }

    public static StopChecker stopWhenReachMaxDistance(final int maxDistance) {
        return new StopChecker() {
            @Override
            public Boolean shouldStop(TravelRoute travelRoute) {
                return travelRoute.doesReachOrExceedDistance(maxDistance);
            }
        };
    }

    public static interface StopChecker {
        Boolean shouldStop(TravelRoute travelRoute);
    }
}