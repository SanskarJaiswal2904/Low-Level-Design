package MovieTicketSystem;

import java.util.Map;

public class FoodAndBeverages {
    private final FoodBeverageType foodBeverageType;
    private final int quantity;
    private final double price;



    public FoodAndBeverages(int quantity, FoodBeverageType foodBeverageType) {
        this.quantity = quantity;
        this.foodBeverageType = foodBeverageType;
        this.price = calculatePrice(quantity, foodBeverageType);
    }

    private double calculatePrice(int quantity, FoodBeverageType foodBeverageType) {
        switch (foodBeverageType){
            case POPCORN -> {return 100 * quantity;}
            case CHEESEPOPCORN -> {return 220 * quantity;}
            case CARAMELPOPCORN -> {return 320 * quantity;}
            case PANEERNUGGET -> {return 280 * quantity;}
            case COFFEE -> {return 50 * quantity;}
            case SMALLCOLDDRINK -> {return 250 * quantity;}
            case MEDIUMCOLDDRINK -> {return 350 * quantity;}
            case LARGECOLDDRINK -> {return 400 * quantity;}
            default -> throw new RuntimeException("Item is not on the menu: "+ foodBeverageType);
        }
    }
    public FoodBeverageType getFoodBeverageType() {
        return foodBeverageType;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
