package MovieTicketSystem;

import java.util.List;

public class CinemaHall {
    private final List<Screen> numberOfScreen;
    private final int numberOfWashroom;
    private final String address;
    private final MovieCollection movieCollection;

    public CinemaHall(List<Screen> numberOfScreen, int numberOfWashroom, String address, MovieCollection movieCollection) {
        this.numberOfScreen = numberOfScreen;
        this.numberOfWashroom = numberOfWashroom;
        this.address = address;
        this.movieCollection = movieCollection;
    }


    public List<Screen> getNumberOfScreen() {
        return numberOfScreen;
    }

    public int getNumberOfWashroom() {
        return numberOfWashroom;
    }

    public String getAddress() {
        return address;
    }

    public MovieCollection getMovieCollection() {
        return movieCollection;
    }
}
