package hm.videostore.rent;

public class PrintStatementResponse {
    public double grossAmount;
    public int pointsGranted;
    public String customerName;
    public Line[] lines;
    public double netAmount;

    public static class Line {
        public String movieName;
        public int daysRented;
        public double price;
        public int points;
    }
}