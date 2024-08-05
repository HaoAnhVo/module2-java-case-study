package case_study.model.movie_manage;

public class Ticket {
    private static int nextId = 1;

    private final int ticketId;
    private final String userName;
    private final String movieName;
    private final String theaterName;
    private final String cinemaName;
    private final String showTime;
    private final double price;
    private final String ticketType;
    private final int seatNumber;
    private final String status;

    public Ticket(String userName, String movieName, String theaterName, String cinemaName, String showTime, double price, String ticketType, int seatNumber, String status) {
        this.ticketId = nextId++;
        this.userName = userName;
        this.movieName = movieName;
        this.theaterName = theaterName;
        this.cinemaName = cinemaName;
        this.showTime = showTime;
        this.price = price;
        this.ticketType = ticketType;
        this.seatNumber = seatNumber;
        this.status = status;
    }

    public int getTicketId() {
        return ticketId;
    }
    public String getUserName() {
        return userName;
    }
    public String getTheaterName() {
        return theaterName;
    }
    public String getCinemaName() {
        return cinemaName;
    }
    public String getShowTime() {
        return showTime;
    }
    public int getSeatNumber() {
        return seatNumber;
    }

    public String getMovieName() {
        return movieName;
    }

    public double getPrice() {
        return price;
    }

    public String getTicketType() {
        return ticketType;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Ticket ID='" + ticketId + '\'' +
                ", userName='" + userName + '\'' +
                ", movieName='" + movieName + '\'' +
                ", theaterName='" + theaterName + '\'' +
                ", cinemaName='" + cinemaName + '\'' +
                ", showTime='" + showTime + '\'' +
                ", price=" + price + "$" +
                ", ticketType='" + ticketType + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
