package case_study.controller;

import case_study.model.movie_manage.Ticket;
import case_study.service.TicketService;

import java.util.List;
import java.util.Scanner;

public class PaymentController {
    private final Scanner scanner;

    TicketService ticketService;

    public PaymentController() {
        ticketService = new TicketService();
        scanner = new Scanner(System.in);
    }


    public void processPayment(Ticket ticket) {
        System.out.println("Ticket Details:");
        System.out.println("Movie: " + ticket.getMovieName());
        System.out.println("Cinema: " + ticket.getCinemaName());
        System.out.println("Room: " + ticket.getTheaterName());
        System.out.println("Start Time: " + ticket.getShowTime());
        System.out.println("Seats: " + ticket.getSeatNumber());
        System.out.println("Ticket Type: " + ticket.getTicketType());
        System.out.println("Total Price: $" + (ticket.getPrice()));

        System.out.print("Enter payment method (credit/debit/cash): ");
        String paymentMethod = scanner.nextLine();

        if (validatePayment(paymentMethod)) {
            ticket.setStatus("PAID");
            System.out.println("Payment successful. Your ticket has been booked.");
        } else {
            System.out.println("Payment failed. Please try again.");
        }
    }

    private boolean validatePayment(String paymentMethod) {
        return paymentMethod.equalsIgnoreCase("credit") ||
                paymentMethod.equalsIgnoreCase("debit") ||
                paymentMethod.equalsIgnoreCase("cash");
    }
}
