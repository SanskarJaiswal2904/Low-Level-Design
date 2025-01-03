package Splitwise;

import java.util.ArrayList;
import java.util.List;

//split money equally among different users

//problem in unevendistribution
public class start {
    public static void main(String[] args) {
        User a = new User(1,"Sanskar","..k@.com)","986");
        User b = new User(2,"Shubham","..s@.com)","874");
        User c = new User(3,"Aryan","..s2@.com)","8741");
        User d = new User(4,"Abhinav","..s3@.com)","8742");
        User e = new User(5,"Kamya","..s4@.com)","8743");

        List<User> userList = new ArrayList<>();
        userList.add(a);
        userList.add(b);
        userList.add(c);
        userList.add(d);
        userList.add(e);

        SplitManager splitManager = new SplitManager(10000, userList);
        splitManager.showDebtOfEachUserAll();

        splitManager.settlePayment(a, a.getAmount());
        splitManager.showDebtOfEachUserAll();

        splitManager.unevenDistributionAmongUsers(b, 500);
        splitManager.showDebtOfEachUserAll();

        splitManager.unevenDistributionAmongUsers(c,250);
        splitManager.showDebtOfEachUserAll();
//
//        splitManager.settlePayment(b, b.getAmount());
//        splitManager.showDebtOfEachUserAll();
//
//        splitManager.settlePayment(c, c.getAmount());
//        splitManager.showDebtOfEachUserAll();
//
//        splitManager.settlePayment(d, d.getAmount());
//        splitManager.showDebtOfEachUserAll();
//
//        splitManager.settlePayment(e, e.getAmount());
//        splitManager.showDebtOfEachUserAll();


    }
}
