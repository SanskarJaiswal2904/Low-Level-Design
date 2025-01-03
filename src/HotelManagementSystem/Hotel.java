package HotelManagementSystem;

public class Hotel {
    private final String name;
    private final String address;
    private final RoomCollection roomCollection;

    public Hotel(String name, String address, RoomCollection roomCollection) {
        this.name = name;
        this.address = address;
        this.roomCollection = roomCollection;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public RoomCollection getRooms() {
        return roomCollection;
    }


}
