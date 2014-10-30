package hm.videostore;

import static hm.videostore.Context.customerRepository;
import static hm.videostore.Context.movieRepository;
import hm.videostore.data.MovieData;
import hm.videostore.rentstrategy.RentStrategy;
import hm.videostore.rentstrategy.RentStrategyFactory;

import java.util.List;

public class PrintStatementUseCase {
    private String customerId;
    private List<RentalData> rentals;
    private StatementData statement;

    public PrintStatementUseCase(String customerId, List<RentalData> rentals) {
        this.customerId = customerId;
        this.rentals = rentals;
    }

    public StatementData execute() {
        statement = new StatementData();
        writeCustomerName();
        for (RentalData rental : rentals) sumRental(rental);
        return statement;
    }

    private void writeCustomerName() {
        statement.customerName = customerRepository.findById(customerId).name;
    }

    private void sumRental(RentalData rental) {
        RentStrategy rentStrategy = getRentStrategy(rental);
        statement.totalOwed += rentStrategy.calculatePrice();
        statement.frequentRenterPoints += rentStrategy.calculatePoints();
    }

    private RentStrategy getRentStrategy(RentalData rental) {
        MovieData movieData = movieRepository.findById(rental.movieId);
        return RentStrategyFactory.make(movieData.type, rental.daysRented);
    }
}