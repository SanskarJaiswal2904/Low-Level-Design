package VendingMachine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MachineBox {
    private final Map<String, Product> inventory;
    private double balance;
    private double totalSales;

    public MachineBox(){
        this.inventory = new HashMap<>();
        balance = 0.0;
        totalSales = 0.0;
    }
    public void addProduct(Product product){
        inventory.put(product.getItemId(), product);
        System.out.println("Product added: "+product.getItemType().name());
    }
    public void displayProduct(){ //display all available products
        System.out.println("Available products:");
        inventory.values().forEach(System.out::println);
    }
    public void insertCoin(Coin coin){
        System.out.println("Coin inserted: " + coin.getValue());
        balance += coin.getValue();
    }
    public void selectProduct(String productId){
        Product product = inventory.get(productId);
        if(product == null){
            System.out.println("Product does not exist: "+productId);
            return;
        }
        if(product.getItemQuantity() == 0){
            System.out.println("Product is out of stock: "+ product.getItemType().name()); // i dont know if product.getItemType().name() is appropriate or it will be just product.getItemType()
            return;
        }
        if(balance < product.getItemPrice()){
            System.out.println("Insufficient balance"+"\tPlease insert more coin: "+balance);
            return;
        }
        if(product.dispense()){
            System.out.println("Total money deposited: "+ balance);
            balance -= product.getItemPrice();
            totalSales += product.getItemPrice();
            System.out.println("Dispensing: "+ productId);
            returnChange();
        }
    }
    public void returnChange(){
        if(balance > 0){
            System.out.println("Returning change: " + balance);
            balance = 0.0;
        }
    }

    public void restockProduct(String productId, int quantity){
        Product product = inventory.get(productId);
        if(product != null){
            System.out.println(product.getItemType()+" restocked successfully.");
            product.restock(quantity);
        } else{
            System.out.println("Product does not exist: "+productId);
            return;
        }
    }
    public void viewTotalSales(){
        System.out.println("Total sales: " + totalSales);
    }
}
