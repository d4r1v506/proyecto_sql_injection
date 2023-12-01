package com.proyecto;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;
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

    public static void main(String[] args) {
        Main configReader = new Main("config.properties");
        String url = configReader.getProperty("url");
        String username = configReader.getProperty("username");
        String password = configReader.getProperty("password");
        Scanner scanner = new Scanner(System.in);
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(url, username, password);
            logger.info("Ingrese el id del producto: ");
            String productId = scanner.nextLine();
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
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
