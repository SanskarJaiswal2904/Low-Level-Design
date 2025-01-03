package ParkingLot;

public class ParkingSlot {
    private final ParkingSlotType parkingSlotType;
    private final int parkingSlotId;

    private boolean isOccupied;

    public ParkingSlot(ParkingSlotType parkingSlotType, int parkingSlotId) {
        this.parkingSlotType = parkingSlotType;
        this.parkingSlotId = parkingSlotId;
        this.isOccupied = false;
    }

    public ParkingSlotType getParkingSlotType() {
        return parkingSlotType;
    }

    public int getParkingSlotId() {
        return parkingSlotId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void occupy(){
        isOccupied = true;
    }
    public void vacate(){
        isOccupied = false;
    }
}
