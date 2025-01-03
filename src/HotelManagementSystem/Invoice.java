package HotelManagementSystem;

import ParkingLot.ParkingSlot;
import ParkingLot.Vehicle;

import java.time.LocalDateTime;

public class Invoice {
    private final String invoiceId;
    private final RoomType roomType;
    private final LocalDateTime entryTime;

    public Invoice(String invoiceId, RoomType roomType, LocalDateTime entryTime) {
        this.invoiceId = invoiceId;
        this.roomType = roomType;
        this.entryTime = entryTime;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }
}
