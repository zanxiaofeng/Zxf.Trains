package service.map.visitor;

public class StopCheckers {
    public static StopChecker noStop() {
        return travelRoute -> false;
    }

    public static StopChecker stopWhenThroughaTownTwice() {
        return travelRoute -> travelRoute.doesThroughaTownTwice();
    }

    public static StopChecker stopWhenReachMaxStops(final int maxStops) {
        return travelRoute -> travelRoute.doesReachOrExceedStops(maxStops);
    }

    public static StopChecker stopWhenReachMaxDistance(final int maxDistance) {
        return travelRoute -> travelRoute.doesReachOrExceedDistance(maxDistance);
    }

    public static interface StopChecker {
        Boolean shouldStop(TravelRoute travelRoute);
    }
}