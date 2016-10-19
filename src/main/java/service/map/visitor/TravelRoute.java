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
        return this.getStops() >= stops;
    }

    public Boolean doesReachDistance(int distance) {
        return this.getDistance() >= distance;
    }

    public Boolean doesThroughaTownTwice() {
        if (this.getStops() < 1) {
            return false;
        }

        if (this.getStartTown().equals(this.getLastTown())) {
            return true;
        }

        if (this.getVisitedRoutes().stream()
                .limit(this.getVisitedRoutes().size() - 1)
                .anyMatch(route -> route.getTo().equals(this.getLastTown()))) {
            return true;
        }

        return false;
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
