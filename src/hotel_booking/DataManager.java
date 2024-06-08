/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author shaikasif
 */
public class DataManager {
    private static DataManager instance;
    private Connection connection;
    private final String url = "jdbc:derby://localhost:1527/HotelBookingSystemDB_Ebd;create=true";
    private final String username = "hotel"; 
    private final String password = "hotel"; 

    private DataManager() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connection established."); //A new Connection is established
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, username, password);
            }
        } catch (SQLException e) {
            System.out.println("Failed to get connection: " + e.getMessage());
        }
        return connection;
    }

    public void closeConnections() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to close connection: " + e.getMessage());
        }
    }
}
