package service.map.entity;

import java.util.ArrayList;
import java.util.List;

public class Town {
	private String name;
	private List<Route> routes = new ArrayList<Route>();

	public Town(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Route> getRoutes() {
		return routes;
	}
}