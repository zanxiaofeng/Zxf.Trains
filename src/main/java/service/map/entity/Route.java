package service.map.entity;

public class Route {
	private int weight;
	private Town to;
	
	public Route(int weight,Town to){
		this.weight = weight;
		this.to = to;
	}

	public int getWeight() {
		return weight;
	}

	public Town getTo() {
		return to;
	}
}