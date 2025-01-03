package HotelManagementSystem;

public abstract class Rooms {
    private final int quantity;
    private final RoomType roomType;

    public Rooms(int quantity, RoomType roomType) {
        this.quantity = quantity;
        this.roomType = roomType;
    }
}
