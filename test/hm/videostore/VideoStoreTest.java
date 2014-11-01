package hm.videostore;

import hm.videostore.customer.CreateCustomerRequest;
import hm.videostore.customer.CreateCustomerUseCase;
import hm.videostore.data.Context;
import hm.videostore.data.Customer;
import hm.videostore.data.Movie;
import static hm.videostore.data.Movie.Type.*;
import hm.videostore.movie.CreateMovieRequest;
import hm.videostore.movie.CreateMovieUseCase;
import hm.videostore.rent.PrintStatementRequest;
import hm.videostore.rent.PrintStatementResponse;
import hm.videostore.rent.PrintStatementUseCase;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VideoStoreTest {
    private List<PrintStatementRequest.Rental> rentals;
    private PrintStatementResponse statement;
    private String customerId;
    private int currentStatementLine = 0;

    private void givenRental(Movie.Type type, int daysRented) {
        givenRental(type, daysRented, String.format("Movie no. %f", Math.random()));
    }

    private void givenRental(Movie.Type type, int daysRented, String movieName) {
        String movieId = createMovie(type, movieName);

        PrintStatementRequest.Rental rental = new PrintStatementRequest.Rental();
        rental.movieId = movieId;
        rental.daysRented = daysRented;
        rentals.add(rental);
    }

    private void givenCustomer(String name) {
        customerId = createCustomer(name);
    }

    private String createCustomer(String name) {
        CreateCustomerRequest request = new CreateCustomerRequest();
        request.name = name;
        return new CreateCustomerUseCase().execute(request).id;
    }

    private String createMovie(Movie.Type type, String name) {
        CreateMovieRequest request = new CreateMovieRequest();
        request.type = type;
        request.name = name;
        return new CreateMovieUseCase().execute(request).id;
    }

    private void printStatement() {
        PrintStatementRequest request = new PrintStatementRequest();
        request.customerId = customerId;
        request.rentals = rentals.toArray(new PrintStatementRequest.Rental[rentals.size()]);
        statement = new PrintStatementUseCase().execute(request);
        rentals.clear();
    }

    private void assertAmounts(double gross) {
        assertAmounts(gross, gross);
    }

    private void assertAmounts(double gross, double net) {
        assertEquals(gross, statement.grossAmount, .0001);
        assertEquals(net, statement.netAmount, .0001);
    }

    private void assertPointsGranted(int points) {
        assertEquals(points, statement.pointsGranted);
    }

    private void assertCustomerName(String name) {
        assertEquals(name, statement.customerName);
    }

    private void assertStatementLine(String movieName, int daysRented, double price, int points) {
        assertNotNull(statement.lines);
        PrintStatementResponse.Line line = statement.lines[currentStatementLine];
        assertEquals(movieName, line.movieName);
        assertEquals(daysRented, line.daysRented);
        assertEquals(price, line.price, .0001);
        assertEquals(points, line.points);
        currentStatementLine++;
    }

    @Before
    public void setUp() {
        Context.movieRepository = new InMemoryRepository<Movie>();
        Context.customerRepository = new InMemoryRepository<Customer>();
        rentals = new ArrayList<PrintStatementRequest.Rental>();
        givenCustomer("Default Customer");
    }

    @Test
    public void whenRentingOneRegularMovieForOneDay_TotalShouldBeTheRegularRate() {
        givenRental(REGULAR, 1);
        printStatement();
        assertAmounts(2);
    }

    @Test
    public void whenRentingOneRegularMoviePorTwoDays_TotalShouldBeTheRegularRate() {
        givenRental(REGULAR, 2);
        printStatement();
        assertAmounts(2);
    }

    @Test
    public void whenRentingOneRegularMovieForThreeDays_TotalShouldBeTheRegularRatePlusRegularPentaltyRate() {
        givenRental(REGULAR, 3);
        printStatement();
        assertAmounts(2 + 1.5);
    }

    @Test
    public void whenRentingOneRegularMovieForFourDays_TotalShouldBeTheRegularRatePlusRegularPenaltyRateTimesTwo() {
        givenRental(REGULAR, 4);
        printStatement();
        assertAmounts(2 + (1.5 * 2));
    }

    @Test
    public void whenRentingOneChildrensMovieForOneDay_TotalShouldBeTheChildrensRate() {
        givenRental(CHILDRENS, 1);
        printStatement();
        assertAmounts(1.5);
    }

    @Test
    public void whenRentingOneChildrensMovieForThreeDays_TotalShouldBeTheChildrensRate() {
        givenRental(CHILDRENS, 3);
        printStatement();
        assertAmounts(1.5);
    }

    @Test
    public void whenRentingOneChildrensMovieForFourDays_TotalShouldBeTheChildrensRatePlusChildrensPenalty() {
        givenRental(CHILDRENS, 4);
        printStatement();
        assertAmounts(1.5 + 1.5);
    }

    @Test
    public void whenRentingOneChildrensMovieForFiveDays_TotalShouldBeTheChildrensRatePlusChildrensPenaltyTimesTwo() {
        givenRental(CHILDRENS, 5);
        printStatement();
        assertAmounts(1.5 + (1.5 * 2));
    }

    @Test
    public void whenRentingOneNewReleaseForOneDay_TotalShouldBeTheNewReleaseRate() {
        givenRental(NEW_RELEASE, 1);
        printStatement();
        assertAmounts(3);
    }

    @Test
    public void whenRentingOneNewReleaseForTwoDays_TotalShouldBeTheNewReleaseRateTimesTwo() {
        givenRental(NEW_RELEASE, 2);
        printStatement();
        assertAmounts(3 * 2);
    }

    @Test
    public void whenRentingOneNewReleaseForThreeDays_TotalShouldBeTheNewReleaseRateTimesThree() {
        givenRental(NEW_RELEASE, 3);
        printStatement();
        assertAmounts(3 * 3);
    }

    @Test
    public void whenRetingManyMovies_TotalShouldBeTheSum() {
        givenRental(REGULAR, 3);
        givenRental(CHILDRENS, 3);
        givenRental(NEW_RELEASE, 3);
        printStatement();
        assertAmounts((2 + 1.5) + (1.5) + (3 * 3));
    }

    @Test
    public void whenRentingOneRegularMovie_FrequentRenterPointsShouldBeOne() {
        givenRental(REGULAR, 3);
        printStatement();
        assertPointsGranted(1);
    }

    @Test
    public void whenRentingOneChildrensMovie_FrequentRenterPointsShouldBeOne() {
        givenRental(CHILDRENS, 3);
        printStatement();
        assertPointsGranted(1);
    }

    @Test
    public void whenRentingOneNewReleaseForOneDay_FrequentRenterPointsShouldBeOne() {
        givenRental(NEW_RELEASE, 1);
        printStatement();
        assertPointsGranted(1);
    }

    @Test
    public void whenRentingOneNewReleaseForMoreThanTwoDays_FrequentRenterPointsShouldBeTwo() {
        givenRental(NEW_RELEASE, 5);
        printStatement();
        assertPointsGranted(2);
    }

    @Test
    public void whenRentingManyMovies_FrequentRenterPointsShouldBeTheSum() {
        givenRental(REGULAR, 1);
        givenRental(CHILDRENS, 2);
        givenRental(NEW_RELEASE, 3);
        printStatement();
        assertPointsGranted(1 + 1 + 2);
    }

    @Test
    public void givenACustomerId_ItsNameMustBePrintedInTheStatement() {
        givenCustomer("Customer Name");
        printStatement();
        assertCustomerName("Customer Name");
    }

    @Test
    public void givenAMovie_ShouldPrintItsDetailsInAStatementLine() {
        givenRental(REGULAR, 2, "Regular Movie");
        givenRental(NEW_RELEASE, 3, "Newly Released Movie");
        printStatement();
        assertStatementLine("Regular Movie", 2, 2.0, 1);
        assertStatementLine("Newly Released Movie", 3, 9.0, 2);
    }

    @Test
    public void givenASecondRentalForSameCustomer_ThePreviouslyReceivedPointsShouldBeDeductedFromTheNetAmount() {
        givenCustomer("Customer Name");
        givenRental(REGULAR, 1);
        givenRental(CHILDRENS, 2);
        givenRental(NEW_RELEASE, 3);
        printStatement();

        givenRental(NEW_RELEASE, 2);
        printStatement();
        assertAmounts(6, 2);
    }
}