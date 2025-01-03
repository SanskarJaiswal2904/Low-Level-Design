package ParkingLot.Demo;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

// Enums for Vehicle Type and Slot Type
enum VehicleType {
    CAR, BIKE, TRUCK
}

enum ParkingSlotType {
    SMALL, MEDIUM, LARGE
}

// Abstract Vehicle Class
abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    public Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }
}

// Specific Vehicle Classes
class Car extends Vehicle {
    public Car(String licensePlate) {
        super(licensePlate, VehicleType.CAR);
    }
}

class Bike extends Vehicle {
    public Bike(String licensePlate) {
        super(licensePlate, VehicleType.BIKE);
    }
}

class Truck extends Vehicle {
    public Truck(String licensePlate) {
        super(licensePlate, VehicleType.TRUCK);
    }
}

// Parking Slot Class
class ParkingSlot {
    private final int slotId;
    private final ParkingSlotType type;
    private boolean isOccupied;

    public ParkingSlot(int slotId, ParkingSlotType type) {
        this.slotId = slotId;
        this.type = type;
        this.isOccupied = false;
    }

    public int getSlotId() {
        return slotId;
    }

    public ParkingSlotType getType() {
        return type;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void occupy() {
        isOccupied = true;
    }

    public void vacate() {
        isOccupied = false;
    }

    @Override
    public String toString() {
        return "Slot{id=" + slotId + ", type=" + type + ", occupied=" + isOccupied + "}";
    }
}

// Parking Ticket Class
class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSlot slot;
    private final LocalDateTime entryTime;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot slot) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = LocalDateTime.now();
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return "Ticket{id=" + ticketId + ", vehicle=" + vehicle.getLicensePlate() +
                ", slot=" + slot.getSlotId() + ", vehicleType=" + getVehicle() + ", entryTime=" + entryTime + "}";
    }
}

// Parking Rate Calculation
class ParkingRate {
    public static double calculateFee(LocalDateTime entryTime, LocalDateTime exitTime, ParkingSlotType slotType) {
        long hours = Duration.between(entryTime, exitTime).toHours();
        if (hours == 0) hours = 1; // Minimum 1-hour charge

        switch (slotType) {
            case SMALL: return hours * 10.0; // 10 per hour
            case MEDIUM: return hours * 20.0; // 20 per hour
            case LARGE: return hours * 30.0; // 30 per hour
            default: throw new IllegalArgumentException("Unknown slot type: " + slotType);
        }
    }
}

// Parking Manager Class
class ParkingManager {
    private final Map<String, Ticket> activeTickets = new HashMap<>();
    private final Map<ParkingSlotType, List<ParkingSlot>> availableSlots = new HashMap<>();
    private int ticketCounter = 1;

    public ParkingManager(int smallSlots, int mediumSlots, int largeSlots) {
        initializeSlots(ParkingSlotType.SMALL, smallSlots);
        initializeSlots(ParkingSlotType.MEDIUM, mediumSlots);
        initializeSlots(ParkingSlotType.LARGE, largeSlots);
    }

    private void initializeSlots(ParkingSlotType type, int count) {
        List<ParkingSlot> slots = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            slots.add(new ParkingSlot(i, type));
        }
        availableSlots.put(type, slots);
    }

    public Ticket parkVehicle(Vehicle vehicle) {
        ParkingSlotType requiredSlotType = getSlotTypeForVehicle(vehicle.getType());
        ParkingSlot slot = findAvailableSlot(requiredSlotType);

        if (slot == null) {
            throw new RuntimeException("No available slots for " + vehicle.getType());
        }

        slot.occupy();
        String ticketId = "TICKET-" + ticketCounter++;
        Ticket ticket = new Ticket(ticketId, vehicle, slot);
        activeTickets.put(ticketId, ticket);

        return ticket;
    }

    public double unparkVehicle(String ticketId) {
        Ticket ticket = activeTickets.remove(ticketId);

        if (ticket == null) {
            throw new RuntimeException("Invalid ticket ID: " + ticketId);
        }

        ParkingSlot slot = ticket.getSlot();
        slot.vacate();

        LocalDateTime exitTime = LocalDateTime.now();
        return ParkingRate.calculateFee(ticket.getEntryTime(), exitTime, slot.getType());
    }

    private ParkingSlotType getSlotTypeForVehicle(VehicleType vehicleType) {
        switch (vehicleType) {
            case BIKE: return ParkingSlotType.SMALL;
            case CAR: return ParkingSlotType.MEDIUM;
            case TRUCK: return ParkingSlotType.LARGE;
            default: throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }

    private ParkingSlot findAvailableSlot(ParkingSlotType type) {
        for (ParkingSlot slot : availableSlots.get(type)) {
            if (!slot.isOccupied()) {
                return slot;
            }
        }
        return null;
    }

    public void displayAvailableSlots() {
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
}

// Main Class
public class ParkingLotSystem {
    public static void main(String[] args) {
        ParkingManager manager = new ParkingManager(2, 3, 1);

        // Park vehicles
        Vehicle car = new Car("CAR-123");
        Vehicle bike = new Bike("BIKE-456");
        Vehicle truck = new Truck("TRUCK-789");

        Ticket carTicket = manager.parkVehicle(car);
        Ticket bikeTicket = manager.parkVehicle(bike);

        System.out.println(carTicket);
        System.out.println(bikeTicket);

        manager.displayAvailableSlots();

        // Unpark vehicles
        double carFee = manager.unparkVehicle(carTicket.getTicketId());
        System.out.println("Car Fee: $" + carFee);

        double bikeFee = manager.unparkVehicle(bikeTicket.getTicketId());
        System.out.println("Bike Fee: $" + bikeFee);

        manager.displayAvailableSlots();
    }
}
