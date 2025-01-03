package HotelManagementSystem;

public class NormalRoom extends Rooms{
    private final int quantity;
    public NormalRoom(int quantity) {
        super(quantity, RoomType.NORMAL);
        this.quantity = quantity;
    }

}
