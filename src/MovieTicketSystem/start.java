package MovieTicketSystem;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
//Recheck with chatgpt
public class start {
    public static void main(String[] args) {

        int movieId = 1;
        ArrayList <LanguageType> languageSupported1 = new ArrayList<>();
        languageSupported1.add(LanguageType.HINDI);
        languageSupported1.add(LanguageType.ENGLISH);
        languageSupported1.add(LanguageType.TELGU);
        languageSupported1.add(LanguageType.TAMIL);
        Duration movieDuration = Duration.ofHours(2).plusMinutes(45);
        Movie movie1 = new Movie(movieId++, "Mufasa", "Director", "Lion Story", movieDuration,
                LocalDate.of(2024, 12, 3), languageSupported1, Rating.GENERAL, Genre.ANIMATION);

        List<Movie> movieList = new ArrayList<>();
        movieList.add(movie1);

        MovieCollection movieCollection = new MovieCollection(movieList);

        Audience a = new Audience("Sanskar", GenderType.MALE);
        Audience b = new Audience("Kamya", GenderType.FEMALE);
        Audience.setPhoneNumber("9875614692");

        List<Audience> audienceList = new ArrayList<>();
        audienceList.add(a);
        audienceList.add(b);

        SeatOfScreen seatOfScreen = new SeatOfScreen(200,50,25);
        Screen screen1 = new Screen(1, seatOfScreen, movie1, audienceList);
        double seatAmount = seatOfScreen.bookSeats(2,SeatType.DELUXE);
        audienceList.clear();


        ArrayList<Screen> screenList = new ArrayList<>();
        screenList.add(screen1);

        CinemaHall cinemaHall = new CinemaHall(screenList,5,"Barrackpore", movieCollection);

        FoodAndBeverages foodAndBeverages1 = new FoodAndBeverages(2, FoodBeverageType.COFFEE);
        FoodAndBeverages foodAndBeverages2 = new FoodAndBeverages(3, FoodBeverageType.POPCORN);
        FoodAndBeverages foodAndBeverages3 = new FoodAndBeverages(1, FoodBeverageType.PANEERNUGGET);

        List<FoodAndBeverages> foodAndBeveragesList = new ArrayList<>();
        foodAndBeveragesList.add(foodAndBeverages1);
        foodAndBeveragesList.add(foodAndBeverages2);
        foodAndBeveragesList.add(foodAndBeverages3);

        double finalAmount = CalculateTicketPricing.calculateTotalPrice(foodAndBeveragesList, seatAmount);

        System.out.println("Food Total: " + (finalAmount - seatAmount) + " Rs.\nSeat Total: " + seatAmount + " Rs.");
        System.out.println("Total: "+ finalAmount +" Rs.");

    }
}
