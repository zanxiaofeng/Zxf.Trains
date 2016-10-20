package service.map.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Town {
    private String name;
    private List<Route> routes = new ArrayList<>();

    public Town(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<Route> selectNextRoutesByNextTownName(String nextTownName) {
        for (Route route : this.getRoutes()) {
            if (route.isEndWith(nextTownName)) {
                return Arrays.asList(route);
            }
        }
        return Arrays.asList();
    }
}