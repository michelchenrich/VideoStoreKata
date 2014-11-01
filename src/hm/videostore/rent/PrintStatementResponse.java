package hm.videostore.rent;

public class PrintStatementResponse {
    public double totalOwed;
    public int frequentRenterPoints;
    public String customerName;
    public Line[] lines;

    public static class Line {
        public String movieName;
        public int daysRented;
        public double price;
        public int points;
    }
}