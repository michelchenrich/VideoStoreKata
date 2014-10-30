package hm.videostore;

public class NewReleaseMovie extends Movie {
    public double calculateRentalPrice(int daysRented) {
        double value;
        value = 3 * daysRented;
        return value;
    }

    public int calculateFrequentRenterPoints(int daysRented) {
        if (daysRented > 2)
            return 2;
        return super.calculateFrequentRenterPoints(daysRented);
    }
}
