package case_study.service;

import case_study.model.account_manage.Customer;
import case_study.model.account_manage.Role;
import case_study.model.account_manage.User;
import case_study.model.cinema_manage.Cinema;
import case_study.model.movie_manage.Movie;
import case_study.model.movie_manage.Ticket;
import case_study.model.movie_manage.TicketType;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TicketService {

    private static final String TICKETS_FILE_PATH = "src/case_study/repository/tickets.csv";
    private final List<Ticket> tickets;
    private final Map<String, Cinema> cinemas;
    public Map<String, Movie> movies;
    UserService userService = new UserService();
    CinemaService cinemaService = new CinemaService();
    MovieService movieService = new MovieService();

    public TicketService() {
        tickets = readTicketsFromFile();
        cinemas = cinemaService.getAllCinemas();
        movies = movieService.getAllMovies();
    }


    public void bookTicket(Map<String, User> users, Map<String, Customer> customers, String username, String cinemaName, String theaterName, String movieName, String showTime, int seatNumber, TicketType ticketType) {

        User user = users.get(username);
        double price = ticketType.getPrice();
        Ticket ticket = new Ticket(username, movieName, theaterName, cinemaName, showTime, price, ticketType.name(), seatNumber, "Đã đặt");
        tickets.add(ticket);
        writeTicketsToFile(tickets);

        if (user != null && !user.getRoles().contains(Role.ROLE_CUSTOMER)) {
            System.out.println("Your account has been granted ROLE_CUSTOMER");
            Customer customer = new Customer(user.getUsername(), user.getPassword(), user.getName(), user.getEmail(), user.getPhoneNumber());
            customers.put(username, customer);
            System.out.println(customers);
            user.addRole(Role.ROLE_CUSTOMER);
            userService.addRoleToUser(username, Role.ROLE_CUSTOMER);
        }
    }

    public List<Ticket> getTicketsByUsername(String username) {
        return tickets.stream()
                .filter(ticket -> ticket.getUserName().equals(username))
                .collect(Collectors.toList());
    }

    public void cancelTicket(String username, int ticketId) {
        tickets.removeIf(ticket -> ticket.getUserName().equals(username) && ticket.getTicketId() == ticketId);
        writeTicketsToFile(tickets);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<String> getTheaterNames(String cinemaName, String movieName) {
        if (cinemas.containsKey(cinemaName)) {
            List<String> theaterNames = new ArrayList<>();
            for (String theaterName : cinemas.get(cinemaName).getRooms().keySet()) {
                if (movies.get(movieName).getShowTimesByCinema().get(cinemaName).containsKey(theaterName)) {
                    theaterNames.add(theaterName);
                }
            }
            return theaterNames;
        }
        return new ArrayList<>();
    }

    public List<Integer> getAvailableSeats(String cinemaName, String theaterName, String showTime, int maxSeats) {
        List<Integer> availableSeats = new ArrayList<>();
        for (int seat = 1; seat <= maxSeats; seat++) {
            if (isSeatAvailable(cinemaName, theaterName, showTime, seat)) {
                availableSeats.add(seat);
            }
        }
        return availableSeats;
    }

    public boolean isSeatAvailable(String cinemaName, String theaterName, String showTime, int seatNumber) {
        for (Ticket ticket : tickets) {
            if (ticket.getCinemaName().equals(cinemaName) &&
                    ticket.getTheaterName().equals(theaterName) &&
                    ticket.getShowTime().equals(showTime) &&
                    ticket.getSeatNumber() == (seatNumber)) {
                return false;
            }
        }
        return true;
    }

    private void writeTicketsToFile(List<Ticket> tickets) {
        try (FileWriter writer = new FileWriter(TICKETS_FILE_PATH)) {
            writer.append("TicketId,UserName,MovieName,TheaterName,CinemaName,ShowTime,Price,TicketType,SeatNumber,Status\n");
            for (Ticket ticket : tickets) {
                writer.append(String.valueOf(ticket.getTicketId()))
                        .append(',')
                        .append(ticket.getUserName())
                        .append(',')
                        .append(ticket.getMovieName())
                        .append(',')
                        .append(ticket.getTheaterName())
                        .append(',')
                        .append(ticket.getCinemaName())
                        .append(',')
                        .append(ticket.getShowTime())
                        .append(',')
                        .append(String.valueOf(ticket.getPrice()))
                        .append(',')
                        .append(ticket.getTicketType())
                        .append(',')
                        .append(String.valueOf(ticket.getSeatNumber()))
                        .append(',')
                        .append(ticket.getStatus())
                        .append('\n');
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    private List<Ticket> readTicketsFromFile() {
        List<Ticket> tickets = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKETS_FILE_PATH))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                if (fields.length >= 10) {
                    int ticketId = Integer.parseInt(fields[0].trim());
                    double price = Double.parseDouble(fields[6].trim());
                    int seatNumber = Integer.parseInt(fields[8].trim());
                    String userName = fields[1].trim();
                    String movieName = fields[2].trim();
                    String theaterName = fields[3].trim();
                    String cinemaName = fields[4].trim();
                    String showTime = fields[5].trim();
                    String ticketType = fields[7].trim();
                    String status = fields[9].trim();

                    Ticket ticket = new Ticket(userName, movieName, theaterName, cinemaName, showTime, price, ticketType, seatNumber, status);
                    tickets.add(ticket);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }
}
