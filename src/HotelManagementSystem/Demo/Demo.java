package HotelManagementSystem.Demo;

import java.time.LocalDateTime;
import java.util.*;

enum RoomType {
    NORMAL, DELUXE, EXECUTIVE, PRESIDENTIAL
}
    class RoomCollection {
        private final Map<RoomType, List<Room>> roomInventory;
        private static int invoiceIdCounter = 1;
        private final Map<String, Invoice> activeInvoices;

        public RoomCollection(int normalCount, int deluxeCount, int executiveCount, int presidentialCount) {
            roomInventory = new HashMap<>();
            activeInvoices = new HashMap<>();
            initialiseRooms(normalCount, RoomType.NORMAL);
            initialiseRooms(deluxeCount, RoomType.DELUXE);
            initialiseRooms(executiveCount, RoomType.EXECUTIVE);
            initialiseRooms(presidentialCount, RoomType.PRESIDENTIAL);
        }

        private void initialiseRooms(int count, RoomType roomType) {
            List<Room> rooms = new ArrayList<>();
            for (int i = 1; i <= count; i++) {
                rooms.add(new Room(i, roomType, false));
            }
            roomInventory.put(roomType, rooms);
        }

        public Invoice checkInHotel(String customerName, LocalDateTime entryTime, RoomType roomType) {
            List<Room> rooms = roomInventory.get(roomType);
            for (Room room : rooms) {
                if (!room.isOccupied()) {
                    room.setOccupied(true);
                    String invoiceId = "INVOICE-" + invoiceIdCounter++;
                    Invoice invoice = new Invoice(invoiceId, roomType, entryTime, room.getRoomNumber(), customerName);
                    activeInvoices.put(invoiceId, invoice);
                    System.out.println("Room " + room.getRoomNumber() + " of " + room.getRoomType() + " room booked successfully for " + customerName);
                    return invoice;
                }
            }
            throw new RuntimeException("No rooms available of type " + roomType);
        }

        public void checkOutHotel(String invoiceId) {
            if (!activeInvoices.containsKey(invoiceId)) {
                throw new RuntimeException("Invalid Invoice ID: " + invoiceId);
            }

            Invoice invoice = activeInvoices.remove(invoiceId);
            LocalDateTime checkOutTime = LocalDateTime.now();

            // Calculate price
            double totalPrice = invoice.calculatePrice(checkOutTime);

            // Free the room
            List<Room> rooms = roomInventory.get(invoice.getRoomType());
            for (Room room : rooms) {
                if (room.getRoomNumber() == invoice.getRoomNumber()) {
                    room.setOccupied(false);
                    System.out.println("Room " + room.getRoomNumber() + " is now free.");
                    System.out.printf("Total Price for Stay (Customer: %s, Room Type: %s): $%.2f%n",
                            invoice.getCustomerName(), invoice.getRoomType(), totalPrice);
                    return;
                }
            }
        }


        public void displayAvailableRooms() {
            for (Map.Entry<RoomType, List<Room>> entry : roomInventory.entrySet()) {
                RoomType roomType = entry.getKey();
                long availableCount = entry.getValue().stream().filter(room -> !room.isOccupied()).count();
                System.out.println(roomType + ": " + availableCount + " rooms available.");
            }
        }
    }

    class Room {
        private final int roomNumber;
        private final RoomType roomType;
        private boolean isOccupied;

        public Room(int roomNumber, RoomType roomType, boolean isOccupied) {
            this.roomNumber = roomNumber;
            this.roomType = roomType;
            this.isOccupied = isOccupied;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public RoomType getRoomType() {
            return roomType;
        }

        public boolean isOccupied() {
            return isOccupied;
        }

        public void setOccupied(boolean occupied) {
            isOccupied = occupied;
        }
    }

    class Invoice {
        private final String invoiceId;
        private final RoomType roomType;
        private final LocalDateTime entryTime;
        private final int roomNumber;
        private final String customerName;

        public Invoice(String invoiceId, RoomType roomType, LocalDateTime entryTime, int roomNumber, String customerName) {
            this.invoiceId = invoiceId;
            this.roomType = roomType;
            this.entryTime = entryTime;
            this.roomNumber = roomNumber;
            this.customerName = customerName;
        }

        public String getInvoiceId() {
            return invoiceId;
        }

        public RoomType getRoomType() {
            return roomType;
        }

        public LocalDateTime getEntryTime() {
            return entryTime;
        }

        public int getRoomNumber() {
            return roomNumber;
        }

        public String getCustomerName() {
            return customerName;
        }
        public double calculatePrice(LocalDateTime checkOutTime) {
            long daysStayed = java.time.Duration.between(entryTime, checkOutTime).toDays();
            if (daysStayed == 0) daysStayed = 1; // Minimum charge for 1 day

            double pricePerDay;
            switch (roomType) {
                case NORMAL -> pricePerDay = 100.0;
                case DELUXE -> pricePerDay = 200.0;
                case EXECUTIVE -> pricePerDay = 300.0;
                case PRESIDENTIAL -> pricePerDay = 500.0;
                default -> throw new IllegalStateException("Unexpected value: " + roomType);
            }
            return daysStayed * pricePerDay;
        }

    }

public class Demo {
    public static void main(String[] args) {
        // Initialize RoomCollection with different types of rooms
        RoomCollection roomCollection = new RoomCollection(5, 3, 2, 1);

        // Display available rooms initially
        System.out.println("Initial Room Availability:");
        roomCollection.displayAvailableRooms();

        // Check-in customers
        System.out.println("\nChecking in customers...");
        try {
            roomCollection.checkInHotel("Bob", LocalDateTime.now(), RoomType.DELUXE);
            roomCollection.checkInHotel("Charlie", LocalDateTime.now(), RoomType.EXECUTIVE);
            roomCollection.checkInHotel("Diana", LocalDateTime.now(), RoomType.PRESIDENTIAL);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display available rooms after some check-ins
        System.out.println("\nRoom Availability After Check-ins:");
        roomCollection.displayAvailableRooms();

        // Try checking in another customer to a full room type
        System.out.println("\nTrying to check in to a Presidential Room (should fail):");
        try {
            roomCollection.checkInHotel("Eve", LocalDateTime.now(), RoomType.PRESIDENTIAL);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Check-out a customer
        System.out.println("\nChecking out a customer...");
        try {
            String invoiceId = "INVOICE-1"; // Example invoice ID
            roomCollection.checkOutHotel(invoiceId);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display available rooms after check-out
        System.out.println("\nRoom Availability After Check-out:");
        roomCollection.displayAvailableRooms();

        Invoice invoice = roomCollection.checkInHotel("Alice", LocalDateTime.now().minusDays(2), RoomType.NORMAL);

        // Check-out the customer and display the price
        roomCollection.checkOutHotel(invoice.getInvoiceId());
    }
}
