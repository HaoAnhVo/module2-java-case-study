package case_study.service;

import case_study.model.cinema_manage.Cinema;
import case_study.model.movie_manage.Movie;
import case_study.model.movie_manage.ShowTime;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ShowTimeService{

    private static final String SHOWTIMES_FILE_PATH = "src/case_study/repository/showTimes.csv";

    MovieService movieService = new MovieService();
    public Map<String, Movie> movies = movieService.getAllMovies();

    public ShowTimeService() {
        readShowTimesFromFile(movies);
        writeShowTimesToFile(movies);
    }

    public void writeShowTimesToFile(Map<String, Movie> movies) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SHOWTIMES_FILE_PATH))) {
            writer.write("Movie name,Cinema Name,Room,Start time,End time\n");
            for (Map.Entry<String, Movie> movieEntry : movies.entrySet()) {
                String movieName = movieEntry.getKey();
                Movie movie = movieEntry.getValue();
                Map<String, Map<String, ShowTime>> showTimesByCinema = movie.getShowTimesByCinema();

                if (showTimesByCinema.isEmpty()) {
                    System.out.println("You haven't set a showing time for movie " + movieName);
                    continue;
                }

                for (Map.Entry<String, Map<String, ShowTime>> cinemaEntry : showTimesByCinema.entrySet()) {
                    String cinemaName = cinemaEntry.getKey();
                    Map<String, ShowTime> showTimesByTheater = cinemaEntry.getValue();

                    for (Map.Entry<String, ShowTime> theaterEntry : showTimesByTheater.entrySet()) {
                        String theaterName = theaterEntry.getKey();
                        ShowTime showTime = theaterEntry.getValue();

                        writer.write(String.format("%s,%s,%s,%s,%s%n",
                                movieName,
                                cinemaName,
                                theaterName,
                                showTime.getStartTime(),
                                showTime.getEndTime()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readShowTimesFromFile(Map<String, Movie> movies) {
        try (BufferedReader reader = new BufferedReader(new FileReader(SHOWTIMES_FILE_PATH))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String movieName = parts[0];
                    String cinemaName = parts[1];
                    String theaterName = parts[2];
                    String startTime = parts[3];
                    String endTime = parts[4];

                    ShowTime showTime = new ShowTime(startTime, endTime, theaterName, cinemaName);
                    if (movies.containsKey(movieName)) {
                        Movie movie = movies.get(movieName);
                        movie.addShowTime(cinemaName, theaterName, showTime);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addShowTime(String movieName, String cinemaName, String theaterName, ShowTime showTime, Map<String, Movie> movies, Map<String, Cinema> cinemas) {
        if (movies.containsKey(movieName) && cinemas.containsKey(cinemaName) &&
                cinemas.get(cinemaName).getRooms().containsKey(theaterName)) {
            Movie movie = movies.get(movieName);
            movie.addShowTime(cinemaName, theaterName, showTime);
            writeShowTimesToFile(movies);
            System.out.println("Showtime added successfully.");
        } else {
            System.out.println("Invalid cinema, theater, or movie name.");
        }
    }

    public void updateShowTime(String movieName, String cinemaName, String theaterName, ShowTime showTime, Map<String, Movie> movies) {
        Movie movie = movies.get(movieName);
        if (movie != null) {
            movie.updateShowTime(cinemaName, theaterName, showTime);
            writeShowTimesToFile(movies);
        }
    }

    public void showShowTime(Map<String, Movie> movies,String movieName) {
        Movie movie = movies.get(movieName);
        if (movie == null) {
            System.out.println("Movie not found.");
            return;
        }
        Map<String, Map<String, ShowTime>> showTimesByCinema = movie.getShowTimesByCinema();
        for (Map.Entry<String, Map<String, ShowTime>> cinemaEntry : showTimesByCinema.entrySet()) {
            String cinemaName = cinemaEntry.getKey();
            Map<String, ShowTime> showTimesByTheater = cinemaEntry.getValue();
            for (Map.Entry<String, ShowTime> theaterEntry : showTimesByTheater.entrySet()) {
                String theaterName = theaterEntry.getKey();
                ShowTime showTime = theaterEntry.getValue();
                System.out.println("Cinema: " + cinemaName);
                System.out.println("Theater: " + theaterName);
                System.out.println("Start Time: " + showTime.getStartTime());
                System.out.println("End Time: " + showTime.getEndTime());
                System.out.println("---------------------------");
            }
        }
    }

    public void deleteShowTime(String movieName, String cinemaName, String theaterName, String showTime, Map<String, Movie> movies, Map<String, Cinema> cinemas) {
        if (movies.containsKey(movieName) && cinemas.containsKey(cinemaName) &&
                cinemas.get(cinemaName).getRooms().containsKey(theaterName)) {
            Movie movie = movies.get(movieName);
            Map<String, Map<String, ShowTime>> showTimesByCinema = movie.getShowTimesByCinema();

            if (showTimesByCinema.containsKey(cinemaName) &&
                    showTimesByCinema.get(cinemaName).containsKey(theaterName) &&
                    showTimesByCinema.get(cinemaName).get(theaterName).getStartTime().equals(showTime)) {
                showTimesByCinema.get(cinemaName).remove(theaterName);

                if (showTimesByCinema.get(cinemaName).isEmpty()) {
                    showTimesByCinema.remove(cinemaName);
                }
                System.out.println("Remove successfully.");
                writeShowTimesToFile(movies);
            } else {
                System.out.println("Not found showTime to remove.");
            }
        } else {
            System.out.println("Invalid cinema, theater, or movie name.");
        }
    }

    public List<ShowTime> getShowTimes(String cinemaName, String theaterName, String movieName, Map<String, Movie> movies) {
        if (movies.containsKey(movieName)) {
            Map<String, Map<String, ShowTime>> showTimesByCinema = movies.get(movieName).getShowTimesByCinema();
            if (showTimesByCinema.containsKey(cinemaName) && showTimesByCinema.get(cinemaName).containsKey(theaterName)) {
                return Collections.singletonList(showTimesByCinema.get(cinemaName).get(theaterName));
            }
        }
        return new ArrayList<>();
    }
}
