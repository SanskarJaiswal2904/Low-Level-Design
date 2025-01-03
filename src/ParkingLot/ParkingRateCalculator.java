package ParkingLot;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParkingRateCalculator {
     public static double calculateAmount(LocalDateTime entryTime, LocalDateTime exitTime, ParkingSlotType vehicleType){
         long hours = Duration.between(entryTime,exitTime).toHours();
         if(hours == 0){ hours = 1; }
         switch (vehicleType){
             case SMALL : return hours * 10;
             case MEDIUM : return hours * 20;
             case LARGE : return hours * 30;
             default: throw new IllegalArgumentException("Invalid Vehicle Type : " + vehicleType);
         }
     }
}
