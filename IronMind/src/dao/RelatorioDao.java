package dao;

import java.sql.Connection;
import java.sql.DriverManager;

// Conexão com PostgreSQL.

public class RelatorioDao {

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/academia",
                "postgres",
                "123"
        );
    }
}