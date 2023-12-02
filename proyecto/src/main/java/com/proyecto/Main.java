package com.proyecto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private Properties properties;
    private static Logger logger = LoggerFactory.getLogger(Main.class.getName());

    public Main(String filePath) {
        properties = new Properties();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) throws IOException {
        Main configReader = new Main("config.properties");
        String url = configReader.getProperty("url");
        String username = configReader.getProperty("username");
        String password = configReader.getProperty("password");
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Ingrese el id del producto: ");
            String productId = scanner.readLine();
            String sql = "SELECT * FROM productos WHERE id = ?";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.valueOf(productId));
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                logger.info("ID: {}, Nombre: {}", id, nombre);
            }
            scanner.close();
        } catch (SQLException e) {
            logger.error("Hay un error inesperado");
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar");
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar");
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error("Error al cerrar");
                }
            }
        }
    }
}