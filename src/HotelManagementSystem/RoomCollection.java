package HotelManagementSystem;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class RoomCollection {
    private final int normalCount;
    private final int deluxeCount;
    private final int executiveCount;
    private final int presidentialCount;

    private Map<Boolean, Map<RoomType, Integer>> occupiedRooms;
    private static int invoiceIdCounter = 1;

    public RoomCollection(int normalCount, int deluxeCount, int executiveCount, int presidentialCount) {
        this.normalCount = normalCount;
        this.deluxeCount = deluxeCount;
        this.executiveCount = executiveCount;
        this.presidentialCount = presidentialCount;
        NormalRoom normalRoom = new NormalRoom(normalCount);
        DeluxeRoom deluxeRoom = new DeluxeRoom(deluxeCount);
        ExecutiveRoom executiveRoom = new ExecutiveRoom(executiveCount);
        PresidentialRoom presidentialRoom = new PresidentialRoom(presidentialCount);
        occupiedRooms = new HashMap<>();
        initialiseRoom(normalCount, RoomType.NORMAL);
        initialiseRoom(deluxeCount, RoomType.DELUXE);
        initialiseRoom(executiveCount, RoomType.EXECUTIVE);
        initialiseRoom(presidentialCount, RoomType.PRESIDENTIAL);
    }

    private void initialiseRoom(int count, RoomType roomType) {
        Map<RoomType, Integer> map = new HashMap<>();
        for (int i = 1; i <= count; i++) {
            map.put(roomType, i);
            occupiedRooms.put(false, map);
        }
    }

    public boolean checkInHotel(LocalDateTime entryTime, RoomType roomType){
        boolean roomsAvailable = false;
        for(Map.Entry<Boolean, Map<RoomType, Integer>> outerEntry : occupiedRooms.entrySet()){
            Boolean outerKey = outerEntry.getKey();
            if(!outerKey) {
                Map<RoomType, Integer> innerMap = outerEntry.getValue();
                for (Map.Entry<RoomType, Integer> innerEntry : innerMap.entrySet()) {
                    RoomType innerKey = innerEntry.getKey();
                    Integer value = innerEntry.getValue();
                    if(innerKey == roomType){
                        System.out.println("Room booked.");
                        roomsAvailable = true;
                        Map<RoomType, Integer> map = new HashMap<>();
                        map.put(innerKey, value);
                        occupiedRooms.put(true, map);
                        Invoice invoice = new Invoice("INVOICE-" + invoiceIdCounter++, roomType, entryTime);
                        return true;
                    }
                }
            }
            if (!roomsAvailable){
                throw new RuntimeException("No rooms available.");
            }
        }
        return false;
    }

    public boolean checkOutHotel(String invoiceId){
        return true;
    }


    public int getNormalCount() {
        return normalCount;
    }

    public int getDeluxeCount() {
        return deluxeCount;
    }

    public int getExecutiveCount() {
        return executiveCount;
    }

    public int getPresidentialCount() {
        return presidentialCount;
    }
}
