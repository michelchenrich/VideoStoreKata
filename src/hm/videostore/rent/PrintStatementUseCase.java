package hm.videostore.rent;

import static hm.videostore.data.Context.customerRepository;
import static hm.videostore.data.Context.movieRepository;
import hm.videostore.data.Customer;
import hm.videostore.data.Movie;
import static hm.videostore.rent.PrintStatementRequest.Rental;
import static hm.videostore.rent.PrintStatementResponse.Line;
import hm.videostore.rent.strategy.RentStrategy;
import hm.videostore.rent.strategy.RentStrategyFactory;

public class PrintStatementUseCase {
    public PrintStatementResponse execute(PrintStatementRequest request) {
        return new Runner(request).execute();
    }

    private static class Runner {
        private PrintStatementResponse response;
        private PrintStatementRequest request;
        private int currentLine;
        private Customer customer;

        public Runner(PrintStatementRequest request) {
            this.request = request;
            response = new PrintStatementResponse();
            response.lines = new Line[request.rentals.length];
            customer = customerRepository.findById(request.customerId);
        }

        public PrintStatementResponse execute() {
            writeCustomer();
            sumRentals();
            deductPreviousPoints();
            updateGrantedPoints();
            return response;
        }

        private void writeCustomer() {
            response.customerName = customer.name;
        }

        private void sumRentals() {
            for (Rental rental : request.rentals) sumRental(rental);
        }

        private void sumRental(Rental rental) {
            Line line = makeLine(rental);
            response.grossAmount += line.price;
            response.netAmount += line.price;
            response.pointsGranted += line.points;
            response.lines[currentLine++] = line;
        }

        private Line makeLine(Rental rental) {
            Movie movie = movieRepository.findById(rental.movieId);
            RentStrategy rentStrategy = RentStrategyFactory.make(movie.type, rental.daysRented);
            Line line = new Line();
            line.movieName = movie.name;
            line.daysRented = rental.daysRented;
            line.price = rentStrategy.calculatePrice();
            line.points = rentStrategy.calculatePoints();
            return line;
        }

        private void deductPreviousPoints() {
            response.netAmount -= customer.frequentRenterPoints;
        }

        private void updateGrantedPoints() {
            customer.frequentRenterPoints = response.pointsGranted;
            customerRepository.save(customer);
        }
    }
}