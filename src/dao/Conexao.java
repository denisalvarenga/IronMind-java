package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por fornecer conexão com o banco PostgreSQL.
 */
public class Conexao {

    private static final String URL = "jdbc:postgresql://localhost:5432/IronMind";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123456";

    /**
     * Retorna conexão ativa com o banco.
     * 
     * @return Connection
     * @throws SQLException erro ao conectar
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado. Verifique o .jar no classpath.", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
