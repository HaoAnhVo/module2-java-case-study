package case_study.model.cinema_manage;

public class Theater {
    private String name;
    private int seatCount;

    public Theater(String name, int seatCount) {
        this.name = name;
        this.seatCount = seatCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }


    @Override
    public String toString() {
        return "Theater{" +
                "name='" + name + '\'' +
                ", seatCount=" + seatCount +
                '}';
    }
}
