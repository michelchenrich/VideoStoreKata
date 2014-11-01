package hm.videostore.rent;

import static hm.videostore.data.Context.customerRepository;
import static hm.videostore.data.Context.movieRepository;
import hm.videostore.data.Customer;
import hm.videostore.data.Movie;
import static hm.videostore.rent.PrintStatementResponse.Line;
import hm.videostore.rent.strategy.RentStrategy;
import hm.videostore.rent.strategy.RentStrategyFactory;

public class PrintStatementUseCase {
    public PrintStatementResponse execute(PrintStatementRequest request) {
        Customer customer = customerRepository.findById(request.customerId);
        PrintStatementResponse response = new PrintStatementResponse();
        response.lines = new Line[request.rentals.length];
        response.customerName = customer.name;
        for (int i = 0; i < request.rentals.length; i++) {
            Line line = makeLine(request.rentals[i]);
            response.grossAmount += line.price;
            response.netAmount += line.price;
            response.pointsGranted += line.points;
            response.lines[i] = line;
        }
        response.netAmount -= customer.frequentRenterPoints;
        customer.frequentRenterPoints = response.pointsGranted;
        customerRepository.save(customer);
        return response;
    }

    private Line makeLine(PrintStatementRequest.Rental rental) {
        Movie movie = movieRepository.findById(rental.movieId);
        RentStrategy rentStrategy = RentStrategyFactory.make(movie.type, rental.daysRented);

        Line line = new Line();
        line.movieName = movie.name;
        line.daysRented = rental.daysRented;
        line.price = rentStrategy.calculatePrice();
        line.points = rentStrategy.calculatePoints();
        return line;
    }
}