package case_study.controller;

import case_study.common.InputUtils;
import case_study.common.RegexPatterns;
import case_study.model.account_manage.Customer;
import case_study.model.account_manage.Role;
import case_study.model.account_manage.User;
import case_study.model.cinema_manage.Cinema;
import case_study.model.cinema_manage.Theater;
import case_study.model.movie_manage.*;
import case_study.service.*;
import case_study.view.UserView;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserController {
    private final UserService userService;
    private final UserView userView;
    private final MovieService movieService;
    private final ShowTimeService showTimeService;
    private final CinemaService cinemaService;
    private final TicketService ticketService;
    private User loggedInUser;

    private final Scanner scanner = new Scanner(System.in);

    public UserController(UserService userService, UserView userView, MovieService movieService, CinemaService cinemaService, ShowTimeService showTimeService, TicketService ticketService) {
        this.userService = userService;
        this.userView = userView;
        this.movieService = movieService;
        this.cinemaService = cinemaService;
        this.showTimeService = showTimeService;
        this.ticketService = ticketService;
    }

    public void start() {
        while (true) {
            if (loggedInUser == null) {
                userView.showInitialMenu();
                String choice = userView.getInput("");
                switch (choice) {
                    case "1" -> registerUser();
                    case "2" -> login();
                    case "3" -> {
                        return;
                    }
                    default -> userView.displayMessage("Invalid choice. Please try again.");
                }
            } else {
                showRoleBasedMenu();
            }
        }
    }

    private void showRoleBasedMenu() {
        if (loggedInUser.getRoles().contains(Role.ROLE_ADMIN)) {
            userView.showAdminMenu();
            handleAdminActions();
        } else if (loggedInUser.getRoles().contains(Role.ROLE_MANAGER)) {
            userView.showManagerMenu();
            handleManagerActions();
        } else if (loggedInUser.getRoles().contains(Role.ROLE_EMPLOYEE)) {
            userView.showEmployeeMenu();
            handleEmployeeActions();
        } else if (loggedInUser.getRoles().contains(Role.ROLE_CUSTOMER)) {
            userView.showCustomerMenu();
            handleCustomerActions();
        } else if (loggedInUser.getRoles().contains(Role.ROLE_USER)) {
            userView.showUserMenu();
            handleUserActions();
        }
    }

    private void handleAdminActions() {
        String choice = userView.getInput("");
        switch (choice) {
            case "1" -> userFunctional();
            case "2" -> employeeFunctional();
            case "3" -> customerFunctional();
            case "4" -> cinemaAndTheaterFunctional();
            case "5" -> movieAndShowTimeFunctional();
            case "6" -> ticketFunctional();
            case "7" -> logout();
            default -> userView.displayMessage("Invalid choice. Please try again.");
        }
    }

    private void handleManagerActions() {
        String choice = userView.getInput("");
        switch (choice) {
            case "1" -> userFunctional();
            case "2" -> employeeFunctional();
            case "3" -> customerFunctional();
            case "4" -> cinemaAndTheaterFunctional();
            case "5" -> movieAndShowTimeFunctional();
            case "6" -> ticketFunctional();
            case "7" -> logout();
            default -> userView.displayMessage("Invalid choice. Please try again.");
        }
    }

    private void handleEmployeeActions() {
        String choice = userView.getInput("");
        switch (choice) {
            case "1" -> showMovies();
            case "2" -> showShowTime();
            case "3" -> showTheaters();
            case "4" -> showCinemas();
            case "5" -> bookTicket();
            case "6" -> cancelUserTicket();
            case "7" -> showUserTickets();
            case "8" -> showTickets();
            case "9" -> showEmployees();
            case "10" -> showCustomers();
            case "11" -> registerUser();
            case "12" -> updateEmployeeDetails();
            case "13" -> updateCustomerDetails();
            case "14" -> deleteUser();
            case "15" -> addShowTime();
            case "16" -> updateShowTime();
            case "17" -> deleteShowTime();
            case "18" -> logout();
            default -> userView.displayMessage("Invalid choice. Please try again.");
        }
    }

    private void handleCustomerActions() {
        String choice = userView.getInput("");
        switch (choice) {
            case "1" -> showMovies();
            case "2" -> showShowTime();
            case "3" -> showTheaters();
            case "4" -> showCinemas();
            case "5" -> bookTicket();
            case "6" -> showUserTickets();
            case "7" -> cancelUserTicket();
            case "8" -> updateCustomerDetails();
            case "9" -> logout();
            default -> userView.displayMessage("Invalid choice. Please try again.");
        }
    }

    private void handleUserActions() {
        String choice = userView.getInput("");
        switch (choice) {
            case "1" -> showMovies();
            case "2" -> showShowTime();
            case "3" -> showTheaters();
            case "4" -> showCinemas();
            case "5" -> bookTicket();
            case "6" -> showUserTickets();
            case "7" -> cancelUserTicket();
            case "8" -> updateUserDetails();
            case "9" -> logout();
            default -> userView.displayMessage("Invalid choice. Please try again.");
        }
    }

    private void login() {
        String username = userView.getInput("Enter username: ");
        String password = userView.getInput("Enter password: ");
        loggedInUser = userService.loginUser(username, password);
        if (loggedInUser != null) {
            userView.displayMessage("Login successful. Roles: " + loggedInUser.getRoles());
            if (loggedInUser.getEmployeeDetails() != null) {
                userView.displayMessage("Employee details: " + loggedInUser.getEmployeeDetails());
            }
            if (loggedInUser.getCustomerDetails() != null) {
                userView.displayMessage("Customer details: " + loggedInUser.getCustomerDetails());
            }
        } else {
            userView.displayMessage("Invalid username or password.");
        }
    }

    private void logout() {
        loggedInUser = null;
        userView.displayMessage("Logout successful.");
    }

    /******************************************* User Handle *************************************************/

    public void userFunctional() {
        while (true) {
            userView.displayUserFunctional();
            String choice = userView.getInput("");
            switch (choice) {
                case "1" -> registerUser();
                case "2" -> showUsers();
                case "3" -> updateUserDetails();
                case "4" -> deleteUser();
                case "5" -> {
                    return;
                }
                default -> userView.displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    private void registerUser() {
        User newUser = getInputUserForRegister();
        userService.registerUser(newUser.getUsername(), newUser.getPassword(), newUser.getName(), newUser.getEmail(), newUser.getPhoneNumber());
        userView.displayMessage("User registration successful.");
    }

    private void updateUserDetails() {
        User updateUser = getInputUserForUpdate();
        if (updateUser != null) {
            userService.updateUserDetails(updateUser.getUsername(), updateUser.getPassword(), updateUser.getName(), updateUser.getPhoneNumber(), updateUser.getEmail());
            userView.displayMessage("User details updated successfully.");
        }
    }

    private void showUsers() {
        userView.displayUsers(userService.getAllUsers());
    }

    private void deleteUser() {
        String username = userView.getInput("Enter username: ");
        if (userService.deleteUser(username)) {
            userView.displayMessage("User deleted successfully.");
        } else {
            userView.displayMessage("User not found.");
        }
    }

    /******************************************* Employee Handle *************************************************/

    public void employeeFunctional() {
        while (true) {
            userView.displayEmployeeFunctional();
            String choice = userView.getInput("");
            switch (choice) {
                case "1" -> registerEmployee();
                case "2" -> showEmployees();
                case "3" -> updateEmployeeDetails();
                case "4" -> deleteUser();
                case "5" -> promoteEmployeeToManager();
                case "6" -> {
                    return;
                }
                default -> userView.displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    private void showEmployees() {
        userView.displayEmployees(userService.getAllEmployees());
    }

    private void registerEmployee() {
        User newUser = getInputUserForRegister();
        String identification = userView.getInput("Enter identification: ");
        userService.registerEmployee(newUser.getUsername(), newUser.getPassword(), identification, newUser.getPhoneNumber(), newUser.getName(), newUser.getEmail());
        userView.displayMessage("Employee registration successful.");
    }

    private void updateEmployeeDetails() {
        User updateEmployee = getInputUserForUpdate();
        if (updateEmployee != null) {
            String newIdentification = userView.getInput("Enter new identification: ");
            userService.updateEmployeeDetails(updateEmployee.getUsername(), updateEmployee.getPassword(), newIdentification, updateEmployee.getPhoneNumber(), updateEmployee.getName(), updateEmployee.getEmail());
            userView.displayMessage("Employee details updated successfully.");
        }
    }


    private void promoteEmployeeToManager() {
        String username = userView.getInput("Enter username: ");
        if (userService.promoteEmployeeToManager(username)) {
            userView.displayMessage("Employee " + username + " has been promoted to ROLE_MANAGER.");
        } else {
            userView.displayMessage("Promotion failed. User " + username + " is either not an employee or does not exist.");
        }
    }


    private void showCustomers() {
        userView.displayCustomers(userService.getAllCustomers());
    }


    /******************************************* Customer Handle *************************************************/

    public void customerFunctional() {
        while (true) {
            userView.displayCustomerFunctional();
            String choice = userView.getInput("");
            switch (choice) {
                case "1" -> registerCustomer();
                case "2" -> showCustomers();
                case "3" -> updateCustomerDetails();
                case "4" -> deleteUser();
                case "5" -> {
                    return;
                }
                default -> userView.displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    private void registerCustomer() {
        User newUser = getInputUserForRegister();
        userService.registerCustomer(newUser.getUsername(), newUser.getPassword(), newUser.getName(), newUser.getEmail(), newUser.getPhoneNumber());
        userView.displayMessage("Customer registration successful.");
    }

    private void updateCustomerDetails() {
        User updateCustomer = getInputUserForUpdate();
        if (updateCustomer != null) {
            userService.updateCustomerDetails(updateCustomer.getUsername(), updateCustomer.getPassword(), updateCustomer.getName(), updateCustomer.getPhoneNumber(), updateCustomer.getEmail());
            userView.displayMessage("Customer details updated successfully.");
        }
    }


    /******************************************* Movie Handle *************************************************/

    public void movieAndShowTimeFunctional() {
        while (true) {
            userView.displayMovieAndShowTimeManagement();
            String choice = userView.getInput("");
            switch (choice) {
                case "1" -> showMovies();
                case "2" -> addMovie();
                case "3" -> updateMovie();
                case "4" -> deleteMovie();
                case "5" -> showShowTime();
                case "6" -> addShowTime();
                case "7" -> updateShowTime();
                case "8" -> deleteShowTime();
                case "9" -> {
                    return;
                }
                default -> userView.displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    private void addMovie() {
        String title = userView.getInput("Enter movie title: ");
        if (movieService.isMovieExist(title)) {
            String genre = userView.getInput("Enter movie genre: ");
            String duration = userView.getInput("Enter movie duration in minutes: ");
            String image = userView.getInput("Enter movie image URL: ");
            String trailer = userView.getInput("Enter movie trailer URL: ");
            String description = userView.getInput("Enter movie description: ");
            movieService.addMovie(title, genre, duration, image, trailer, description);
            userView.displayMessage("Movie added successfully.");
        } else {
            userView.displayMessage("This movie already exist.");
        }

    }

    public void showMovies() {
        Map<String, Movie> movies = movieService.getAllMovies();
        showTimeService.readShowTimesFromFile(movies);
        if (movies.isEmpty()) {
            userView.displayMessage("No movies available.");
            return;
        }
        userView.displayMovies(movies);
    }

    public void updateMovie() {
        String name = userView.getInput("Enter the name of the movie to update: ");
        if (movieService.isMovieExist(name)) {
            userView.displayMessage("Movie with name " + name + " does not exist.");
            return;
        }
        String genre = userView.getInput("Enter the new genre of the movie: ");
        String duration = userView.getInput("Enter the new duration of the movie (in minutes): ");
        String image = userView.getInput("Enter the new image URL of the movie: ");
        String trailer = userView.getInput("Enter the new trailer URL of the movie: ");
        String description = userView.getInput("Enter the new description of the movie: ");

        try {
            movieService.updateMovie(name, genre, duration, image, trailer, description);
            userView.displayMessage("Movie updated successfully.");
        } catch (IllegalArgumentException e) {
            userView.displayMessage(e.getMessage());
        }
    }

    public void deleteMovie() {
        String movieName = userView.getInput("Enter the name of the movie to delete: ");
        if (movieService.isMovieExist(movieName)) {
            userView.displayMessage("Movie with name " + movieName + " does not exist.");
            return;
        }

        try {
            movieService.deleteMovie(movieName);
            userView.displayMessage("Movie deleted successfully.");
        } catch (IllegalArgumentException e) {
            userView.displayMessage(e.getMessage());
        }
    }

    /******************************************* Theater Handle *************************************************/

    public void addTheater() {
        String cinemaName = userView.getInput("Enter the name of the cinema: ");
        Cinema cinema = cinemaService.getCinema(cinemaName);
        if (cinema == null) {
            userView.displayMessage("Cinema not found.");
            return;
        }

        String theaterName = userView.getInput("Enter the name of the new theater: ");
        int seatCount = Integer.parseInt(userView.getInput("Enter the number of seats: "));

        if (cinema.getRoom(theaterName) != null) {
            userView.displayMessage("Theater already exists in this cinema.");
            return;
        }

        Theater newTheater = new Theater(theaterName, seatCount);
        cinema.addRoom(newTheater);
        cinemaService.writeCinemaToFile(cinemaService.getAllCinemas());
        userView.displayMessage("Theater added successfully.");
    }

    public void updateTheater() {
        String cinemaName = userView.getInput("Enter the name of the cinema: ");
        Cinema cinema = cinemaService.getCinema(cinemaName);
        if (cinema == null) {
            userView.displayMessage("Cinema not found.");
            return;
        }

        String oldTheaterName = userView.getInput("Enter the current name of the theater: ");
        Theater oldTheater = cinema.getRoom(oldTheaterName);
        if (oldTheater == null) {
            userView.displayMessage("Theater not found.");
            return;
        }

        String newTheaterName = userView.getInput("Enter the new name of the theater: ");
        int seatCount = Integer.parseInt(userView.getInput("Enter the number of seats: "));

        Theater updatedTheater = new Theater(newTheaterName, seatCount);
        cinema.updateTheater(oldTheaterName, updatedTheater);
        cinemaService.writeCinemaToFile(cinemaService.getAllCinemas());
        userView.displayMessage("Theater updated successfully.");
    }

    public void showTheaters() {
        userView.displayTheaters(cinemaService.getAllCinemas());
    }

    public void deleteTheater() {
        String cinemaName = userView.getInput("Enter the name of the cinema: ");
        Cinema cinema = cinemaService.getCinema(cinemaName);
        if (cinema == null) {
            userView.displayMessage("Cinema not found.");
            return;
        }

        String theaterName = userView.getInput("Enter the name of the theater to delete: ");
        if (cinema.getRoom(theaterName) == null) {
            userView.displayMessage("Theater not found in this cinema.");
            return;
        }

        cinema.getRooms().remove(theaterName);
        cinemaService.writeCinemaToFile(cinemaService.getAllCinemas());
        userView.displayMessage("Theater deleted successfully.");
    }


    /******************************************* Cinema Handle *************************************************/

    public void cinemaAndTheaterFunctional() {
        while (true) {
            userView.displayCinemaAndTheaterManagement();
            String choice = userView.getInput("");
            switch (choice) {
                case "1" -> showCinemas();
                case "2" -> addCinema();
                case "3" -> updateCinema();
                case "4" -> deleteCinema();
                case "5" -> showTheaters();
                case "6" -> addTheater();
                case "7" -> updateTheater();
                case "8" -> deleteTheater();
                case "9" -> {
                    return;
                }
                default -> userView.displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    public void addCinema() {
        String name = userView.getInput("Enter the name of the new cinema: ");
        if (cinemaService.getCinema(name) != null) {
            userView.displayMessage("Cinema already exists.");
            return;
        }

        Cinema newCinema = new Cinema(name);
        cinemaService.addCinema(newCinema);
        userView.displayMessage("Cinema added successfully.");
    }

    public void showCinemas() {
        userView.displayCinemas(cinemaService.getAllCinemas());
    }

    public void updateCinema() {
        String oldName = userView.getInput("Enter the current name of the cinema: ");
        if (cinemaService.getCinema(oldName) == null) {
            userView.displayMessage("Cinema not found.");
            return;
        }

        String newName = userView.getInput("Enter the new name of the cinema: ");
        if (cinemaService.updateCinema(oldName, newName)) {
            userView.displayMessage("Cinema updated successfully.");
        } else {
            userView.displayMessage("Failed to update cinema.");
        }
    }

    public void deleteCinema() {
        String name = userView.getInput("Enter the name of the cinema to delete: ");
        if (cinemaService.deleteCinema(name)) {
            userView.displayMessage("Cinema deleted successfully.");
        } else {
            userView.displayMessage("Failed to delete cinema.");
        }
    }

    /******************************************* ShowTime Handle *************************************************/
    public void addShowTime() {
        String movieName = userView.getInput("Enter movie name: ");
        System.out.println(movieService.getAllMovies());
        if (!movieService.getAllMovies().containsKey(movieName)) {
            userView.displayMessage("Movie not found.");
            return;
        }
        String cinemaName = userView.getInput("Enter cinema name: ");
        String theaterName = userView.getInput("Enter theater name: ");
        String startTime = userView.getInput("Enter new start time (yyyy-mm-dd HH:MM): ");
        String endTime = userView.getInput("Enter new end time (yyyy-mm-dd HH:MM): ");
        ShowTime showTime = new ShowTime(startTime, endTime, theaterName, cinemaName);
        showTimeService.addShowTime(movieName, cinemaName, theaterName, showTime, movieService.getAllMovies(), cinemaService.getAllCinemas());
    }

    public void updateShowTime() {
        String movieName = userView.getInput("Enter movie name: ");
        if (!movieService.getAllMovies().containsKey(movieName)) {
            userView.displayMessage("Movie not found.");
            return;
        }
        String cinemaName = userView.getInput("Enter cinema name: ");
        String theaterName = userView.getInput("Enter theater name: ");
        String startTime = userView.getInput("Enter new start time (yyyy-mm-dd HH:MM): ");
        String endTime = userView.getInput("Enter new end time (yyyy-mm-dd HH:MM): ");

        ShowTime showTime = new ShowTime(startTime, endTime, theaterName, cinemaName);
        showTimeService.updateShowTime(movieName, cinemaName, theaterName, showTime, movieService.getAllMovies());
    }

    public void showShowTime() {
        String movieName = userView.getInput("Enter movie name: ");
        System.out.println(movieService.getAllMovies().containsKey(movieName));
        if (!movieService.getAllMovies().containsKey(movieName)) {
            userView.displayMessage("Movie not found.");
            return;
        }
        showTimeService.showShowTime(movieService.getAllMovies(), movieName);
    }

    public void deleteShowTime() {
        String movieName = userView.getInput("Enter movie name: ");
        String cinemaName = userView.getInput("Enter cinema name: ");
        String theaterName = userView.getInput("Enter theater name: ");
        String showTime = userView.getInput("Enter showTime: ");
        showTimeService.deleteShowTime(movieName, cinemaName, theaterName, showTime, movieService.getAllMovies(), cinemaService.getAllCinemas());
    }

    /******************************************* Ticket Handle *************************************************/

    public void ticketFunctional() {
        while (true) {
            userView.displayTicketManagement();
            String choice = userView.getInput("");
            switch (choice) {
                case "1" -> bookTicket();
                case "2" -> showTickets();
                case "3" -> cancelUserTicket();
                case "4" -> {
                    return;
                }
                default -> userView.displayMessage("Invalid choice. Please try again.");
            }
        }
    }

    public void bookTicket() {
        Map<String, User> users = userService.getAllUsers();
        Map<String, Customer> customers = userService.getAllCustomers();
        String username = loggedInUser.getUsername();

        userView.displayMessage("Choose movie:");
        String movieName = selectMovie();
        if (movieName == null) {
            userView.displayMessage("Invalid movie!");
            return;
        }

        userView.displayMessage("Choose cinema:");
        String cinemaName = selectCinema();
        if (cinemaName == null) {
            userView.displayMessage("Invalid cinema!");
            return;
        }

        userView.displayMessage("Choose room:");
        String theaterName = selectTheater(cinemaName, movieName);
        if (theaterName == null) {
            userView.displayMessage("Invalid room!");
            return;
        }

        userView.displayMessage("Choose showtime:");
        String showTime = selectShowTime(cinemaName, theaterName, movieName);
        if (showTime == null) {
            userView.displayMessage("Invalid showtime!");
            return;
        }

        int maxSeats = 30;
        int seatNumber = selectSeatName(cinemaName, theaterName, showTime, maxSeats);
        if (seatNumber == -1) {
            userView.displayMessage("There are no more empty seats. Please choose to another time slot");
            return;
        }
        userView.displayMessage("Select Ticket type:");
        userView.displayMessage("1. NORMAL (10$)");
        userView.displayMessage("2. VIP (16$)");
        int ticketTypeChoice = scanner.nextInt();
        scanner.nextLine();

        TicketType ticketType;
        if (ticketTypeChoice == 1) {
            ticketType = TicketType.REGULAR;
        } else if (ticketTypeChoice == 2) {
            ticketType = TicketType.VIP;
        } else {
            userView.displayMessage("Invalid ticket type!");
            return;
        }

        Ticket ticket = new Ticket(username, movieName, theaterName, cinemaName, showTime, ticketType.getPrice(), ticketType.name(), seatNumber, "PENDING");
        new PaymentController().processPayment(ticket);

        if (ticket.getStatus().equals("PAID")) {
            ticketService.bookTicket(users, customers, username, ticket);
            userView.displayMessage("Ticket booking successful!");
        } else {
            userView.displayMessage("Ticket booking failed. Payment was not successful.");
        }
    }

    private String selectMovie() {
        List<String> movieNames = movieService.getMovieName();
        for (int i = 0; i < movieNames.size(); i++) {
            userView.displayMessage((i + 1) + ". " + movieNames.get(i));
        }
        int movieChoice = scanner.nextInt();
        scanner.nextLine();
        if (movieChoice < 1 || movieChoice > movieNames.size()) {
            return null;
        }
        return movieNames.get(movieChoice - 1);
    }

    private String selectCinema() {
        List<String> cinemaNames = cinemaService.getCinemaNames();
        for (int i = 0; i < cinemaNames.size(); i++) {
            userView.displayMessage((i + 1) + ". " + cinemaNames.get(i));
        }
        int cinemaChoice = scanner.nextInt();
        scanner.nextLine();
        if (cinemaChoice < 1 || cinemaChoice > cinemaNames.size()) {
            return null;
        }
        return cinemaNames.get(cinemaChoice - 1);
    }

    private String selectTheater(String cinemaName, String movieName) {
        List<String> theaterNames = ticketService.getTheaterNames(cinemaName, movieName);
        for (int i = 0; i < theaterNames.size(); i++) {
            userView.displayMessage((i + 1) + ". " + theaterNames.get(i));
        }
        int theaterChoice = scanner.nextInt();
        scanner.nextLine();
        if (theaterChoice < 1 || theaterChoice > theaterNames.size()) {
            return null;
        }
        return theaterNames.get(theaterChoice - 1);
    }

    private int selectSeatName(String cinemaName, String theaterName, String showTime, int maxSeats) {
        List<Integer> availableSeats = ticketService.getAvailableSeats(cinemaName, theaterName, showTime, maxSeats);
        if (availableSeats.isEmpty()) {
            return -1;
        }
        userView.displayMessage("The seats are empty: " + availableSeats);
        int seatNumber;
        while (true) {
            userView.displayMessage("Select seat number:");
            seatNumber = scanner.nextInt();
            scanner.nextLine();
            if (availableSeats.contains(seatNumber)) {
                break;
            } else {
                userView.displayMessage("Seat is invalid or already reserved. Please choose another seat.");
            }
        }
        return seatNumber;
    }

    private String selectShowTime(String cinemaName, String theaterName, String movieName) {
        List<ShowTime> showTimes = showTimeService.getShowTimes(cinemaName, theaterName, movieName, movieService.getAllMovies());
        if (showTimes.isEmpty()) {
            return null;
        }
        for (int i = 0; i < showTimes.size(); i++) {
            userView.displayMessage((i + 1) + ". " + showTimes.get(i).getStartTime());
        }
        int showTimeChoice = scanner.nextInt();
        scanner.nextLine();
        if (showTimeChoice < 1 || showTimeChoice > showTimes.size()) {
            return null;
        }
        return showTimes.get(showTimeChoice - 1).getStartTime();
    }

    public void showUserTickets() {
        String username = loggedInUser.getUsername();
        List<Ticket> userTickets = ticketService.getTicketsByUsername(username);

        if (userTickets.isEmpty()) {
            userView.displayMessage("You haven't booked any tickets yet.");
        } else {
            for (Ticket ticket : userTickets) {
                userView.displayMessage(String.valueOf(ticket));
            }
        }
    }

    public void cancelUserTicket() {
        String username = loggedInUser.getUsername();
        List<Ticket> userTickets = ticketService.getTicketsByUsername(username);

        if (userTickets.isEmpty()) {
            userView.displayMessage("You haven't booked any tickets yet.");
            return;
        }

        showUserTickets();
        userView.displayMessage("Enter ticket code to cancel:");
        int ticketId = scanner.nextInt();
        scanner.nextLine();

        ticketService.cancelTicket(username, ticketId);
        userView.displayMessage("The ticket has been cancelled.");
    }

    public void showTickets() {
        List<Ticket> tickets = ticketService.getTickets();
        if (tickets.isEmpty()) {
            userView.displayMessage("No tickets were booked.");
        } else {
            for (Ticket ticket : tickets) {
                userView.displayMessage(String.valueOf(ticket));
            }
        }
    }


    /******************************************* Get Input from User *************************************************/
    public User getInputUserForRegister() {
        String username = InputUtils.getValidatedInput(
                "Enter username: ",
                usernameInput -> !userService.usernameExists(usernameInput) && RegexPatterns.validateUsername(usernameInput)
        );
        String password = InputUtils.getValidatedInput(
                "Enter password (At least 8 characters, including at least one uppercase letter, one lowercase letter, one number, and one special character): ",
                RegexPatterns::validatePassword
        );
        String name = InputUtils.getValidatedInput(
                "Enter your name: ",
                RegexPatterns::validateName
        );
        String email = InputUtils.getValidatedInput(
                "Enter your email: ",
                emailInput -> !userService.emailExists(username, emailInput) && RegexPatterns.validateEmail(emailInput)
        );
        String phoneNumber = InputUtils.getValidatedInput(
                "Enter your phone number: ",
                phoneNumberInput -> !userService.phoneNumberExists(username ,phoneNumberInput) && RegexPatterns.validatePhoneNumber(phoneNumberInput)
        );
        return new User(username, password, name, email, phoneNumber);
    }

    public User getInputUserForUpdate() {
        String username;
        String newFullName;
        String newPassword;
        String newEmail;
        String newPhoneNumber;
        while (true) {
            username = userView.getInput("Enter username: ");
            if (!userService.usernameExists(username)) {
                userView.displayMessage("The user name you just entered does not exist!");
            } else {
                break;
            }
        }

        String oldPassword = userView.getInput("Enter old password: ");
        if (userService.isNotOldPassword(username, oldPassword)) {
            userView.displayMessage("Incorrect password! Please try again.");
            return null;
        }


        newPassword = InputUtils.getValidatedInput(
                "Enter new password (At least 8 characters, including at least one uppercase letter, one lowercase letter, one number, and one special character): ",
                RegexPatterns::validatePassword
        );
        newFullName = InputUtils.getValidatedInput(
                "Enter new full name: ",
                RegexPatterns::validateName
        );
        while (true) {
            newEmail = InputUtils.getValidatedInput(
                    "Enter new email: ",
                    RegexPatterns::validateEmail
            );
            if (userService.emailExists(username, newEmail)) {
                userView.displayMessage("This email already exists. Please check the email you just entered.");
            } else {
                break;
            }
        }
        while (true) {
            newPhoneNumber = InputUtils.getValidatedInput(
                    "Enter new phone number: ",
                    RegexPatterns::validatePhoneNumber
            );
            if (userService.phoneNumberExists(username ,newPhoneNumber)) {
                userView.displayMessage("This phone number already exists. Please check the phone number you just entered.");
            } else {
                break;
            }
        }
        return new User(username, newPassword, newFullName, newEmail, newPhoneNumber);
    }
}


