package Data;

public class Player implements Comparable<Player>{
    private final String name;
    private final Integer initiative;

    public Player(String name, Integer initiative) {
        this.name = name;
        this.initiative = initiative;
    }

    public String getName() {
        return name;
    }

    public Integer getInitiative() {
        return initiative;
    }

    @Override
    public int compareTo(Player o) {
        return this.getInitiative().compareTo(o.getInitiative());
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", initiative=" + initiative +
                '}';
    }
}
