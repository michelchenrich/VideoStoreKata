package hm.videostore;

import java.util.List;

public class PrintStatementUseCase {
    private List<RentalData> rentals;

    public PrintStatementUseCase(List<RentalData> rentals) {
        this.rentals = rentals;
    }

    public StatementData execute() {
        StatementData statement = new StatementData();

        for (RentalData rental : rentals) {
            int typeCode = Context.movieRepository.findById(rental.movieId).typeCode;
            if (typeCode == 1)
                if (rental.daysRented > 2)
                    statement.totalOwed = Rates.regular + (Rates.regularPenalty * (rental.daysRented - 2));
                else
                    statement.totalOwed = Rates.regular;
            else if (typeCode == 2)
                if (rental.daysRented > 3)
                    statement.totalOwed = Rates.childrens + (Rates.childrensPenalty * (rental.daysRented - 3));
                else
                    statement.totalOwed = Rates.childrens;
            else
                statement.totalOwed = Rates.newRelease * rental.daysRented;
        }

        return statement;
    }
}
