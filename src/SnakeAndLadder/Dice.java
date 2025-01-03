package SnakeAndLadder;

public class Dice {
    private int numberOfDice;

    public Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }
    public int roll(){
        int value = (int) (Math.random() * (6 * numberOfDice - numberOfDice + 1)) + numberOfDice;
        return value;
    }
}
