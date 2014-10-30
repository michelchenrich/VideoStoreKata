package hm.videostore.rentstrategy;

public abstract class RentStrategy {
    protected int daysRented;

    public RentStrategy(int daysRented) {
        this.daysRented = daysRented;
    }

    public abstract double calculateRentalPrice();

    public int calculateFrequentRenterPoints() {
        return 1;
    }
}
