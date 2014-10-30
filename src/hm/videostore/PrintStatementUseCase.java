package hm.videostore;

import static hm.videostore.Context.movieRepository;
import hm.videostore.repository.MovieData;

import java.util.List;

public class PrintStatementUseCase {
    private List<RentalData> rentals;
    private StatementData statement;

    public PrintStatementUseCase(List<RentalData> rentals) {
        this.rentals = rentals;
    }

    public StatementData execute() {
        statement = new StatementData();

        for (RentalData rental : rentals) sumRental(rental);

        return statement;
    }

    private void sumRental(RentalData rental) {
        Movie movie = makeMovie(movieRepository.findById(rental.movieId));
        statement.totalOwed = movie.calculateRentalPrice(rental.daysRented);
    }

    private Movie makeMovie(MovieData data) {
        Movie movie;
        if (data.typeCode == 1) movie = new RegularMovie();
        else if (data.typeCode == 2) movie = new ChildrensMovie();
        else movie = new NewReleaseMovie();
        return movie;
    }
}