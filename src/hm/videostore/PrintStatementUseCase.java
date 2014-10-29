package hm.videostore;

import java.util.List;

public class PrintStatementUseCase {
    public PrintStatementUseCase(List<Rental> rentals) {
    }

    public Statement execute() {
        return new Statement();
    }
}
