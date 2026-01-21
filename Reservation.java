package OnlineReservationSystem;

import  java.util.Scanner;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.ResultSet;
//import java.sql.PreparedStatement;
import java.sql.*;
public class Reservation {
//    package OnlineReservationSystem;



        private Connection connection;
        private Scanner scanner;

        public Reservation(Connection connection, Scanner scanner) {
            this.connection = connection;
            this.scanner = scanner;
        }
    public void bookReservation() {

        System.out.print("Enter Passenger Name: ");
        String name = scanner.next();

        System.out.print("Enter Train No: ");
        int trainNo = scanner.nextInt();

        System.out.print("Enter Class Type: ");
        String classType = scanner.next();

        System.out.print("Enter Journey Date (YYYY-MM-DD): ");
        String date = scanner.next();

        System.out.print("From Station: ");
        String from = scanner.next();

        System.out.print("To Station: ");
        String to = scanner.next();

        String query = "INSERT INTO reservations " +
                "(passenger_name, train_no, class_type, journey_date, from_station, to_station) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(
                    query,
                    PreparedStatement.RETURN_GENERATED_KEYS   // ⭐ IMPORTANT
            );

            ps.setString(1, name);
            ps.setInt(2, trainNo);
            ps.setString(3, classType);
            ps.setDate(4, Date.valueOf(date));
            ps.setString(5, from);
            ps.setString(6, to);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int pnr = rs.getInt(1);
                    System.out.println("Reservation Successful!");
                    System.out.println("Your PNR Number is: " + pnr);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public void viewReservations() {

            String query = "SELECT * FROM reservations";

            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ResultSet rs = ps.executeQuery();

                System.out.println("+-----+----------+---------+------------+------------+-------------+-------------+");
                System.out.println("| PNR | Name     | TrainNo | Class      | Date       | From        | To          |");
                System.out.println("+-----+----------+---------+------------+------------+-------------+-------------+");

                while (rs.next()) {
                    System.out.printf("| %-3d | %-8s | %-7d | %-10s | %-10s | %-11s | %-11s |\n",
                            rs.getInt("pnr"),
                            rs.getString("passenger_name"),
                            rs.getInt("train_no"),
                            rs.getString("class_type"),
                            rs.getDate("journey_date"),
                            rs.getString("from_station"),
                            rs.getString("to_station"));
                    System.out.println("+-----+----------+---------+------------+------------+-------------+-------------+");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public boolean cancelReservation(int pnr) {

            String query = "DELETE FROM reservations WHERE pnr = ?";

            try {
                PreparedStatement ps = connection.prepareStatement(query);
                ps.setInt(1, pnr);

                return ps.executeUpdate() > 0;

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }
    }




