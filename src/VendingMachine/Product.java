package VendingMachine;

public class Product {
    private final String itemId;
    private final ProductType itemType;
    private int itemQuantity;
    private final double itemPrice;

    public Product(String itemId, ProductType itemType, int itemQuantity, double itemPrice) {
        this.itemId = itemId;
        this.itemType = itemType;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    public String getItemId() {
        return itemId;
    }

    public ProductType getItemType() {
        return itemType;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }
    public void restock(int itemQuantity){
        this.itemQuantity += itemQuantity;
    }
    public boolean dispense(){
        if(this.itemQuantity > 0){
            this.itemQuantity--;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString(){
        return "Product{id:"+itemId+",type:"+itemType+",quantity:"+itemQuantity+",price:"+itemPrice+"}";
    }
}
