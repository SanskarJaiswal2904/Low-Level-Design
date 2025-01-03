package SnakeAndLadder;
// This is the demo from chatGPT
import java.util.*;

class SnakeAndLadderGame {
    private static final int BOARD_SIZE = 1000;
    private Map<Integer, Integer> snakes;
    private Map<Integer, Integer> ladders;
    private Queue<Player> players;
    private Map<String, Integer> playerPositions;

    public SnakeAndLadderGame() {
        snakes = new HashMap<>();
        ladders = new HashMap<>();
        players = new LinkedList<>();
        playerPositions = new HashMap<>();
    }

    public void addSnake(int head, int tail) {
        if(head == BOARD_SIZE){
            throw new ImproperLocationOfSnake();
        }
        snakes.put(head, tail);
    }

    public void addLadder(int start, int end) {
        ladders.put(start, end);
    }

    public void addPlayer(String name) {
        players.add(new Player(name));
        playerPositions.put(name, 0);
    }

    private int rollDice() {
        int val = new Random().nextInt(6) + 1;
        System.out.println("\nRolling Dice.. Face:- "+ val);
        return val;
    }

    static class Player {
        private final String name;

        public Player(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static void main(String[] args) {
        SnakeAndLadderGame game = new SnakeAndLadderGame();

        // Add snakes
        game.addSnake(16, 6);
        game.addSnake(47, 26);
        game.addSnake(49, 11);
        game.addSnake(56, 53);
        game.addSnake(62, 19);
        game.addSnake(64, 60);
        game.addSnake(87, 24);
        game.addSnake(93, 73);
        game.addSnake(95, 75);
//        game.addSnake(100, 5); //Custom Exception
        game.addSnake(98, 78);

        // Add ladders
        game.addLadder(1, 38);
        game.addLadder(4, 14);
        game.addLadder(9, 31);
        game.addLadder(21, 42);
        game.addLadder(28, 84);
        game.addLadder(36, 44);
        game.addLadder(51, 67);
        game.addLadder(71, 91);
        game.addLadder(80, 100);

        // Add players
        game.addPlayer("Alice");
        game.addPlayer("Bob");
        game.addPlayer("Charlie");
        game.addPlayer("Human");
        game.addPlayer("Fish");
        game.addPlayer("Dog");
        game.addPlayer("Lion");

        // Start the game
        game.startGame();
    }
    public void startGame() {
        System.out.println("Game Started!");
        List<String> rank = new ArrayList<>();

        while (players.size() > 1) {
            Player currentPlayer = players.poll();
            String playerName = currentPlayer.getName();
            int currentPosition = playerPositions.get(playerName);

            int diceRoll = rollDice();
            int nextPosition = currentPosition + diceRoll;

            if (nextPosition > BOARD_SIZE) {
                System.out.println(playerName + " is standing at " + currentPosition);
                System.out.println(playerName + " rolled " + diceRoll + " but can't move.");
                players.offer(currentPlayer);
                continue;
            }

            if (snakes.containsKey(nextPosition)) {
                System.out.println(playerName + " encountered a snake at " + nextPosition + " and is sent to " + snakes.get(nextPosition));
                nextPosition = snakes.get(nextPosition);
            } else if (ladders.containsKey(nextPosition)) {
                System.out.println(playerName + " climbed a ladder from " + nextPosition + " to " + ladders.get(nextPosition));
                nextPosition = ladders.get(nextPosition);
            }

            System.out.println(playerName + " moved to position " + nextPosition);

            if (nextPosition == BOARD_SIZE) {
                System.out.println(playerName + " wins the game!");
                rank.add(playerName);
                continue;
            }

            playerPositions.put(playerName, nextPosition);
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

public class ImproperLocationOfSnake extends RuntimeException {
    public ImproperLocationOfSnake(){
        System.out.println("Snake cannot be placed in last position.");
    }
    public ImproperLocationOfSnake(String msg){
        System.out.println(msg);
    }
}
