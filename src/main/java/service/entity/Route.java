package service.entity;

import service.map.visitor.TravelRoute;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Route {
    private List<String> towns;
    private int distance;

    public Route(List<String> towns, int distance) {
        this.towns = towns;
        this.distance = distance;
    }

    public int getDistance() {
        return this.distance;
    }

    public String toString() {
        return String.join("-", this.towns);
    }

    public static Route fromTravelRoute(TravelRoute travelRoute) {
        if (travelRoute == null) {
            return null;
        }
        return new Route(travelRoute.getVisitedTownNames(), travelRoute.getDistance());
    }
}