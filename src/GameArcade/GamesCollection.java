package GameArcade;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GamesCollection {
    private Map<GameTypes, List<Games>> gameInventory;
    private static int billIdCounter = 1;
    private Map<String, GameBill> activeBills;

    public GamesCollection(int bikeRacingNumber, int bowlingAllyNumber, int smashTheMoleNumber) {
        this.gameInventory = new HashMap<>();
        this.activeBills = new HashMap<>();
        initialiseGame(bikeRacingNumber, GameTypes.BIKERACING);
        initialiseGame(bowlingAllyNumber, GameTypes.BOWLINGALLY);
        initialiseGame(smashTheMoleNumber, GameTypes.SMASHTHEMOLE);
    }

    private void initialiseGame(int count, GameTypes gameTypes) {
        List<Games> gameList = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            gameList.add(new Games(i, gameTypes, false));
        }
        gameInventory.put(gameTypes, gameList);
    }

    public GameBill checkInGame(String customerName, LocalDateTime entryTime, GameTypes gameTypes){
        List<Games> gameList = gameInventory.get(gameTypes);
        for(Games games : gameList){
            if(!games.isOccupied()){
                games.setOccupied(true);
                int gameNumber = games.getGameNumber();
                String billId = "BILLID-" + billIdCounter++;
                GameBill gameBill = new GameBill(billId, gameTypes, entryTime, gameNumber, customerName);
                activeBills.put(billId, gameBill);
                System.out.println("Game " + games.getGameNumber() + " of " + games.getGameTypes() + " games booked successfully for " + customerName);
                return gameBill;
            }
        }
        throw new RuntimeException("No games available of type " + gameTypes);
    }

    public void checkOutGame(String billId){
        if(!activeBills.containsKey(billId)){
            throw new RuntimeException("Invalid BillId: " + billId);
        }
        GameBill gameBill = activeBills.remove(billId);
        LocalDateTime exitTime = LocalDateTime.now();

        double totalAmount = gameBill.calculatePrice(exitTime);
        List<Games> games = gameInventory.get(gameBill.getGameTypes());
        for (Games game : games){
            if(game.getGameNumber() == gameBill.getGameNumber()){
                game.setOccupied(false);
                System.out.println("Game " + game.getGameNumber() + " is now free.");
                System.out.printf("Total Price for Stay (Customer: %s, Game Type: %s): $%.2f%n",
                        gameBill.getCustomerName(), gameBill.getGameTypes(), totalAmount);
                return;
            }
        }
    }

    public void displayAvailableGames() {
        for (Map.Entry<GameTypes, List<Games>> entry : gameInventory.entrySet()) {
            GameTypes roomType = entry.getKey();
            long availableCount = entry.getValue().stream().filter(game -> !game.isOccupied()).count();
            System.out.println(roomType + ": " + availableCount + " games available.");
        }
    }
}
