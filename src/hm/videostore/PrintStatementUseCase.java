package hm.videostore;

import static hm.videostore.Context.movieRepository;

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
        Movie movie = MovieFactory.make(movieRepository.findById(rental.movieId));
        statement.totalOwed += movie.calculateRentalPrice(rental.daysRented);
        statement.frequentRenterPoints += movie.calculateFrequentRenterPoints(rental.daysRented);
    }
}