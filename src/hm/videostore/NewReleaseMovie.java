package hm.videostore;

public class NewReleaseMovie extends Movie {
    public double calculateRentalPrice(int daysRented) {
        double value;
        value = 3 * daysRented;
        return value;
    }
}
