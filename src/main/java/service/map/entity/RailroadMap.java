package service.map.entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RailroadMap {
	private Map<String, Town> towns;

	public RailroadMap(Map<String, Town> towns) {
		this.towns = towns;
	}

	public Map<String, Town> getTowns() {
		return towns;
	}

	public static RailroadMap create(String mapData) throws Exception {
		Map<String, Town> towns = new HashMap<>();
		Set<String> routes = new HashSet<>();
		for (String routeData : mapData.split(",")) {
			String firstTown = routeData.substring(0, 1);
			String secondTown = routeData.substring(1, 2);
			Integer weight = Integer.parseInt(routeData.substring(2));

			String routePath = routeData.substring(0, 2);
			if (routes.contains(routePath)) {
				throw new Exception("The route " + routePath + " is duplicate.");
			}
			routes.add(routePath);

			if (firstTown.endsWith(secondTown)) {
				throw new Exception("The start town and end town of route "
						+ routePath + " are same.");
			}

			if (!towns.containsKey(firstTown)) {
				towns.put(firstTown, new Town(firstTown));
			}

			if (!towns.containsKey(secondTown)) {
				towns.put(secondTown, new Town(secondTown));
			}

			Route route = new Route(weight, towns.get(secondTown));
			towns.get(firstTown).getRoutes().add(route);
		}

		return new RailroadMap(towns);
	}
}
