package MovieTicketSystem.Demo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Demo {
    public static void main(String[] args) {
        int movieId = 1;
        List<LanguageType> languageSupported = List.of(LanguageType.HINDI, LanguageType.ENGLISH, LanguageType.TELUGU, LanguageType.TAMIL);
        Duration movieDuration = Duration.ofHours(2).plusMinutes(45);
        Movie movie = new Movie(movieId++, "Mufasa", "Director", "Lion Story", movieDuration,
                LocalDate.of(2024, 12, 3), languageSupported, Rating.GENERAL, Genre.ANIMATION);

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie);

        MovieCollection movieCollection = new MovieCollection(movieList);

        Audience a = new Audience("Sanskar", GenderType.MALE);
        Audience b = new Audience("Kamya", GenderType.FEMALE);
        Audience.setPhoneNumber("9875614692");

        List<Audience> audienceList = new ArrayList<>();
        audienceList.add(a);
        audienceList.add(b);

        SeatOfScreen seatOfScreen = new SeatOfScreen(200, 50, 25);
        Screen screen = new Screen(1, seatOfScreen, movie, audienceList);
        double seatAmount = seatOfScreen.bookSeats(2, SeatType.DELUXE);
        audienceList.clear();

        List<Screen> screenList = new ArrayList<>();
        screenList.add(screen);

        CinemaHall cinemaHall = new CinemaHall(screenList, 5, "Barrackpore", movieCollection);

        List<FoodAndBeverages> foodAndBeveragesList = List.of(
                new FoodAndBeverages(2, FoodBeverageType.COFFEE),
                new FoodAndBeverages(3, FoodBeverageType.POPCORN),
                new FoodAndBeverages(1, FoodBeverageType.PANEERNUGGET)
        );

        double finalAmount = CalculateTicketPricing.calculateTotalPrice(foodAndBeveragesList, seatAmount);

        System.out.println("Food Total: " + (finalAmount - seatAmount) + " Rs.\nSeat Total: " + seatAmount + " Rs.");
        System.out.println("Total: " + finalAmount + " Rs.");
    }
}

enum SeatType { ECONOMY, DELUXE, EXECUTIVE }
enum Rating { KIDS, GENERAL, PARENTALGUIDANCE, RRATED, ADULT }
enum LanguageType { HINDI, ENGLISH, TELUGU, TAMIL }
enum Genre { ACTION, COMEDY, DRAMA, HORROR, ROMANCE, ADVENTURE, THRILLER, MYSTERY, SUSPENSE, ANIMATION }
enum GenderType { MALE, FEMALE, OTHERS }
enum FoodBeverageType { POPCORN, CHEESEPOPCORN, CARAMELPOPCORN, PANEERNUGGET, COFFEE }

class SeatOfScreen {
    private final Map<SeatType, Integer> seatCounts;
    private final Map<SeatType, Double> seatPrices;

    public SeatOfScreen(int economy, int deluxe, int executive) {
        seatCounts = new HashMap<>();
        seatCounts.put(SeatType.ECONOMY, economy);
        seatCounts.put(SeatType.DELUXE, deluxe);
        seatCounts.put(SeatType.EXECUTIVE, executive);

        seatPrices = Map.of(
                SeatType.ECONOMY, 200.0,
                SeatType.DELUXE, 300.0,
                SeatType.EXECUTIVE, 450.0
        );
    }

    public double bookSeats(int quantity, SeatType seatType) {
        if (seatCounts.get(seatType) < quantity) {
            throw new RuntimeException("Not enough seats available for " + seatType);
        }
        seatCounts.put(seatType, seatCounts.get(seatType) - quantity);
        return seatPrices.get(seatType) * quantity;
    }
}

class Screen {
    private final int floorNumber;
    private final SeatOfScreen seatOfScreen;
    private final Movie movie;
    private final List<Audience> audience;

    public Screen(int floorNumber, SeatOfScreen seatOfScreen, Movie movie, List<Audience> audience) {
        this.floorNumber = floorNumber;
        this.seatOfScreen = seatOfScreen;
        this.movie = movie;
        this.audience = audience;
    }
}

class Movie {
    private final int id;
    private final String name;
    private final String director;
    private final String description;
    private final Duration duration;
    private final LocalDate releaseDate;
    private final List<LanguageType> languages;
    private final Rating rating;
    private final Genre genre;

    public Movie(int id, String name, String director, String description, Duration duration,
                 LocalDate releaseDate, List<LanguageType> languages, Rating rating, Genre genre) {
        this.id = id;
        this.name = name;
        this.director = director;
        this.description = description;
        this.duration = duration;
        this.releaseDate = releaseDate;
        this.languages = languages;
        this.rating = rating;
        this.genre = genre;
    }
}

class MovieCollection {
    private final List<Movie> movies;

    public MovieCollection(List<Movie> movies) {
        this.movies = movies;
    }
}

class Audience {
    private final String name;
    private final GenderType gender;
    private static String phoneNumber;

    public Audience(String name, GenderType gender) {
        this.name = name;
        this.gender = gender;
    }

    public static void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
        Audience.phoneNumber = phoneNumber;
    }
}

class FoodAndBeverages {
    private final FoodBeverageType type;
    private final int quantity;
    private final double price;

    public FoodAndBeverages(int quantity, FoodBeverageType type) {
        this.quantity = quantity;
        this.type = type;
        this.price = switch (type) {
            case POPCORN -> 100 * quantity;
            case COFFEE -> 50 * quantity;
            case PANEERNUGGET -> 280 * quantity;
            default -> throw new RuntimeException("Invalid item: " + type);
        };
    }

    public double getPrice() {
        return price;
    }
}

class CinemaHall {
    private final List<Screen> screens;
    private final int washrooms;
    private final String address;
    private final MovieCollection movies;

    public CinemaHall(List<Screen> screens, int washrooms, String address, MovieCollection movies) {
        this.screens = screens;
        this.washrooms = washrooms;
        this.address = address;
        this.movies = movies;
    }
}

class CalculateTicketPricing {
    public static double calculateTotalPrice(List<FoodAndBeverages> food, double seatPrice) {
        double total = seatPrice;
        for (FoodAndBeverages item : food) {
            total += item.getPrice();
        }
        return total;
    }
}
