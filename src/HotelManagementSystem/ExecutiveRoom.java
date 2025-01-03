package HotelManagementSystem;

public class ExecutiveRoom extends Rooms{
    private final int quantity;
    public ExecutiveRoom(int quantity){
        super(quantity, RoomType.EXECUTIVE);
        this.quantity = quantity;
    }
}
