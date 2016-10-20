package service.map.entity;

public class Route {
    private int weight;
    private Town to;

    public Route(int weight, Town to) {
        this.weight = weight;
        this.to = to;
    }

    public int getWeight() {
        return weight;
    }

    public Town getTo() {
        return to;
    }

    public Boolean isEndWith(Town town) {
        return this.getTo().equals(town);
    }

    public Boolean isEndWith(String townName) {
        return this.getTo().getName().equals(townName);
    }
}