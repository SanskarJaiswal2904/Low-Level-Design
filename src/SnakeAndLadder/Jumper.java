package SnakeAndLadder;

public class Jumper {
    private int startPoint;
    private int endPoint;

    private boolean isLadder;

    public Jumper(int startPoint, int endPoint, boolean isLadder, int size){
        if(startPoint == endPoint){
            throw new ImproperLocationOfSnake("Start point and end point is same in either Ladder or Snake.");
        }
        if(!isLadder && endPoint > startPoint){
            throw new ImproperLocationOfSnake("Snake cannot take you Up. Use Ladder");
        }
        if(isLadder && endPoint < startPoint){
            throw new ImproperLocationOfSnake("Ladder cannot take you down. Use Snake");
        }
        if(!isLadder && startPoint == size){
            throw new ImproperLocationOfSnake();
        }
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.isLadder = isLadder;
    }

    public boolean isLadder() {
        return isLadder;
    }

    public int getStartPoint() {
        return startPoint;
    }

    public int getEndPoint() {
        return endPoint;
    }
}
