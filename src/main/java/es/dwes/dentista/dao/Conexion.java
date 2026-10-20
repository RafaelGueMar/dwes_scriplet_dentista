package es.dwes.dentista.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {
    private static final String URL_PREDETERMINADA =
            "jdbc:mysql://localhost:3306/dentista?useSSL=false&allowPublicKeyRetrieval=true&characterEncoding=UTF-8";

    private Conexion() { }

    public static Connection obtenerConexion() throws SQLException {
        String url = System.getenv().getOrDefault("DENTISTA_DB_URL", URL_PREDETERMINADA);
        String usuario = System.getenv().getOrDefault("DENTISTA_DB_USER", "user");
        String contrasena = System.getenv().getOrDefault("DENTISTA_DB_PASSWORD", "user");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver JDBC de MySQL.", e);
        }
        return DriverManager.getConnection(url, usuario, contrasena);
    }
}