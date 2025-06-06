package infra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS users (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100),
                email VARCHAR(100),
                password VARCHAR(100)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS products (
                id SERIAL PRIMARY KEY,
                name VARCHAR(100),
                price NUMERIC,
                stock INTEGER
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS orders (
                id SERIAL PRIMARY KEY,
                user_id INTEGER REFERENCES users(id),
                total NUMERIC,
                status VARCHAR(50)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS payments (
                id SERIAL PRIMARY KEY,
                order_id INTEGER REFERENCES orders(id),
                method VARCHAR(50),
                status VARCHAR(50)
            );
        """);

        stmt.execute("""
            CREATE TABLE IF NOT EXISTS cart (
                user_id INTEGER REFERENCES users(id),
                product_id INTEGER REFERENCES products(id),
                PRIMARY KEY (user_id, product_id),
                shipping DOUBLE
            );
        """);

        stmt.close();
    }

    public static void seed(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        stmt.executeUpdate("""
        INSERT INTO users (name, email) 
        VALUES 
            ('Dudu', 'dudu@email.com', '123456', 'admin'),
            ('Ana', 'ana@gmail.com', '123456', 'user'),
            ('Carlos', 'carlos@hotmail.com', '123456', 'user')
        ON CONFLICT DO NOTHING;
    """);

        stmt.executeUpdate("""
        INSERT INTO products (name, price, stock) 
        VALUES 
            ('Mouse Gamer', 150.00, 50),
            ('Teclado Mecânico', 250.00, 30),
            ('Monitor 24"', 800.00, 20),
            ('Headset Bluetooth', 300.00, 15),
            ('Cadeira Ergonômica', 1200.00, 5)
        ON CONFLICT DO NOTHING;
    """);

        stmt.executeUpdate("""
        INSERT INTO orders (user_id, total, status) 
        VALUES 
            (1, 400.00, 'PENDENTE'),
            (2, 1050.00, 'PAGO'),
            (3, 150.00, 'CANCELADO')
        ON CONFLICT DO NOTHING;
    """);

        stmt.executeUpdate("""
        INSERT INTO payments (order_id, method, status) 
        VALUES 
            (1, 'cartao_credito', 'AGUARDANDO'),
            (2, 'pix', 'PAGO'),
            (3, 'boleto', 'CANCELADO')
        ON CONFLICT DO NOTHING;
    """);

        stmt.executeUpdate("""
        INSERT INTO cart (user_id, product_id)
        VALUES 
            (1, 1),  -- Dudu comprando Mouse
            (1, 2),  -- Dudu comprando Teclado
            (2, 4),  -- Ana comprando Headset
            (3, 3)   -- Carlos comprando Monitor
        ON CONFLICT DO NOTHING;
    """);

        stmt.close();
    }
}
