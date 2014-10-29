package hm.videostore;

import hm.videostore.repository.MovieData;
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

    @Before
    public void setUp() {
        Context.movieRepository = new InMemoryRepository<MovieData>();

        double seed = Math.random();
        Rates.regular = seed * 2;
        Rates.regularPenalty = seed * 3;
        Rates.childrens = seed * 1.5;
        Rates.childrensPenalty = seed * 2.5;
        Rates.newRelease = seed * 3.5;
        rentals = new ArrayList<RentalData>();
    }

    @Test
    public void whenRentingOneRegularMovieForOneDay_TotalShouldBeTheRegularRate() {
        givenRental(Type.REGULAR, 1);
        printStatement();
        assertTotalOwed(Rates.regular);
    }

    @Test
    public void whenRentingOneRegularMoviePorTwoDays_TotalShouldBeTheRegularRate() {
        givenRental(Type.REGULAR, 2);
        printStatement();
        assertTotalOwed(Rates.regular);
    }

    @Test
    public void whenRentingOneRegularMovieForThreeDays_TotalShouldBeTheRegularRatePlusRegularPentaltyRate() {
        givenRental(Type.REGULAR, 3);
        printStatement();
        assertTotalOwed(Rates.regular + Rates.regularPenalty);
    }

    @Test
    public void whenRentingOneRegularMovieForFourDays_TotalShouldBeTheRegularRatePlusRegularPenaltyRateTimesTwo() {
        givenRental(Type.REGULAR, 4);
        printStatement();
        assertTotalOwed(Rates.regular + (Rates.regularPenalty * 2));
    }

    @Test
    public void whenRentingOneChildrensMovieForOneDay_TotalShouldBeTheChildrensRate() {
        givenRental(Type.CHILDRENS, 1);
        printStatement();
        assertTotalOwed(Rates.childrens);
    }

    @Test
    public void whenRentingOneChildrensMovieForThreeDays_TotalShouldBeTheChildrensRate() {
        givenRental(Type.CHILDRENS, 3);
        printStatement();
        assertTotalOwed(Rates.childrens);
    }

    @Test
    public void whenRentingOneChildrensMovieForFourDays_TotalShouldBeTheChildrensRatePlusChildrensPenalty() {
        givenRental(Type.CHILDRENS, 4);
        printStatement();
        assertTotalOwed(Rates.childrens + Rates.childrensPenalty);
    }

    @Test
    public void whenRentingOneChildrensMovieForFiveDays_TotalShouldBeTheChildrensRatePlusChildrensPenaltyTimesTwo() {
        givenRental(Type.CHILDRENS, 5);
        printStatement();
        assertTotalOwed(Rates.childrens + (Rates.childrensPenalty * 2));
    }

    @Test
    public void whenRentingOneNewReleaseForOneDay_TotalShouldBeTheNewReleaseRate() {
        givenRental(Type.NEW_RELEASE, 1);
        printStatement();
        assertTotalOwed(Rates.newRelease);
    }

    @Test
    public void whenRentingOneNewReleaseForTwoDays_TotalShouldBeTheNewReleaseRateTimesTwo() {
        givenRental(Type.NEW_RELEASE, 2);
        printStatement();
        assertTotalOwed(Rates.newRelease * 2);
    }

    @Test
    public void whenRentingOneNewReleaseForThreeDays_TotalShouldBeTheNewReleaseRateTimesThree() {
        givenRental(Type.NEW_RELEASE, 3);
        printStatement();
        assertTotalOwed(Rates.newRelease * 3);
    }
}