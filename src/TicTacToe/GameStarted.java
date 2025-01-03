package TicTacToe;

import java.util.Queue;
import java.util.Scanner;
//player 1 is x ie 1  //player 2 is o ie 2 //3 means blank


public class GameStarted {
    int[][] Grid;

    private Queue<Player> players;
    private Table table;

    public GameStarted(Queue<Player> players, Table table) {
        this.players = players;
        this.table = table;
        initialiseTable(this.table);
    }

    public void startGame(){
        System.out.println("Game Started..\n");
        Scanner in = new Scanner(System.in);
        int totalBoxes = table.getSize() * table.getSize() - 1;
        System.out.println("Your Table:");
        int numberOfTurn = 0;

        while(true){
            printTable();
            Player currentPlayer = players.poll();
            String currentPlayerName = currentPlayer.getName();
            int peice = currentPlayer.getPeice();
            String currentPlayerPeice = peice == 1 ? "X" : "O";

            System.out.println();
            System.out.println(currentPlayerName + " enter the position from 0 to "+ totalBoxes + " to enter " + "\"" + currentPlayerPeice + "\"" + ":");
            numberOfTurn++;
            int input = in.nextInt();
            while(input < 0 || input > totalBoxes){
                System.out.println("Invalid Input");
                System.out.println("Enter input from 0 to " + totalBoxes);
                input = in.nextInt();
            }

            int lat = input / table.getSize();
            int lon = input % table.getSize();
            writeTable(lat, lon, peice);

            boolean anyoneWonYet = false;
            if(numberOfTurn >= 2 * table.getSize() - 3) {
                boolean checkLat = checkHorizontally();
                boolean checkLong = checkVertically();
                boolean checkDiagonal = checkDiagonally();

                anyoneWonYet = checkLat || checkLong || checkDiagonal;

                if (anyoneWonYet) {
                    System.out.println("Congratulations!!");
                    System.out.println(currentPlayerName + " with \"" + currentPlayerPeice+ "\"" + " won the match!");
                    printTable();
                    break;
                }
            }


            if(numberOfTurn == totalBoxes + 1){
                System.out.println("\nMatch is Draw!");
                printTable();
                break;
            }
            players.offer(currentPlayer);
        }
        System.out.println("\n\nGame finished.");
    }

    private boolean checkDiagonally() {
        boolean diagonalWon = forwardDiagonal() || backDiagonal();
        if(diagonalWon){
            System.out.println("\nMatch won by diagonally.");
        }
        return diagonalWon;
    }

    private boolean backDiagonal() {
        int row = table.getSize()-1;
        int col = table.getSize()-1;
        int val = Grid[row][col];
        if(val == 3){
            return false;
        }
        while(true){
            if(row < 0 || col < 0){
                break;
            }
            if(Grid[row][col] != val){
                return false;
            } else{
                row = row - 1;
                col = col - 1;
                continue;
            }
        }
        return true;
    }

    private boolean forwardDiagonal(){
        int row = table.getSize()-1;
        int col = 0;
        int val = Grid[row][col];
        if(val == 3){
            return false;
        }
        while(true){
            if(row < 0 || col > table.getSize()-1){
                break;
            }
            if(Grid[row][col] != val){
                return false;
            } else{
                row = row - 1;
                col = col + 1;
                continue;
            }
        }
        return true;
    }

    private boolean checkHorizontally() {
        for (int i = 0; i < Grid.length; i++) {
            int val = Grid[i][0];
            if(val == 3){
                continue;
            }
            boolean rowWin = true;
            for (int j = 0; j < Grid[0].length; j++) {
                if(Grid[i][j] != val){
                    rowWin = false;
                    break;
                }
            }
            if(rowWin){
                System.out.println("\nMatch won by horizontally.");
                return true;
            }
        }
        return false;
    }

    private boolean checkVertically() {
        for (int i = 0; i < Grid[0].length; i++) {
            int val = Grid[0][i];
            if(val == 3){
                continue;
            }
            boolean colWin = true;
            for (int j = 0; j < Grid.length; j++) {
                if(Grid[j][i] != val){
                    colWin = false;
                    break;
                }
            }
            if(colWin){
                System.out.println("\nMatch won by vertically.");
                return true;
            }
        }
        return false;
    }

    private void printTable(){
        for (int i = 0; i < Grid.length; i++) {
            for (int j = 0; j < Grid[0].length; j++) {
                if(!(j == 0)){
                    System.out.print("|");
                }
                if(Grid[i][j] == 3){
                    System.out.print(" ");
                }
                if (Grid[i][j] == 1){
                    System.out.print("X");
                }
                if (Grid[i][j] == 2){
                    System.out.print("0");
                }
            }
            if(!(i + 1 >= Grid.length)) {
                System.out.println();
                for(int k = 0; k < table.getSize() - 1; k++) {
                    System.out.print("-+");
                }
                System.out.print("-");
                System.out.println();
            }
        }
    }

    private void writeTable(int i, int j, int p){
        if(Grid[i][j] == 3) {
            Grid[i][j] = p;
        } else{
            System.out.println("You cannot Overwrite Values.");
        }
    }
    private void initialiseTable(Table table) {
        int LatLong = table.getSize();
        Grid = new int[LatLong][LatLong];
        for (int i = 0; i < Grid.length; i++) {
            for (int j = 0; j < Grid[0].length; j++) {
                Grid[i][j] = 3;

            }
        }
    }

}

