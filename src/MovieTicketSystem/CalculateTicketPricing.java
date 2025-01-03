package MovieTicketSystem;

import java.util.List;

public class CalculateTicketPricing {
    public static double calculateTotalPrice(List<FoodAndBeverages> foodAndBeverages, double seatAmount){
        double amount = 0.0;
        amount += seatAmount;
        for(FoodAndBeverages foodAndBeverage : foodAndBeverages){
            amount += foodAndBeverage.getPrice();
        }
        return amount;
    }
}