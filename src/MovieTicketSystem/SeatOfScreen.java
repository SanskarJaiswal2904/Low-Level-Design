package MovieTicketSystem;

import java.util.HashMap;
import java.util.Map;

public class SeatOfScreen {
    private final int economyCount;
    private final int deluxeCount;
    private final int executiveCount;
    private boolean isOccupied;
    private final Map<SeatType, Integer> seatTypeMapping;
    private final Map<Boolean, Map<SeatType, Integer>> seatAvailable;

    public SeatOfScreen(int economyCount, int deluxeCount, int executiveCount) {
        this.economyCount = economyCount;
        this.deluxeCount = deluxeCount;
        this.executiveCount = executiveCount;
        this.isOccupied = false;
        this.seatTypeMapping = new HashMap<>();
        this.seatAvailable = new HashMap<>();
        initialiseMap(SeatType.ECONOMY, economyCount);
        initialiseMap(SeatType.DELUXE, deluxeCount);
        initialiseMap(SeatType.EXECUTIVE, executiveCount);
    }

    private void initialiseMap(SeatType seatType, int seatCount) {
        seatTypeMapping.put(seatType, seatCount);
        seatAvailable.put(true, seatTypeMapping);
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

    public int getEconomyCount() {
        return economyCount;
    }

    public int getDeluxeCount() {
        return deluxeCount;
    }

    public int getExecutiveCount() {
        return executiveCount;
    }
    public int getTotalSeatsInScreen(){
        return economyCount + deluxeCount + executiveCount;
    }
    public int totalSeatAvailableLive(){
        int count = 0;
        for(Map.Entry<Boolean, Map<SeatType, Integer>> outerEntry : seatAvailable.entrySet()){
            Boolean outerKey = outerEntry.getKey();
            if(outerKey) {
                Map<SeatType, Integer> innerMap = outerEntry.getValue();
                for (Map.Entry<SeatType, Integer> innerEntry : innerMap.entrySet()) {
                    SeatType innerKey = innerEntry.getKey();
                    Integer value = innerEntry.getValue();
                    count += value;
                }
            }
        }
        return count;
    }

    private int seatTypeAvailableCount(SeatType seatType){
        int count = 0;
        for(Map.Entry<Boolean, Map<SeatType, Integer>> outerEntry : seatAvailable.entrySet()){
            Boolean outerKey = outerEntry.getKey();
            if(outerKey) {
                Map<SeatType, Integer> innerMap = outerEntry.getValue();
                for (Map.Entry<SeatType, Integer> innerEntry : innerMap.entrySet()) {
                    SeatType innerKey = innerEntry.getKey();
                    if(innerKey == seatType){
                        Integer value = innerEntry.getValue();
                        count += value;
                    }
                }
            }
        }
        return count;

    }

    public double bookSeats(int quantity, SeatType seatType){
        double seatIsAvailablePrice = 0.0;
        int count = seatTypeAvailableCount(seatType);
        if(quantity <= count){
            seatIsAvailablePrice = calculatePriceforSeating(quantity, seatType);
            updateLiveSeat(quantity, seatType);
        } else{
            throw new RuntimeException("No seats are available in this category: "+ seatType);
        }
        return seatIsAvailablePrice;
    }

    private void updateLiveSeat(int quantity, SeatType seatType) {
        //rethink updateLiveSeat method
        for(Map.Entry<Boolean, Map<SeatType, Integer>> outerEntry : seatAvailable.entrySet()) {
            Boolean outerKey = outerEntry.getKey();
            Map<SeatType, Integer> innerMap = outerEntry.getValue();
            for (Map.Entry<SeatType, Integer> innerEntry : innerMap.entrySet()) {
                SeatType innerKey = innerEntry.getKey();
                Integer value = innerEntry.getValue();
                if(innerKey != seatType){
                    continue;
                } else{
                   value = value - quantity;
                   outerKey = false;
                }
            }
        }
    }

    private double calculatePriceforSeating(int quantity, SeatType seatType){
        switch (seatType){
            case ECONOMY -> {return 200 * quantity;}
            case DELUXE -> {return 300 * quantity;}
            case EXECUTIVE -> {return 450 * quantity;}
            default -> throw new RuntimeException("Invalid seat type: "+ seatType);
        }

    }
}
