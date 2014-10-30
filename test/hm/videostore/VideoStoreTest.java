package hm.videostore;

import static hm.videostore.Type.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VideoStoreTest {
    private List<RentalData> rentals;
    private StatementData statement;

    private void givenRental(Type type, int daysRented) {
        String movieId = createMovie(type);

        RentalData rental = new RentalData();
        rental.movieId = movieId;
        rental.daysRented = daysRented;
        rentals.add(rental);
    }

    private String createMovie(Type type) {
        return new CreateMovieUseCase(type.code, String.format("Movie no. %f", Math.random())).execute();
    }

    private void printStatement() {
        statement = new PrintStatementUseCase(rentals).execute();
    }

    private void assertTotalOwed(double totalOwed) {
        assertEquals(totalOwed, statement.totalOwed, .0001);
    }

    private void assertFrequentRenterPoints(int points) {
        assertEquals(points, statement.frequentRenterPoints);
    }

    @Before
    public void setUp() {
        Context.movieRepository = new InMemoryMovieRepository();
        rentals = new ArrayList<RentalData>();
    }

    @Test
    public void whenRentingOneRegularMovieForOneDay_TotalShouldBeTheRegularRate() {
        givenRental(REGULAR, 1);
        printStatement();
        assertTotalOwed(2);
    }

    @Test
    public void whenRentingOneRegularMoviePorTwoDays_TotalShouldBeTheRegularRate() {
        givenRental(REGULAR, 2);
        printStatement();
        assertTotalOwed(2);
    }

    @Test
    public void whenRentingOneRegularMovieForThreeDays_TotalShouldBeTheRegularRatePlusRegularPentaltyRate() {
        givenRental(REGULAR, 3);
        printStatement();
        assertTotalOwed(2 + 1.5);
    }

    @Test
    public void whenRentingOneRegularMovieForFourDays_TotalShouldBeTheRegularRatePlusRegularPenaltyRateTimesTwo() {
        givenRental(REGULAR, 4);
        printStatement();
        assertTotalOwed(2 + (1.5 * 2));
    }

    @Test
    public void whenRentingOneChildrensMovieForOneDay_TotalShouldBeTheChildrensRate() {
        givenRental(CHILDRENS, 1);
        printStatement();
        assertTotalOwed(1.5);
    }

    @Test
    public void whenRentingOneChildrensMovieForThreeDays_TotalShouldBeTheChildrensRate() {
        givenRental(CHILDRENS, 3);
        printStatement();
        assertTotalOwed(1.5);
    }

    @Test
    public void whenRentingOneChildrensMovieForFourDays_TotalShouldBeTheChildrensRatePlusChildrensPenalty() {
        givenRental(CHILDRENS, 4);
        printStatement();
        assertTotalOwed(1.5 + 1.5);
    }

    @Test
    public void whenRentingOneChildrensMovieForFiveDays_TotalShouldBeTheChildrensRatePlusChildrensPenaltyTimesTwo() {
        givenRental(CHILDRENS, 5);
        printStatement();
        assertTotalOwed(1.5 + (1.5 * 2));
    }

    @Test
    public void whenRentingOneNewReleaseForOneDay_TotalShouldBeTheNewReleaseRate() {
        givenRental(NEW_RELEASE, 1);
        printStatement();
        assertTotalOwed(3);
    }

    @Test
    public void whenRentingOneNewReleaseForTwoDays_TotalShouldBeTheNewReleaseRateTimesTwo() {
        givenRental(NEW_RELEASE, 2);
        printStatement();
        assertTotalOwed(3 * 2);
    }

    @Test
    public void whenRentingOneNewReleaseForThreeDays_TotalShouldBeTheNewReleaseRateTimesThree() {
        givenRental(NEW_RELEASE, 3);
        printStatement();
        assertTotalOwed(3 * 3);
    }

    @Test
    public void whenRetingManyMovies_TotalShouldBeTheSum() {
        givenRental(REGULAR, 3);
        givenRental(CHILDRENS, 3);
        givenRental(NEW_RELEASE, 3);
        printStatement();
        assertTotalOwed((2 + 1.5) + (1.5) + (3 * 3));
    }

    @Test
    public void whenRentingOneRegularMovie_FrequentRenterPointsShouldBeOne() {
        givenRental(REGULAR, 3);
        printStatement();
        assertFrequentRenterPoints(1);
    }

    @Test
    public void whenRentingOneChildrensMovie_FrequentRenterPointsShouldBeOne() {
        givenRental(CHILDRENS, 3);
        printStatement();
        assertFrequentRenterPoints(1);
    }

    @Test
    public void whenRentingOneNewReleaseForOneDay_FrequentRenterPointsShouldBeOne() {
        givenRental(NEW_RELEASE, 1);
        printStatement();
        assertFrequentRenterPoints(1);
    }

    @Test
    public void whenRentingOneNewReleaseForMoreThanTwoDays_FrequentRenterPointsShouldBeTwo() {
        givenRental(NEW_RELEASE, 5);
        printStatement();
        assertFrequentRenterPoints(2);
    }

    @Test
    public void whenRentingManyMovies_FrequentRenterPointsShouldBeTheSum() {
        givenRental(REGULAR, 1);
        givenRental(CHILDRENS, 2);
        givenRental(NEW_RELEASE, 3);
        printStatement();
        assertFrequentRenterPoints(1 + 1 + 2);
    }
}