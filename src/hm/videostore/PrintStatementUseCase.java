package hm.videostore;

import java.util.List;

public class PrintStatementUseCase {
    private List<Rental> rentals;

    public PrintStatementUseCase(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public Statement execute() {
        Statement statement = new Statement();

        for (Rental rental : rentals) {
            Movie movie = Context.gateway.findById(rental.movieId);
            if (movie.getTypeCode() == 1)
                if (rental.daysRented > 2)
                    statement.totalOwed = Rates.regular + (Rates.regularPenalty * (rental.daysRented - 2));
                else
                    statement.totalOwed = Rates.regular;
            else
                statement.totalOwed = Rates.childrens;
        }

        return statement;
    }
}
