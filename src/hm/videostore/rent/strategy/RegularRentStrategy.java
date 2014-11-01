package hm.videostore.rent.strategy;

public class RegularRentStrategy extends RentStrategy {
    public RegularRentStrategy(int daysRented) {
        super(daysRented);
    }

    public double calculatePrice() {
        double value;
        if (daysRented > 2)
            value = 2 + (1.5 * (daysRented - 2));
        else
            value = 2;
        return value;
    }
}
