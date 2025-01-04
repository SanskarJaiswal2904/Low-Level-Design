package GameArcade;

public class Games {
    private final int gameNumber;
    private final GameTypes gameTypes;
    private boolean isOccupied;

    public Games(int gameNumber, GameTypes gameTypes, boolean isOccupied) {
        this.gameNumber = gameNumber;
        this.gameTypes = gameTypes;
        this.isOccupied = isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public GameTypes getGameTypes() {
        return gameTypes;
    }

    public boolean isOccupied() {
        return isOccupied;
    }
}
