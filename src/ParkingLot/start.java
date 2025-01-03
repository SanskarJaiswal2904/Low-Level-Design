package ParkingLot;

public class start {
    public static void main(String[] args) {
        ParkingSlotManager manager = new ParkingSlotManager(2, 3, 1);

        // Park vehicles
         Vehicle car = new  Car("CAR-123");
         Vehicle bike = new  Bike("BIKE-456");
         Vehicle truck = new  Truck("TRUCK-789");

         Ticket carTicket = manager.parkVehicle(car);
         Ticket bikeTicket = manager.parkVehicle(bike);

        System.out.println(carTicket);
        System.out.println(bikeTicket);

        manager.displayAvailableSlots();

        // Unpark vehicles
        double carFee = manager.unParkVehicle(carTicket.getSlotID());
        System.out.println("Car Fee: $" + carFee);

        double bikeFee = manager.unParkVehicle(bikeTicket.getSlotID());
        System.out.println("Bike Fee: $" + bikeFee);

        manager.displayAvailableSlots();    }
}

