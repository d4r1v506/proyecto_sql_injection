package com.proyecto;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {

            Scanner scanner = new Scanner(System.in);
            // Establecer la conexión con la base de datos
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root",
                    "");

            // Crear una sentencia SQL vulnerable a SQL Injection
            // String productId = "1";

            System.out.print("Ingrese el id del producto: ");
            String productId = scanner.nextLine();
            String sql = "SELECT * FROM productos WHERE id = " + productId;

            // Ejecutar la consulta
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Procesar los resultados
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                // double precio = resultSet.getDouble("precio");
                System.out.println("ID: " + id + ", Nombre: " + nombre);
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
