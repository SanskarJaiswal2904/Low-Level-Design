package HotelManagementSystem;

public class PresidentialRoom extends Rooms{
    private final int quantity;
    public PresidentialRoom(int quantity){
        super(quantity, RoomType.PRESIDENTIAL);
        this.quantity = quantity;
    }
}