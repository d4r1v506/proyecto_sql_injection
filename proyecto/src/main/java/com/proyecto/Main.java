package com.proyecto;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {

            Scanner scanner = new Scanner(System.in);            
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase", "root",
                    "");

            System.out.print("Ingrese el id del producto: ");
            String productId = scanner.nextLine();
            String sql = "SELECT * FROM productos WHERE id = " + productId;

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                System.out.println("ID: " + id + ", Nombre: " + nombre);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
