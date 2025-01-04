package Splitwise.Demo;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        User a = new User(1, "Sanskar", "sanskar@domain.com", "986");
        User b = new User(2, "Shubham", "shubham@domain.com", "874");
        User c = new User(3, "Aryan", "aryan@domain.com", "8741");
        User d = new User(4, "Abhinav", "abhinav@domain.com", "8742");
        User e = new User(5, "Kamya", "kamya@domain.com", "8743");

        List<User> userList = new ArrayList<>();
        userList.add(a);
        userList.add(b);
        userList.add(c);
        userList.add(d);
        userList.add(e);

        SplitManager splitManager = new SplitManager(10000, userList);

        System.out.println("Initial Debts:");
        splitManager.showDebtOfEachUserAll();

        System.out.println("\nUneven distribution for Shubham:");
        splitManager.unevenDistributionAmongUsers(b, 5000);
        splitManager.showDebtOfEachUserAll();


        System.out.println("\nUneven distribution for Aryan:");
        splitManager.unevenDistributionAmongUsers(c, 4000);
        splitManager.showDebtOfEachUserAll();

        System.out.println("\nSettling Shubham's payment:");
        splitManager.settlePayment(b, b.getAmount());
        splitManager.showDebtOfEachUserAll();

        System.out.println("\nSettling remaining users' payments:");
        for (User user : userList) {
            if (!user.hasPaid()) {
                splitManager.settlePayment(user, user.getAmount());
            }
        }
        splitManager.showDebtOfEachUserAll();
    }
}

 class User {
    private final int userId;
    private final String name;
    private final String email;
    private final String phone;
    private boolean hasPaid;
    private double amount;

    public User(int userId, String name, String email, String phone) {
        System.out.println("User added: " + name);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.hasPaid = false;
    }

    public boolean hasPaid() {
        return hasPaid;
    }

    public void yesHasPaid() {
        this.hasPaid = true;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = Math.round(amount * 100.0) / 100.0;
    }

    public String getName() {
        return name;
    }
}



 class SplitManager {
    private double totalAmount;
    private final List<User> users;

    public SplitManager(double totalAmount, List<User> users) {
        this.totalAmount = totalAmount;
        this.users = users;
        assignAmountToEachUser(totalAmount);
    }

    private void assignAmountToEachUser(double totalAmount) {
        if (users.isEmpty()) {
            System.out.println("No users exist!");
            return;
        }
        if (totalAmount == 0) {
            System.out.println("No money to split!");
            return;
        }
        double amountPerUser = Math.round((totalAmount / users.size()) * 100.0) / 100.0;
        for (User user : users) {
            if (!user.hasPaid()) {
                user.setAmount(amountPerUser);
            }
        }
    }

    public boolean unevenDistributionAmongUsers(User specificUser, double specificAmount) {
        if (specificUser.hasPaid()) {
            System.out.println(specificUser.getName() + " has already paid.");
            return false;
        }

        double remainingAmount = totalAmount - specificAmount;
        int usersLeft = 0;

        for (User user : users) {
            if (!user.hasPaid() && user != specificUser) {
                usersLeft++;
            }
        }

        if (usersLeft == 0) {
            System.out.println("No other users to distribute the remaining amount.");
            return false;
        }

        double amountForOthers = Math.round((remainingAmount / usersLeft) * 100.0) / 100.0;

        for (User user : users) {
            if (!user.hasPaid() && user != specificUser) {
                user.setAmount(amountForOthers);
            }
        }

        specificUser.setAmount(specificAmount);
        return true;
    }

    public void showDebtOfEachUserAll() {
        boolean allPaid = true;

        for (User user : users) {
            if (!user.hasPaid()) {
                allPaid = false;
                System.out.println(user.getName() + " owes: $" + user.getAmount());
            } else {
                System.out.println(user.getName() + " has cleared their dues.");
            }
        }

        if (allPaid) {
            System.out.println("Everyone has paid!");
        }
        System.out.println();
    }

    public boolean settlePayment(User user, double paymentAmount) {
        try {
            if (user.hasPaid()) {
                System.out.println(user.getName() + " has already paid.");
                return false;
            }

            if (Math.abs(paymentAmount - user.getAmount()) > 0.01) {
                System.out.println("Incorrect payment amount. You need to pay exactly: $" + user.getAmount());
                return false;
            }

            user.setAmount(0);
            user.yesHasPaid();
            totalAmount -= paymentAmount;
            System.out.println(user.getName() + " has successfully paid $" + paymentAmount + ".");
            return true;

        } catch (Exception e) {
            System.out.println("Error processing payment for " + user.getName() + ": " + e.getMessage());
            return false;
        }
    }
}