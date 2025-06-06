package service;

import model.Product;
import infra.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public List<Product> getAllProducts() {
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
