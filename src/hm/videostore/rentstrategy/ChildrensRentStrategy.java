package hm.videostore.rentstrategy;

public class ChildrensRentStrategy extends RentStrategy {
    public ChildrensRentStrategy(int daysRented) {
        super(daysRented);
    }

    public double calculateRentalPrice() {
        double value;
        if (daysRented > 3)
            value = 1.5 + (1.5 * (daysRented - 3));
        else
            value = 1.5;
        return value;
    }
}
