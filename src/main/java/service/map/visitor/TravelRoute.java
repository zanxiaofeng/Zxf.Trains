package service.map.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<String> getVisitedTownNames() {
        Stream<String> startTownName = Stream.of(this.getStartTown().getName());
        Stream<String> restTownNames = this.getVisitedRoutes().stream()
                .map(route -> route.getTo().getName());
        return Stream.concat(startTownName, restTownNames).collect(
                Collectors.toList());
    }

    public Town getLastTown() {
        if (visitedRoutes.size() == 0) {
            return startTown;
        }
        int lastIndex = visitedRoutes.size() - 1;
        return visitedRoutes.get(lastIndex).getTo();
    }

    public int getStops() {
        return visitedRoutes.size();
    }

    public int getDistance() {
        return this.visitedRoutes.stream().mapToInt(route -> route.getWeight()).sum();
    }

    public Boolean doesReachStops(int stops) {
        return this.getStops() == stops;
    }

    public Boolean doesReachOrExceedStops(int stops) {
        return this.getStops() >= stops;
    }

    public Boolean doesReachOrExceedDistance(int distance) {
        return this.getDistance() >= distance;
    }

    public Boolean doesThroughaTownTwice() {
        if (!this.hasThroughedAtLeastOneTown()) {
            return false;
        }

        if (this.getStartTown().equals(this.getLastTown())) {
            return true;
        }

        if (this.getVisitedRoutes().stream()
                .limit(this.getVisitedRoutes().size() - 1)
                .anyMatch(route -> route.isEndWith(this.getLastTown()))) {
            return true;
        }

        return false;
    }

    public Boolean hasThroughedAtLeastOneTown() {
        return this.getStops() > 0;
    }

    public Boolean isEndWith(String townName) {
        return this.getLastTown().getName().equals(townName);
    }

    public TravelRoute newBranch(Route route) {
        TravelRoute newRoute = new TravelRoute(this.startTown);
        newRoute.visitedRoutes.addAll(this.visitedRoutes);
        newRoute.visitedRoutes.add(route);
        return newRoute;
    }

    public static int compareByDistance(TravelRoute route1, TravelRoute route2) {
        return Integer.compare(route1.getDistance(), route2.getDistance());
    }
}
