package TicTacToe;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Play {
    public static void main(String[] args) {
        Player player1 = new Player("Sanskar", 1, 1);
        Player player2 = new Player("Kamya", 2, 2);

        Scanner in = new Scanner(System.in);
        System.out.println("Enter the Grid size:");
        int input = in.nextInt();
        while(input < 0){
            System.out.println("Invalid input.");
            System.out.println("Grid size cannot be negative!");
            System.out.println("Enter positive value.");
            input = in.nextInt();
        }

//        int input = 3;

        Table table = new Table(input);

        Queue<Player> players = new LinkedList<>();
        players.add(player1);
        players.add(player2);

        GameStarted gameStarted = new GameStarted(players ,table);
        gameStarted.startGame();

    }
}
