package hm.videostore;

import static hm.videostore.Context.movieRepository;
import hm.videostore.rentstrategy.RentStrategy;
import hm.videostore.rentstrategy.RentStrategyFactory;
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
        RentStrategy rentStrategy = getRentStrategy(rental);
        statement.totalOwed += rentStrategy.calculateRentalPrice();
        statement.frequentRenterPoints += rentStrategy.calculateFrequentRenterPoints();
    }

    private RentStrategy getRentStrategy(RentalData rental) {
        MovieData movieData = movieRepository.findById(rental.movieId);
        return RentStrategyFactory.make(movieData.typeCode, rental.daysRented);
    }
}