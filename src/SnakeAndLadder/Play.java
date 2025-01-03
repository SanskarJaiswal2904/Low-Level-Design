package SnakeAndLadder;

import java.util.*;
//exception : if number of dice is 2 player getting stuck at 99 is more likely

public class Play {
    public static void main(String[] args) {
        Player Sanskar = new Player(1, "Sanskar");
        Player Shubham = new Player(2, "Shubham");
        Player Aryan = new Player(3, "Aryan");
        Player Rachna = new Player(4, "Rachna");

        Queue<Player> playerList = new LinkedList<>();
        playerList.add(Sanskar);
        playerList.add(Shubham);
        playerList.add(Aryan);
        playerList.add(Rachna);

        Scanner in = new Scanner(System.in);
        System.out.println("Enter total number of squares: ");
        int TotalPlayingFeild = in.nextInt();

        BoardSize boardSize = new BoardSize(TotalPlayingFeild);


        System.out.println("Enter total number of dice: ");
        int noOfDice = in.nextInt();
        Dice dice = new Dice(noOfDice);

        List<Jumper> jumperList = new ArrayList<>();

        // Add ladders
        Jumper ladder = new Jumper(3,65, true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(1, 38,true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(4, 14,true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(9, 31,true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(21, 42,true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(28, 84,true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(36, 44,true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(51, 67,true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(71, 91,true, boardSize.getSize());
        jumperList.add(ladder);
        ladder = new Jumper(80, 100,true, boardSize.getSize());
        jumperList.add(ladder);


        // Add snakes
        Jumper snake = new Jumper(86,1, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(16, 6, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(47, 26, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(49, 11, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(56, 53, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(62, 19, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(64, 60, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(87, 24, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(93, 73, false, boardSize.getSize());
        jumperList.add(snake);
        snake = new Jumper(95, 75, false, boardSize.getSize());
        jumperList.add(snake);
//        snake = new Jumper(100, 5, false, boardSize.getSize());         jumperList.add(snake); //Custom Exception
        snake = new Jumper(98, 78, false, boardSize.getSize());
        jumperList.add(snake);

        GameStarted gameStarted = new GameStarted(playerList,boardSize,jumperList,dice);
        gameStarted.StartGame();


    }
}