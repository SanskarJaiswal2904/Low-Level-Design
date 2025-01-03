package Elevator;

import java.util.LinkedList;
import java.util.Queue;

public class ElevatorController {
    private final Elevator elevator;
    private final Queue<FloorRequest> requestQueue;

    public Elevator getElevator() {
        return elevator;
    }

    public ElevatorController(Elevator elevator) {
        this.elevator = elevator;
        this.requestQueue = new LinkedList<>();
    }

    public void addRequest(FloorRequest request){
        requestQueue.add(request);
        processRequest();
    }
    public void processRequest(){
        while(!requestQueue.isEmpty()){
            FloorRequest floorRequest = requestQueue.poll();
            elevator.moveToFloor(floorRequest.getFloorNumber());
        }
    }

}
