package hm.videostore.rent.strategy;

import hm.videostore.data.Movie;

public class RentStrategyFactory {
    public static RentStrategy make(Movie.Type type, int daysRented) {
        switch (type) {
            case CHILDRENS:
                return new ChildrensRentStrategy(daysRented);
            case NEW_RELEASE:
                return new NewReleaseRentStrategy(daysRented);
            default:
                return new RegularRentStrategy(daysRented);
        }
    }
}
