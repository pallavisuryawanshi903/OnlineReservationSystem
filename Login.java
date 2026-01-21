package OnlineReservationSystem;

import java.sql.*;

public class Login {

    private Connection connection;

    public Login(Connection connection) {
        this.connection = connection;
    }

    public boolean validate(String username, String password) {

        String query = "SELECT * FROM users WHERE username=? AND password=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
