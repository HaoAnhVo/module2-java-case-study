package case_study.model.movie_manage;

import java.util.HashMap;
import java.util.Map;

public class Movie {
    private String name;
    private String genre;
    private String duration;
    private String image;
    private String trailer;
    private String description;
    private Map<String, Map<String, ShowTime>> showTimesByCinema;

    public Movie(String name, String genre, String duration, String image, String trailer, String description) {
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.image = image;
        this.trailer = trailer;
        this.description = description;
        this.showTimesByCinema = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public String getImage() {
        return image;
    }

    public String getTrailer() {
        return trailer;
    }

    public String getDescription() {
        return description;
    }


    public Map<String, Map<String, ShowTime>> getShowTimesByCinema() {
        return showTimesByCinema;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShowTimesByCinema(Map<String, Map<String, ShowTime>> showTimesByCinema) {
        this.showTimesByCinema = showTimesByCinema;
    }


    public void addShowTime(String cinemaName, String theaterName, ShowTime showTime) {
        showTimesByCinema.putIfAbsent(cinemaName, new HashMap<>());
        showTimesByCinema.get(cinemaName).put(theaterName, showTime);
    }


    public void updateShowTime(String cinemaName, String theaterName, ShowTime showTime) {
        if (showTimesByCinema.containsKey(cinemaName) && showTimesByCinema.get(cinemaName).containsKey(theaterName)) {
            showTimesByCinema.get(cinemaName).put(theaterName, showTime);
            System.out.println("Showtime updated successfully.");
        } else {
            System.out.println("Theater " + cinemaName + " and theater " + theaterName + " do not have screening information yet!");
        }
    }

    public void deleteShowTime(String cinemaName, String theaterName) {
        if (showTimesByCinema.containsKey(cinemaName)) {
            showTimesByCinema.get(cinemaName).remove(theaterName);
        }
    }


    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration +
                ", image='" + image + '\'' +
                ", trailer='" + trailer + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
