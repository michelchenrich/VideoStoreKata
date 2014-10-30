package hm.videostore.rentstrategy;

import hm.videostore.data.MovieType;

public class RentStrategyFactory {
    public static RentStrategy make(MovieType type, int daysRented) {
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
