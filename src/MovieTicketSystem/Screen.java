package MovieTicketSystem;

import java.util.List;

public class Screen {
    private final int floorNumber;
    private final SeatOfScreen seatOfScreen;
    private final List<Audience> audience;
    private final Movie movie;
    public Screen(int floorNumber, SeatOfScreen seatOfScreen, Movie movie, List<Audience> audience) {
        this.floorNumber = floorNumber;
        this.seatOfScreen = seatOfScreen;
        this.movie = movie;
        this.audience = audience;
    }

    public Movie getMovie() {
        return movie;
    }

    public int getFloorNumber() {
        return floorNumber;
    }
    public SeatOfScreen getSeatOfScreen() {
        return seatOfScreen;
    }

    public List<Audience> getAudience() {
        return audience;
    }
}
