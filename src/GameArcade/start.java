package GameArcade;

import java.time.LocalDateTime;

public class start {
    public static void main(String[] args) {
        // Initialize RoomCollection with different types of rooms
        GamesCollection roomCollection = new GamesCollection(5, 3, 1);

        // Display available rooms initially
        System.out.println("Initial Games Availability:");
        roomCollection.displayAvailableGames();

        // Check-in customers
        System.out.println("\nAllotting games to customers...");
        try {
            roomCollection.checkInGame("Harry", LocalDateTime.now(), GameTypes.BIKERACING);
            roomCollection.checkInGame("Donald", LocalDateTime.now(), GameTypes.BOWLINGALLY);
            roomCollection.checkInGame("Sasha", LocalDateTime.now(), GameTypes.SMASHTHEMOLE);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display available rooms after some check-ins
        System.out.println("\nGames Availability After Check-ins:");
        roomCollection.displayAvailableGames();

        // Try checking in another customer to a full room type
        System.out.println("\nTrying to play Smash the Mole (should fail):");
        try {
            roomCollection.checkInGame("Chris", LocalDateTime.now(), GameTypes.SMASHTHEMOLE);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Check-out a customer
        System.out.println("\nChecking out a customer from game...");
        try {
            String invoiceId = "BILLID-1"; // Example invoice ID
            roomCollection.checkOutGame(invoiceId);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Display available rooms after check-out
        System.out.println("\nGame Availability After Check-out:");
        roomCollection.displayAvailableGames();

        GameBill invoice = roomCollection.checkInGame("Alice", LocalDateTime.now().minusHours(2), GameTypes.BIKERACING);

        // Check-out the customer and display the price
        roomCollection.checkOutGame(invoice.getGameBillId());
    }
}
