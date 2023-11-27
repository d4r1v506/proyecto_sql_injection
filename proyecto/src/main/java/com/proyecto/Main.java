package com.proyecto;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "username",
                    "password");

            // Crear una sentencia SQL vulnerable a SQL Injection
            String productId = args[0];
            String sql = "SELECT * FROM productos WHERE id = " + productId;

            // Ejecutar la consulta
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Procesar los resultados
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                System.out.println("ID: " + id + ", Nombre: " + nombre + ", Precio: " + precio);
            }

            // Cerrar la conexión
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
