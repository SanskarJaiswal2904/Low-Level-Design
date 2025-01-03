package SnakeAndLadder;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class GameStarted {
    private Queue<Player> players;
    private BoardSize boardSize;
    private List<Jumper> jumpers;
    private Dice dice;

    public GameStarted(Queue<Player> players, BoardSize boardSize, List<Jumper> jumpers, Dice dice) {
        this.players = players;
        this.boardSize = boardSize;
        this.jumpers = jumpers;
        this.dice = dice;
    }

    public void StartGame(){
        System.out.println("Game Started...");
        int TotalSides = boardSize.getSize();
        List<String> rank = new ArrayList<>();

        while(players.size() > 1){
            Player currentPlayer = players.poll();
            String currentPlayerName = currentPlayer.getName();

            int diceRollValue = dice.roll();
            System.out.println("\nRolling Dice.. Face:- "+ diceRollValue);

            int nextPosition = currentPlayer.getPlayerPosition() + diceRollValue;

            if(nextPosition > TotalSides){
                System.out.println(currentPlayerName + " is standing at " + currentPlayer.getPlayerPosition());
                System.out.println(currentPlayerName + " rolled " + diceRollValue + " but can't move.");
                players.offer(currentPlayer);
                continue;
            }

            for(Jumper snake : jumpers){
                if(snake.getStartPoint() == nextPosition && !snake.isLadder()){
                    System.out.println(currentPlayerName + " encountered a snake at " + nextPosition + " and is sent to " + snake.getEndPoint());
                    nextPosition = snake.getEndPoint();
                }
            }
            for(Jumper ladder : jumpers){
                if(ladder.getStartPoint() == nextPosition && ladder.isLadder()){
                    System.out.println(currentPlayerName + " climbed a ladder from " + nextPosition + " to " + ladder.getEndPoint());
                    nextPosition = ladder.getEndPoint();
                }
            }

            System.out.println(currentPlayerName + " moved to position " + nextPosition);

            currentPlayer.setPlayerPosition(nextPosition);

            if(nextPosition == TotalSides){
                System.out.println(currentPlayerName + " wins the game!");
                rank.add(currentPlayerName);
                continue;
            }

            players.offer(currentPlayer);

        }
        if(players.size() == 1){
            System.out.println("\n"+players.poll().getName() + " couldn't finish the game.");
        }
        System.out.println("\nWinners of the match are:");

        for(int i = 0; i < rank.size(); i++){
            int rankNumber = i+1;
            System.out.print("Rank: "+ rankNumber + ". " + rank.get(i) + "\n");
        }
        System.out.println("\nGame Finished.");
    }

}
