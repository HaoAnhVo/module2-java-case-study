package case_study.view;

import case_study.model.account_manage.Customer;
import case_study.model.account_manage.Employee;
import case_study.model.account_manage.User;
import case_study.model.cinema_manage.Cinema;
import case_study.model.movie_manage.Movie;
import case_study.model.movie_manage.ShowTime;
import case_study.model.cinema_manage.Theater;


import java.util.Map;
import java.util.Scanner;

public class UserView {
    private final Scanner scanner = new Scanner(System.in);

    public void showInitialMenu() {
        System.out.println("1. Register User");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.print("Choose an option: ");
    }

    public void showAdminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. User Management");
        System.out.println("2. Employees Management");
        System.out.println("3. Customers Management");
        System.out.println("4. Cinemas & Theaters Management");
        System.out.println("5. Movies & ShowTimes Management");
        System.out.println("6. Tickets Management");
        System.out.println("7. Logout");
        System.out.print("Choose an option: ");
    }

    public void showManagerMenu() {
        System.out.println("Manager Menu:");
        System.out.println("1. User Management");
        System.out.println("2. Employees Management");
        System.out.println("3. Customers Management");
        System.out.println("4. Cinemas & Theaters Management");
        System.out.println("5. Movies & ShowTimes Management");
        System.out.println("6. Tickets Management");
        System.out.println("25. Logout");
        System.out.print("Choose an option: ");
    }

    public void showEmployeeMenu() {
        System.out.println("Employee Menu:");
        System.out.println("1. Show Movies");
        System.out.println("2. Show ShowTimes");
        System.out.println("3. Show Theaters");
        System.out.println("4. Show Cinemas");
        System.out.println("5. Book Ticket");
        System.out.println("6. Cancel User Ticket");
        System.out.println("7. Show Your Ticket");
        System.out.println("8. Show All Tickets");
        System.out.println("9. Show Employees");
        System.out.println("10. Show Customers");
        System.out.println("11. Register User Account");
        System.out.println("12. Update Employee Details");
        System.out.println("13. Update Customer Details");
        System.out.println("14. Delete User Account");
        System.out.println("15. Add ShowTime of Movie");
        System.out.println("16. Update ShowTime of Movie");
        System.out.println("17. Delete ShowTime of Movie");
        System.out.println("18. Logout");
        System.out.print("Choose an option: ");
    }

    public void showCustomerMenu() {
        System.out.println("Customer Menu:");
        System.out.println("1. Show Movies");
        System.out.println("2. Show ShowTimes");
        System.out.println("3. Show Theaters");
        System.out.println("4. Show Cinemas");
        System.out.println("5. Book Ticket");
        System.out.println("6. Show Your Tickets");
        System.out.println("7. Cancel Ticket");
        System.out.println("8. Update Customer Details");
        System.out.println("9. Logout");
        System.out.print("Choose an option: ");
    }

    public void showUserMenu() {
        System.out.println("User Menu:");
        System.out.println("1. Show Movies");
        System.out.println("2. Show ShowTimes");
        System.out.println("3. Show Theaters");
        System.out.println("4. Show Cinemas");
        System.out.println("5. Book Ticket");
        System.out.println("6. Show Your Tickets");
        System.out.println("7. Cancel Ticket");
        System.out.println("8. Update User Details");
        System.out.println("9. Logout");
        System.out.print("Choose an option: ");
    }

    // Display Functional for Admin View
    public void displayUserFunctional() {
        System.out.println("Users Management:");
        System.out.println("1. Register User Account");
        System.out.println("2. Display Users");
        System.out.println("3. Update User Details");
        System.out.println("4. Delete User");
        System.out.println("5. Go Back");
        System.out.print("Choose an option: ");
    }

    public void displayEmployeeFunctional() {
        System.out.println("Employees Management:");
        System.out.println("1. Register Employee Account");
        System.out.println("2. Display Employees");
        System.out.println("3. Update Employee Details");
        System.out.println("4. Delete Employee Account");
        System.out.println("5. Promote Employee to Manager");
        System.out.println("6. Go Back");
        System.out.print("Choose an option: ");
    }

    public void displayCustomerFunctional() {
        System.out.println("Customers Management:");
        System.out.println("1. Register Customers Account");
        System.out.println("2. Show Customers");
        System.out.println("3. Update Customer Details");
        System.out.println("4. Delete Customer Account");
        System.out.println("5. Go Back");
        System.out.print("Choose an option: ");
    }

    public void displayMovieAndShowTimeManagement() {
        System.out.println("Movies & ShowTimes Management:");
        System.out.println("1. Show Movies");
        System.out.println("2. Add Movies");
        System.out.println("3. Update Movie");
        System.out.println("4. Delete Movie");
        System.out.println("5. Show ShowTimes");
        System.out.println("6. Add ShowTime");
        System.out.println("7. Update ShowTime");
        System.out.println("8. Delete ShowTime");
        System.out.println("9. Go Back");
        System.out.print("Choose an option: ");
    }

    public void displayCinemaAndTheaterManagement() {
        System.out.println("Cinemas & Theaters Management:");
        System.out.println("1. Show Cinemas");
        System.out.println("2. Add Cinema");
        System.out.println("3. Update Cinema");
        System.out.println("4. Delete Cinema");
        System.out.println("5. Show Theaters");
        System.out.println("6. Add Theater");
        System.out.println("7. Update Theater");
        System.out.println("8. Delete Theater");
        System.out.println("9. Go Back");
        System.out.print("Choose an option: ");
    }

    public void displayTicketManagement() {
        System.out.println("Tickets Management:");
        System.out.println("1. Book Tickets");
        System.out.println("2. Show Tickets");
        System.out.println("3. Cancel Ticket");
        System.out.println("4. Go Back");
        System.out.print("Choose an option: ");
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayUsers(Map<String, User> users) {
        if (users.isEmpty()) {
            System.out.println("No users found");
            return;
        }
        System.out.println("Users List:");
        for (User user : users.values()) {
            System.out.println(user);

        }
    }

    public void displayEmployees(Map<String, Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees found");
            return;
        }
        System.out.println("Employee List:");
        for (Employee employee : employees.values()) {
            System.out.println(employee);

        }
    }

    public void displayCustomers(Map<String, Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("No customers found");
            return;
        }
        System.out.println("Customer List:");
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }

    public void displayMovies(Map<String, Movie> movies) {
        for (Movie movie : movies.values()) {
            System.out.println("Name: " + movie.getName());
            System.out.println("Genre: " + movie.getGenre());
            System.out.println("Duration: " + movie.getDuration() + " minutes");
            System.out.println("Image: " + movie.getImage());
            System.out.println("Trailer: " + movie.getTrailer());
            System.out.println("Description: " + movie.getDescription());
            System.out.println();
            System.out.println("ShowTimes:");
            Map<String, Map<String, ShowTime>> showTimesByCinema = movie.getShowTimesByCinema();
            for (Map.Entry<String, Map<String, ShowTime>> cinemaEntry : showTimesByCinema.entrySet()) {
                String cinemaName = cinemaEntry.getKey();
                System.out.println("Cinema: " + cinemaName);
                Map<String, ShowTime> showTimeByTheater = cinemaEntry.getValue();
                for (Map.Entry<String, ShowTime> theaterEntry : showTimeByTheater.entrySet()) {
                    String theaterName = theaterEntry.getKey();
                    ShowTime showTime = theaterEntry.getValue();
                    System.out.println("Room: " + theaterName);
                    System.out.println("Start Time: " + showTime.getStartTime());
                    System.out.println("End Time: " + showTime.getEndTime());
                    System.out.println();
                }
            }
            System.out.println("---------------------------");
        }
    }

    public void displayTheaters(Map<String, Cinema> cinemas) {
        for (Cinema cinema : cinemas.values()) {
            System.out.println("Cinema: " + cinema.getName());
            if (cinema.getRooms().isEmpty()) {
                System.out.println("  No rooms available.");
            } else {
                for (Theater theater : cinema.getRooms().values()) {
                    System.out.println("  Room: " + theater.getName() + ", Seats: " + theater.getSeatCount());
                }
            }
        }
    }

    public void displayCinemas(Map<String, Cinema> cinemas) {
        for (Cinema cinema : cinemas.values()) {
            if (cinema.getName().isEmpty()) {
                System.out.println("  No cinemas available.");
            } else {
                System.out.println("Cinema: " + cinema.getName());
            }
        }
    }
}
