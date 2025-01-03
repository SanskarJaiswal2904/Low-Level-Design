package Splitwise;

public class User {
    private final int userid;
    private final String name;
    private final String email;
    private final String phone;
    private boolean hasPaid;
    private double amount;
    private boolean amountAutomatically;

    public User(int userid, String name, String email, String phone) {
        System.out.println("User added: " + name);
        this.userid = userid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.hasPaid = false;
        this.amountAutomatically = true;
    }

    public boolean hasPaid() {
        return hasPaid;
    }

    public void yesHasPaid() {
        this.hasPaid = true;
    }
    public void notHasPaid() {
        this.hasPaid = false;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
//        double rounded = Math.round(amount * 100.0)/100.0;
//        this.amount = rounded;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getUserid() {
        return userid;
    }

    public void setAmountAutoToManual(){
        this.amountAutomatically = false;
    }
    public boolean getAmountAutomatically(){
        return this.amountAutomatically;
    }


}
