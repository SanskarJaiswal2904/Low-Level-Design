package ParkingLot;

import java.time.LocalDateTime;

public class Ticket {
    private final String slotID;
    private final Vehicle vehicle;
    private final ParkingSlot parkingSlot;
    private final LocalDateTime entryTime;

    public Ticket(String slotID, Vehicle vehicle, ParkingSlot parkingSlot, LocalDateTime entryTime) {
        this.slotID = slotID;
        this.vehicle = vehicle;
        this.parkingSlot = parkingSlot;
        this.entryTime = entryTime;
    }

    public String getSlotID() {
        return slotID;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}
