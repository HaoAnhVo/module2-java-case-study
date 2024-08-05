package case_study.service;

import case_study.model.cinema_manage.Cinema;
import case_study.model.movie_manage.Movie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CinemaService {
    private static final String CINEMAS_FILE_PATH = "src/case_study/repository/cinemas.csv";

    private final Map<String, Cinema> cinemas = new HashMap<>();

    public CinemaService() {
        initializeDefaultCinemas();
    }

    private void initializeDefaultCinemas() {
        readCinemasFromFile();
        if (cinemas.isEmpty()) {
            cinemas.put("CGV Sài Gòn", new Cinema("CGV Sài Gòn"));
            cinemas.put("CGV Đà Nẵng", new Cinema("CGV Đà Nẵng"));
            cinemas.put("CGV Hà Nội", new Cinema("CGV Hà Nội"));
            writeCinemaToFile(cinemas);
        }
    }

    public void addCinema(Cinema cinema) {
        cinemas.put(cinema.getName(), cinema);
        writeCinemaToFile(cinemas);
    }

    public Cinema getCinema(String name) {
        return cinemas.get(name);
    }

    public Map<String, Cinema> getAllCinemas() {
        return cinemas;
    }

    public boolean updateCinema(String oldName, String newName) {
        Cinema cinema = cinemas.remove(oldName);
        if (cinema != null) {
            cinema.setName(newName);
            cinemas.put(newName, cinema);
            writeCinemaToFile(cinemas);
            return true;
        }
        return false;
    }

    public boolean deleteCinema(String name) {
        writeCinemaToFile(cinemas);
        return cinemas.remove(name) != null;
    }

    public List<String> getCinemaNames() {
        return new ArrayList<>(cinemas.keySet());
    }

    public void writeCinemaToFile(Map<String, Cinema> cinemas) {
        try (FileWriter writer = new FileWriter(CINEMAS_FILE_PATH)) {
            writer.write("Cinema name,Rooms\n");
            for (Cinema cinema : cinemas.values()) {
                writer.write(String.format("%s%s%n",
                        cinema.getName(),
                        cinema.getRooms().values()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Cinema> readCinemasFromFile() {
        Map<String, Cinema> cinemas = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CINEMAS_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length >= 6) {
                    String name = fields[0];
                    Cinema cinema = new Cinema(name);
                    cinemas.put(name, cinema);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cinemas;
    }

}
