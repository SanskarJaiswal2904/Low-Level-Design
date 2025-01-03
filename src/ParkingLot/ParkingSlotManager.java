package ParkingLot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingSlotManager {
    private final Map<String, Ticket> activeTickets;
    private final Map<ParkingSlotType, List<ParkingSlot>> availableSlots;

    private int TicketIdCounter = 1;

    public ParkingSlotManager(int smallSlot, int mediumSlot, int largeSlot){
        activeTickets = new HashMap<>();
        availableSlots = new HashMap<>();
        initializeSlots(smallSlot,ParkingSlotType.SMALL);
        initializeSlots(mediumSlot,ParkingSlotType.MEDIUM);
        initializeSlots(largeSlot,ParkingSlotType.LARGE);

    }

    private void initializeSlots(int count, ParkingSlotType parkingSlotType) {
        List<ParkingSlot> slot = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            ParkingSlot parkingSlot = new ParkingSlot(parkingSlotType, i);
            slot.add(parkingSlot);
        }
        availableSlots.put(parkingSlotType, slot);
    }

    public Ticket parkVehicle(Vehicle vehicle){
        ParkingSlotType slotType = findParkingSlot(vehicle.getVehicleType());
        ParkingSlot parkingSlot = findAvailableParkingSlot(slotType);
        if(parkingSlot == null){
            throw new RuntimeException("No available slots for " + vehicle.getVehicleType());
        }

        LocalDateTime entryTime = LocalDateTime.now();
        String ticketId = "Ticket- "+ TicketIdCounter;
        TicketIdCounter++;


        Ticket ticket = new Ticket(ticketId, vehicle, parkingSlot, entryTime);
        activeTickets.put(ticketId, ticket);
        return ticket;
    }

    public double unParkVehicle(String ticketId){
        Ticket ticket = activeTickets.remove(ticketId);
        if(ticket == null){
            throw new RuntimeException("Invalid ticket ID: " + ticketId);
        }
        LocalDateTime entryTime = ticket.getEntryTime();
        LocalDateTime exitTime = LocalDateTime.now();

        ParkingSlot slot = ticket.getParkingSlot();
        slot.vacate();

        return ParkingRateCalculator.calculateAmount(entryTime, exitTime, slot.getParkingSlotType());

    }

    //only below function I do not understand and need to overWrite sout in every function
    public void displayAvailableSlots(){
        System.out.println("Available Slots:");
        for (Map.Entry<ParkingSlotType, List<ParkingSlot>> entry : availableSlots.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (ParkingSlot slot : entry.getValue()) {
                if (!slot.isOccupied()) {
                    System.out.println(slot);
                }
            }
        }
    }

    private ParkingSlot findAvailableParkingSlot(ParkingSlotType slotType) {
        for (ParkingSlot slot : availableSlots.get(slotType)){
            if(!slot.isOccupied()){
                slot.occupy();
                return slot;
            }
        }
        return null;
    }

    private ParkingSlotType findParkingSlot(VehicleType vehicleType) {
        switch (vehicleType){
            case BIKE : return ParkingSlotType.SMALL;
            case CAR : return ParkingSlotType.MEDIUM;
            case TRUCK : return ParkingSlotType.LARGE;
            default : throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }

}
