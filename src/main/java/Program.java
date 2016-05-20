import java.util.Arrays;
import java.util.List;

import service.RailroadService;
import service.entity.Route;

public class Program {
	public static void main(String[] args) throws Exception {
		RailroadService service = new RailroadService(
				"AB5,BC4,CD8,DC8,DE6,AD5,CE2,EB3,AE7");

		Arrays.asList("A-B-C", "A-D", "A-D-C", "A-E-B-C-D", "A-E-D").stream()
				.forEach(routePath -> {
					Route route = service.getRoute(routePath);
					if (route == null) {
						System.out.println("NO SUCH ROUTE");
					} else {
						System.out.println(route.getDistance());
					}
				});

		List<Route> routesCtoC = service.getRoutesByMaxStops("C", "C", 3);
		System.out.println(routesCtoC.size());

		List<Route> routesAtoC = service.getRoutesByFixedStops("A", "C", 4);
		System.out.println(routesAtoC.size());

		Route routeAtoC = service.getShortestRoute("A", "C");
		System.out.println(routeAtoC.getDistance());

		Route routeBtoB = service.getShortestRoute("B", "B");
		System.out.println(routeBtoB.getDistance());

		List<Route> routesCtoCLessThan30 = service.getRoutesByMaxDistance("C",
				"C", 30);
		System.out.println(routesCtoCLessThan30.size());
	}
}
