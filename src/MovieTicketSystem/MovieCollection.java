package MovieTicketSystem;

import java.util.List;

public class MovieCollection {
    private List<Movie> movieList;

    public MovieCollection(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}

