package MovieTicketSystem;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Movie {
    private final int movieId;
    private final String movieName;
    private final String directorName;
    private final String description;
    private final Duration durationOfMovie;
    private final Genre genre;
    private final LocalDate releaseDate;
    private final List<LanguageType> languageSupported;
    private final Rating rating;
    private Map<Date,List<LocalDateTime>> showTimings;

    public Movie(int movieId, String movieName, String directorName, String description, Duration duration,
                 LocalDate releaseDate, List<LanguageType> languageSupported, Rating rating, Genre genre) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.directorName = directorName;
        this.description = description;
        this.durationOfMovie = duration;
        this.releaseDate = releaseDate;
        this.languageSupported = languageSupported;
        this.rating = rating;
        this.genre = genre;
        this.showTimings = new HashMap<>();
    }

    public void setShowTimings(Map<Date,List<LocalDateTime>> showTimings) {
        this.showTimings = showTimings;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public String getDescription() {
        return description;
    }

    public Duration getDurationOfMovie() {
        return durationOfMovie;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public Rating getRating() {
        return rating;
    }

    public Genre getGenre() { return genre; }

    public List<LanguageType> getLanguageSupported() {
        return languageSupported;
    }
    public Map<Date,List<LocalDateTime>> getShowTimings() {
        return showTimings;
    }
}
