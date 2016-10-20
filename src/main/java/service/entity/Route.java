package service.entity;

import service.map.visitor.TravelRoute;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Route {
    private List<String> towns;
    private List<Integer> weights;

    public Route(List<String> towns, List<Integer> weights) {
        this.towns = towns;
        this.weights = weights;
    }

    public int getDistance() {
        return weights.stream().mapToInt(weight -> weight.intValue()).sum();
    }

    public String toString() {
        return String.join("-", this.towns);
    }

    public static Route fromTravelRoute(TravelRoute travelRoute) {
        if (travelRoute == null) {
            return null;
        }
        return new Route(travelRoute.getVisitedTownNames(), travelRoute.getVisitedWeights());
    }
}