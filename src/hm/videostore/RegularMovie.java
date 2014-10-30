package hm.videostore;

public class RegularMovie extends Movie {
    public double calculateRentalPrice(int daysRented) {
        double value;
        if (daysRented > 2)
            value = 2 + (1.5 * (daysRented - 2));
        else
            value = 2;
        return value;
    }
}
