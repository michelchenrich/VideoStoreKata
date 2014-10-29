package hm.videostore;

import static org.junit.Assert.assertNotNull;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class VideoStoreTest {
    @Test
    public void whenRentingOneRegularMovieForOneDay_TotalShouldBeTheRegularRate() {
        Rates.regular = 2.0;

        List<Rental> rentals = new ArrayList<Rental>();

        Rental regularMovie = new Rental();
        regularMovie.movieId = "Regular";
        regularMovie.daysRented = 1;

        Statement statement = new PrintStatementUseCase(rentals).execute();

        assertNotNull(statement);
    }
}
