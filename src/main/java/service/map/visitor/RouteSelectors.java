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
                return byStartEnd.isEndTown(travelRoute) && travelRoute.getStops() == endStops;
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
                return byStartEnd.isEndTown(travelRoute) && !travelRoute.doesReachDistance(maxDistance);
            }
        };
    }

    public static RouteSelector by(final String startTown, final String endTown) {
        return new RouteSelector() {

            @Override
            public Town selectStartTown(RailroadMap map) {
                return map.getTowns().getOrDefault(startTown, null);
            }

            @Override
            public List<Route> selectNextRoutes(TravelRoute travelRoute) {
                return travelRoute.getLastTown().getRoutes();
            }

            @Override
            public Boolean isEndTown(TravelRoute travelRoute) {
                return travelRoute.getStops() > 0
                        && travelRoute.getLastTown().getName().equals(endTown);
            }
        };
    }

    public static RouteSelector by(final String routePath) {
        final String[] steps = routePath.split("-");
        return new RouteSelector() {

            @Override
            public Town selectStartTown(RailroadMap map) {
                return map.getTowns().getOrDefault(steps[0], null);
            }

            @Override
            public List<Route> selectNextRoutes(TravelRoute travelRoute) {
                if (travelRoute.getStops() >= steps.length - 1) {
                    return Arrays.asList();
                }

                String currentStep = steps[travelRoute.getStops() + 1];

                for (Route route : travelRoute.getLastTown().getRoutes()) {
                    if (route.getTo().getName().equals(currentStep)) {
                        return Arrays.asList(route);
                    }
                }

                return Arrays.asList();
            }

            @Override
            public Boolean isEndTown(TravelRoute travelRoute) {
                return travelRoute.getStops() == steps.length - 1
                        && travelRoute.getLastTown().getName()
                        .equals(steps[steps.length - 1]);
            }
        };
    }

    public static interface RouteSelector {
        Town selectStartTown(RailroadMap map);

        List<Route> selectNextRoutes(TravelRoute travelRoute);

        Boolean isEndTown(TravelRoute travelRoute);
    }
}