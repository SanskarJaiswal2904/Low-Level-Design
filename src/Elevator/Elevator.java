package Elevator;

public class Elevator {
    private int currentFloor;
    private Direction currentDirection;
    private ElevatorState state;
    private final int totalFloors;

    public Elevator(int totalFloors) {
        this.currentFloor = 0;
        this.currentDirection = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.totalFloors = totalFloors;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public ElevatorState getState() {
        return state;
    }

    public void setState(ElevatorState state) {
        this.state = state;
    }
    public void moveToFloor(int targetFloor){
        try {
            if (targetFloor < 0 || targetFloor > totalFloors) {
                throw new InvalidFloorException("Enter valid FloorNumber : " + targetFloor + " is out of range.");
            }
            int previousCurrentFloor = currentFloor;
            while(currentFloor != targetFloor){
                state = ElevatorState.MOVING;
                if(currentFloor < targetFloor){
                    currentFloor++;
                    currentDirection = Direction.UP;
                }else{
                    currentFloor--;
                    currentDirection = Direction.DOWN;
                }
            }

            System.out.println("Elevator is moving from "+ previousCurrentFloor + " to "+ targetFloor+ " in " + currentDirection + " direction.");
            System.out.println("Elevator Reached : " + targetFloor);
            currentDirection = Direction.IDLE;
            state = ElevatorState.IDLE;
        }catch (InvalidFloorException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString(){
        return "Elevator{currentFloor = " + currentFloor + ", currentDirection = " + currentDirection+ ", state = " + state + "}";
    }
}
