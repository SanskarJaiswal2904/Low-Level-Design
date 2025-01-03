package Elevator;

public class InvalidFloorException extends Exception{
    InvalidFloorException(){
        System.out.println("Enter Valid floor number");
    }
    InvalidFloorException(String msg){
        super(msg);
        System.out.println(msg);
    }
}
