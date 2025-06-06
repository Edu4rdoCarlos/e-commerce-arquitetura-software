package infra.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import domain.model.User;
import domain.model.UserRole;
import infra.DatabaseConnection;

public class UserRepository {

    public User findByEmailAndPassword(String email, String password) throws SQLException {
        User user = null;
            Connection conn = DatabaseConnection.getInstance();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM users WHERE email = ? AND password = ?"
            );
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String roleStr = rs.getString("role");
                UserRole role = (roleStr != null) ? UserRole.valueOf(roleStr.toUpperCase()) : UserRole.USER;

                user = new User(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        role
                );
            }

            rs.close();
            stmt.close();
            conn.close();



        return user;
    }
}
