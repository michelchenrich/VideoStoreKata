package hm.videostore;

public class ChildrensMovie extends Movie {
    public double calculateRentalPrice(int daysRented) {
        double value;
        if (daysRented > 3)
            value = 1.5 + (1.5 * (daysRented - 3));
        else
            value = 1.5;
        return value;
    }
}
