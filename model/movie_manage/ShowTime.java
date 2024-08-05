package case_study.model.movie_manage;

public class ShowTime {
    private final String theaterName;
    private final String cinemaName;
    private final String startTime;
    private final String endTime;


    public ShowTime(String startTime, String endTime, String theaterName, String cinemaName) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.theaterName = theaterName;
        this.cinemaName = cinemaName;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }


    @Override
    public String toString() {
        return "ShowTime{" +
                "theaterName='" + theaterName + '\'' +
                ", cinemaName='" + cinemaName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }


}
