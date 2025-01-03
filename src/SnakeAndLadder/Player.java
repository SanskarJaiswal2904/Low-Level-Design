package SnakeAndLadder;

public class Player {
    private int id;
    private String name;

    private int playerPosition;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.playerPosition = 0;
    }

    public String getName() {
        return name;
    }

    public int getPlayerPosition() {
        return playerPosition;
    }

    public int getId() {
        return id;
    }

    public void setPlayerPosition(int playerPosition) {
        this.playerPosition = playerPosition;
    }
}
