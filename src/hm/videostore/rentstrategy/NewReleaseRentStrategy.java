package hm.videostore.rentstrategy;

public class NewReleaseRentStrategy extends RentStrategy {
    public NewReleaseRentStrategy(int daysRented) {
        super(daysRented);
    }

    public double calculateRentalPrice() {
        double value;
        value = 3 * daysRented;
        return value;
    }

    public int calculateFrequentRenterPoints() {
        if (daysRented > 2)
            return 2;
        return super.calculateFrequentRenterPoints();
    }
}
