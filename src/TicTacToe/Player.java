package TicTacToe;

public class Player {
    private String name;
    private int id;

    //1 -> x
    //2 -> o
    //3 -> " "
    private int peice;

    public Player(String name, int id, int peice) {
        this.name = name;
        this.id = id;
        this.peice = peice;
    }

    public String getName() {
        return name;
    }

    public int getPeice() {
        return peice;
    }
}
