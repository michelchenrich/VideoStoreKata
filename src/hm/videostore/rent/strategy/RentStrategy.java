package hm.videostore.rent.strategy;

public abstract class RentStrategy {
    protected int daysRented;

    public RentStrategy(int daysRented) {
        this.daysRented = daysRented;
    }

    public abstract double calculatePrice();

    public int calculatePoints() {
        return 1;
    }
}
