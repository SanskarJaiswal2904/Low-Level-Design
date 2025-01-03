package Elevator;

public class start {
    public static void main(String[] args) {
        ElevatorManager manager = new ElevatorManager(3, 10);

        FloorRequest floorRequest = new FloorRequest(3, Direction.UP);
        manager.handleRequest(floorRequest);
        floorRequest = new FloorRequest(7, Direction.DOWN);
        manager.handleRequest(floorRequest);
        floorRequest = new FloorRequest(1, Direction.UP);
        manager.handleRequest(floorRequest);
        floorRequest = new FloorRequest(8, Direction.DOWN);
        manager.handleRequest(floorRequest);
    }
}
