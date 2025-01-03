package Splitwise;

import java.util.List;

public class SplitManager {
    private double totalAmount;
    private final List<User> users;
    private final double totalUsers;

    public SplitManager(double totalAmount, List<User> users) {
        this.totalAmount = totalAmount;
        this.users = users;
        this.totalUsers = users.size();
        assignedAmountToEachUser(totalAmount);
    }
    private double assignedAmountToEachUser(double totalAmount){
        if(totalUsers == 0){
            System.out.println("No users exist!");
            return 0.0;
        }
        if(totalAmount == 0){
            System.out.println("No money to split!");
            return 0.0;
        }
        double amountOfEachUser = totalAmount/totalUsers;
        for(User user : users){
            if(!user.hasPaid()) {
                user.setAmount(amountOfEachUser);
            }
        }
        return amountOfEachUser;
    }

    public boolean unevenDistributionAmongUsers(User u, double amount){
        int totalUsersLeft = 0;
        for(User user : users){
            if(!user.hasPaid()) {
                ++totalUsersLeft;
            }
        }
        double value = (totalAmount - amount)/(totalUsersLeft-1);

        for(User user : users){
            if(!user.hasPaid()) {
                user.setAmount(value);
            }
        }
        u.setAmount(amount);
        return true;
    }

    public void showDebtOfEachUserAll(){
        int count = 0;
        for(User user : users){
            if(!user.hasPaid()) {
                double value = user.getAmount();
                System.out.println(user.getName() + " is due to pay: " + user.getAmount());
            } else {
                count++;
                System.out.println(user.getName() + " has paid his part.");
            }
        }
        System.out.println();
        if(count == users.size()){
            System.out.println("Everyone has paid!");
        }
    }

    public boolean settlePayment(User user, double amount){
        try {
            if(amount <= totalAmount){
                System.out.println(user.getName() + " has initiated a payment.");
                System.out.println("Processing payment... Please wait!!");
                Thread.sleep(100);
                user.setAmount(0);
                user.yesHasPaid();
                System.out.println(user.getName() + " has paid successfully.\n");
                totalAmount -= amount;
                return true;
            } else {
                System.out.println("You cannot pay more than you're asked.");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
