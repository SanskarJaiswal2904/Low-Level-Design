package Elevator;

import java.util.ArrayList;
import java.util.List;
public class ElevatorManager {
    private final List<ElevatorController> elevators;

    public ElevatorManager(int numberOfElevators, int totalFloors) {
        this.elevators = new ArrayList<>();
        for(int i = 0; i < numberOfElevators; i++){
            elevators.add(new ElevatorController(new Elevator(totalFloors)));
        }
    }
    public void handleRequest(FloorRequest request){
        ElevatorController bestElevator = findBestElevatorRequest(request);
        System.out.println("Assigning request " + request + " to elevator " + elevators.indexOf(bestElevator));
        bestElevator.addRequest(request);
    }

    private ElevatorController findBestElevatorRequest(FloorRequest request){
        ElevatorController bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for(ElevatorController controller : elevators){
            Elevator elevator = controller.getElevator();
            if(elevator.getState() == ElevatorState.MAINTENANCE){
                continue;
            }
            int distance = Math.abs(elevator.getCurrentFloor() - request.getFloorNumber());

            if(distance < minDistance){
                if(elevator.getCurrentDirection() == Direction.IDLE || elevator.getCurrentDirection() == request.getDirection() ||
                        elevator.getState() == ElevatorState.IDLE){ // if elevator is idle or if elevator direction is same as request direction
                    minDistance = distance;
                    bestElevator = controller;
                }
            }
        }
        return  bestElevator != null ? bestElevator : elevators.get(0);
    }
}
