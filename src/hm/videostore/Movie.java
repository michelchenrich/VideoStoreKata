package hm.videostore;

public abstract class Movie {
    public abstract double calculateRentalPrice(int daysRented);

    public int calculateFrequentRenterPoints(int daysRented) {
        return 1;
    }
}
