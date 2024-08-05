package case_study.service;

import case_study.model.movie_manage.Movie;
import case_study.model.movie_manage.ShowTime;

import java.io.*;
import java.util.*;

public class MovieService {
    private static final String MOVIES_FILE_PATH = "src/case_study/repository/movies.csv";

    private Map<String, Movie> movies;

    public MovieService() {
        movies = new HashMap<>();
        initializeDefaultMovies();
    }

    public void initializeDefaultMovies() {
        movies = readMoviesFromFile();
        Movie movie1 = new Movie("Movie 1", "Action", "120", "image1.jpg", "trailer1.mp4", "Description of Movie 1");
        Movie movie2 = new Movie("Movie 2", "Drama", "130", "image2.jpg", "trailer2.mp4", "Description of Movie 2");
        Movie movie3 = new Movie("Movie 3", "Romantic", "150", "image3.jpg", "trailer3.mp4", "Description of Movie 3");

        movie1.addShowTime("CGV Sài Gòn", "Room 1", new ShowTime("2024-09-12 10:00", "2024-09-12 12:00", "Room 1", "CGV Sài Gòn"));
        movie1.addShowTime("CGV Đà Nẵng", "Room 4", new ShowTime("2024-09-12 16:00", "2024-12-09 18:00", "Room 4", "CGV Đà Nẵng"));
        movie1.addShowTime("CGV Hà Nội", "Room 8", new ShowTime("2024-09-13 19:00", "2024-09-13 21:00", "Room 8", "CGV Hà Nội"));
        movie2.addShowTime("CGV Sài Gòn", "Room 10", new ShowTime("2024-09-12 14:00", "2024-09-12 16:00", "Room 10", "CGV Sài Gòn"));
        movie2.addShowTime("CGV Đà Nẵng", "Room 5", new ShowTime("2024-09-13 16:30", "2024-09-13 18:30", "Room 5", "CGV Đà Nẵng"));
        movie2.addShowTime("CGV Hà Nội", "Room 2", new ShowTime("2024-09-15 17:50", "2024-09-15 19:50", "Room 2", "CGV Hà Nội"));
        movie3.addShowTime("CGV Sài Gòn", "Room 6", new ShowTime("2024-09-23 20:00", "2024-09-23 22:30", "Room 6", "CGV Sài Gòn"));
        movie3.addShowTime("CGV Đà Nẵng", "Room 7", new ShowTime("2024-09-27 16:45", "2024-09-27 19:00", "Room 7", "CGV Đà Nẵng"));
        movie3.addShowTime("CGV Hà Nội", "Room 9", new ShowTime("2024-09-26 21:00", "2024-09-26 22:30", "Room 9", "CGV Hà Nội"));

        movies.put("Movie 1", movie1);
        movies.put("Movie 2", movie2);
        movies.put("Movie 3", movie3);
        writeMoviesToFile(movies);

    }

    public void addMovie(String name, String genre, String duration, String image, String trailer, String
            description) {
        Movie movie = new Movie(name, genre, duration, image, trailer, description);
        movies.put(name, movie);
        writeMoviesToFile(movies);
    }

    public void updateMovie(String name, String newGenre, String newDuration, String newImage, String
            newTrailer, String newDescription) {
        Movie movie = movies.get(name);
        String genre = (newGenre != null && !newGenre.isEmpty()) ? newGenre : movie.getGenre();
        String duration = (newDuration != null && !newDuration.isEmpty()) ? newDuration : movie.getDuration();
        String image = (newImage != null && !newImage.isEmpty()) ? newImage : movie.getImage();
        String trailer = (newTrailer != null && !newTrailer.isEmpty()) ? newTrailer : movie.getTrailer();
        String description = (newDescription != null && !newDescription.isEmpty()) ? newDescription : movie.getDescription();

        Movie updatedMovie = new Movie(name, genre, duration, image, trailer, description);
        updatedMovie.setShowTimesByCinema(movie.getShowTimesByCinema());
        movies.put(name, updatedMovie);
        writeMoviesToFile(movies);
    }

    public void deleteMovie(String movieName) {
        if (isMovieExist(movieName)) {
            throw new IllegalArgumentException("Movie with name " + movieName + " does not exist.");
        }
        movies.remove(movieName);
        writeMoviesToFile(movies);
    }

    public boolean isMovieExist(String movieName) {
        return !movies.containsKey(movieName);
    }

    public List<String> getMovieName() {
        return new ArrayList<>(movies.keySet());
    }

    public Map<String, Movie> getAllMovies() {
        return movies;
    }

    private void writeMoviesToFile(Map<String, Movie> movies) {
        try (FileWriter writer = new FileWriter(MOVIES_FILE_PATH)) {
            writer.write("Movie name,Genre,Duration,Image,Trailer,Description\n");
            for (Movie movie : movies.values()) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s%n",
                        movie.getName(),
                        movie.getGenre(),
                        movie.getDuration(),
                        movie.getImage(),
                        movie.getTrailer(),
                        movie.getDescription()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Map<String, Movie> readMoviesFromFile() {
        Map<String, Movie> movies = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(MOVIES_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    String name = fields[0];
                    String genre = fields[1];
                    String duration = fields[2];
                    String image = fields[3];
                    String trailer = fields[4];
                    String description = fields[5];
                    Movie movie = new Movie(name, genre, duration, image, trailer, description);
                    movies.put(name, movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
