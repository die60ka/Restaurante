package com.restaurante.dcarrasco.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtilities {

    //Atributos de clase para gestion de conexión con la base de datos
    private static final String BASE_DATOS = "/home/die60/Programation/Java/Ejercicios/Restaurante/corrientazo/src/main/java/com/restaurante/dcarrasco/resources/corrientazo.db";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            var url = "jdbc:sqlite:" + BASE_DATOS;
            connection = DriverManager.getConnection(url);
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
        }
    }
}
