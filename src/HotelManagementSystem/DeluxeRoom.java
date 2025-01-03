package HotelManagementSystem;

public class DeluxeRoom extends Rooms{
    private final int quantity;
    public DeluxeRoom(int quantity) {
        super(quantity, RoomType.DELUXE);
        this.quantity = quantity;
    }
}
