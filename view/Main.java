package case_study.view;

import case_study.controller.UserController;
import case_study.model.account_manage.Role;
import case_study.service.*;


public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        CinemaService cinemaService = new CinemaService();
        MovieService movieService = new MovieService();
        ShowTimeService showTimeService = new ShowTimeService();
        TicketService ticketService = new TicketService();
        UserView userView = new UserView();
        UserController userController = new UserController(userService, userView, movieService, cinemaService, showTimeService, ticketService);

        // Admin account
        userService.registerAdmin("admin", "1", "admin", "CGVAdmin.com", "0123456789");
        userService.addRoleToUser("admin", Role.ROLE_ADMIN);

        userController.start();
    }
}


