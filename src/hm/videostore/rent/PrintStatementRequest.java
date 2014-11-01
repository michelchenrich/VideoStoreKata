package hm.videostore.rent;

public class PrintStatementRequest {
    public String customerId;
    public Rental[] rentals;

    public static class Rental {
        public String movieId;
        public int daysRented;
    }
}