package OnlineReservationSystem;

import java.sql.*;
import java.util.Scanner;

public class OnlineReservationSystem {

    private static final String url = "jdbc:mysql://localhost:3306/onlinereservationsystem_db";
    private static final String username = "root";
    private static final String password = "pallu@15";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            Login login = new Login(connection);
            Reservation reservation = new Reservation(connection, scanner);

            System.out.print("Enter Username: ");
            String user = scanner.next();

            System.out.print("Enter Password: ");
            String pass = scanner.next();

            if (!login.validate(user, pass)) {
                System.out.println("Invalid Login!");
                return;
            }

            while (true) {
                System.out.println("\n--- ONLINE RESERVATION SYSTEM ---");
                System.out.println("1. Book Reservation");
                System.out.println("2. View Reservations");
                System.out.println("3. Cancel Reservation");
                System.out.println("4. Exit");
                System.out.print("Enter choice: ");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        reservation.bookReservation();
                        break;

                    case 2:
                        reservation.viewReservations();
                        break;

                    case 3:
                        System.out.print("Enter PNR: ");
                        int pnr = scanner.nextInt();
                        if (reservation.cancelReservation(pnr)) {
                            System.out.println("Reservation Cancelled!");
                        } else {
                            System.out.println("Invalid PNR!");
                        }
                        break;
                    case 4:
                        System.out.println("Thanks for choosing us! Your reservation experience matters to us. See you soon!");
                        return;
                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
