package GameArcade;

import java.time.LocalDateTime;

public class GameBill {
    private final String gameBillId;
    private final GameTypes gameTypes;
    private final LocalDateTime entryTime;
    private final int gameNumber;
    private final String customerName;

    public GameBill(String gameBillId, GameTypes gameTypes, LocalDateTime entryTime, int gameNumber, String customerName) {
        this.gameBillId = gameBillId;
        this.gameTypes = gameTypes;
        this.entryTime = entryTime;
        this.gameNumber = gameNumber;
        this.customerName = customerName;
    }

    public double calculatePrice(LocalDateTime exitTime){
        long hoursPlayed = java.time.Duration.between(entryTime, exitTime).toHours();
        if(hoursPlayed == 0){ hoursPlayed = 1; }

        double pricePerDay;
        switch (gameTypes) {
            case BIKERACING -> pricePerDay = 100.0;
            case BOWLINGALLY -> pricePerDay = 200.0;
            case SMASHTHEMOLE -> pricePerDay = 300.0;
            default -> throw new IllegalStateException("Unexpected value: " + gameTypes);
        }
        return hoursPlayed * pricePerDay;
    }

    public String getGameBillId() {
        return gameBillId;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public GameTypes getGameTypes() {
        return gameTypes;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public String getCustomerName() {
        return customerName;
    }
}
