package infra.repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.model.Product;
import infra.DatabaseConnection;

public class ProductRepository {

    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try {
            Connection conn = DatabaseConnection.getInstance();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("SELECT id, name, price, stock FROM products");

            while (rs.next()) {
                Product p = new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("stock")
                );
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
}
