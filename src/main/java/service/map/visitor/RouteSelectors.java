package service.map.visitor;

import java.util.Arrays;
import java.util.List;

import service.map.entity.RailroadMap;
import service.map.entity.Route;
import service.map.entity.Town;

public class RouteSelectors {
    public static RouteSelector by(final String startTown, final String endTown, final int endStops) {
        return new RouteSelector() {
            private RouteSelector byStartEnd = by(startTown, endTown);

            @Override
            public Town selectStartTown(RailroadMap map) {
                return byStartEnd.selectStartTown(map);
            }


            @Override
            public List<Route> selectNextRoutes(TravelRoute travelRoute) {
                return byStartEnd.selectNextRoutes(travelRoute);
            }

            @Override
            public Boolean isEndTown(TravelRoute travelRoute) {
                return byStartEnd.isEndTown(travelRoute) && travelRoute.doesReachStops(endStops);
            }
        };
    }

    public static RouteSelector byMaxDistance(final String startTown, final String endTown, final int maxDistance) {
        return new RouteSelector() {
            private RouteSelector byStartEnd = by(startTown, endTown);

            @Override
            public Town selectStartTown(RailroadMap map) {
                return byStartEnd.selectStartTown(map);
            }

            @Override
            public List<Route> selectNextRoutes(TravelRoute travelRoute) {
                return byStartEnd.selectNextRoutes(travelRoute);
            }

            @Override
            public Boolean isEndTown(TravelRoute travelRoute) {
                return byStartEnd.isEndTown(travelRoute) && !travelRoute.doesReachOrExceedDistance(maxDistance);
            }
        };
    }

    public static RouteSelector by(final String startTown, final String endTown) {
        return new RouteSelector() {

            @Override
            public Town selectStartTown(RailroadMap map) {
                return map.getTownByName(startTown);
            }

            @Override
            public List<Route> selectNextRoutes(TravelRoute travelRoute) {
                return travelRoute.getLastTown().getRoutes();
            }

            @Override
            public Boolean isEndTown(TravelRoute travelRoute) {
                return travelRoute.hasThroughedAtLeastOneTown() && travelRoute.isEndWith(endTown);
            }
        };
    }

    public static RouteSelector by(final String routePath) {
        final String[] steps = routePath.split("-");
        final int stops = steps.length - 1;
        final String startStep = steps[0];
        final String endStep = steps[steps.length - 1];

        return new RouteSelector() {

            @Override
            public Town selectStartTown(RailroadMap map) {
                return map.getTownByName(startStep);
            }

            @Override
            public List<Route> selectNextRoutes(TravelRoute travelRoute) {
                if (travelRoute.doesReachOrExceedStops(stops)) {
                    return Arrays.asList();
                }

                String nextStep = steps[travelRoute.getStops() + 1];
                return travelRoute.getLastTown().selectNextRoutesByNextTownName(nextStep);
            }

            @Override
            public Boolean isEndTown(TravelRoute travelRoute) {
                return travelRoute.doesReachStops(stops) && travelRoute.isEndWith(endStep);
            }
        };
    }

    public static interface RouteSelector {
        Town selectStartTown(RailroadMap map);

        List<Route> selectNextRoutes(TravelRoute travelRoute);

        Boolean isEndTown(TravelRoute travelRoute);
    }
}