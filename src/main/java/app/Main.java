package app;

import infra.DatabaseConnection;
import infra.DatabaseInitializer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getInstance();

            DatabaseInitializer.initialize(conn);

            DatabaseInitializer.seed(conn);

            conn.close();
            System.out.println("Banco inicializado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
