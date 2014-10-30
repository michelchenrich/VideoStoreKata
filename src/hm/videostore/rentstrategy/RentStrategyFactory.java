package hm.videostore.rentstrategy;

public class RentStrategyFactory {
    public static RentStrategy make(int typeCode, int daysRented) {
        RentStrategy rentStrategy;
        if (typeCode == 1) rentStrategy = new RegularRentStrategy(daysRented);
        else if (typeCode == 2) rentStrategy = new ChildrensRentStrategy(daysRented);
        else rentStrategy = new NewReleaseRentStrategy(daysRented);
        return rentStrategy;
    }
}
