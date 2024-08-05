package case_study.model.movie_manage;

public enum TicketType {
    REGULAR(10.0),
    VIP(16.0);

    private final double price;

    TicketType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
