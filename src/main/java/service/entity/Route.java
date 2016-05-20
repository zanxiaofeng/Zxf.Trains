package service.entity;

import java.util.List;

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
		return this.towns.stream().reduce("",
				(result, name) -> result == "" ? name : result + "-" + name);
	}
}