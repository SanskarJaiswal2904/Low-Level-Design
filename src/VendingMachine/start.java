package VendingMachine;

public class start {
    public static void main(String[] args) {
        MachineBox machineBox = new MachineBox();
        machineBox.addProduct(new Product("P1", ProductType.SODA, 10,1.50));
        machineBox.addProduct(new Product("P2", ProductType.CHIPS, 5,2.00));
        machineBox.addProduct(new Product("P3", ProductType.CANDY, 8,1.00));
        machineBox.addProduct(new Product("P4", ProductType.WATER, 15,1.25));

        machineBox.displayProduct();

        machineBox.insertCoin(Coin.QUARTER);
        machineBox.insertCoin(Coin.QUARTER);
        machineBox.insertCoin(Coin.DOLLAR);

        machineBox.selectProduct("P1"); //will success
        machineBox.selectProduct("P2"); //will fail because of insufficient balance

        machineBox.restockProduct("P2", 5);
        machineBox.displayProduct();

        machineBox.viewTotalSales();

    }
}
