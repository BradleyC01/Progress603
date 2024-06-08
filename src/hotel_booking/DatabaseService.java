/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bradl
 */

public class DatabaseService implements BookingManager {

    @Override
    public List<Location> getAllLocations() {
        List<Location> locations = new ArrayList<>();
        String query = "SELECT location_id, name FROM Location";
        try ( Connection conn = dbManager.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int locationId = rs.getInt("location_id");
                String name = rs.getString("name");
                locations.add(new Location(locationId, name));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving locations: " + e.getMessage());
        }
        return locations;
    }

    @Override
    public List<Hotel> getHotelsByLocation(int locationId) {
        List<Hotel> hotels = new ArrayList<>();
        String query = "SELECT hotel_id, name, address, description, price, image_url FROM Hotel WHERE location_id = ?";
        try ( Connection conn = dbManager.getConnection();  PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, locationId);
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int hotelId = rs.getInt("hotel_id");
                    String name = rs.getString("name");
                    String address = rs.getString("address");
                    String description = rs.getString("description");
                    double price = rs.getDouble("price");
                    String imageUrl = rs.getString("image_url");
                    Hotel hotel = new Hotel(hotelId, name, address, description, price, imageUrl, locationId);
                    hotels.add(hotel);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving hotels: " + e.getMessage());
        }
        return hotels;
    }

    @Override
    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT name, price, description, image_url FROM Room";
        try ( Connection conn = dbManager.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String imageUrl = rs.getString("image_url");
                Room room = new Room(name, price, description, imageUrl);
                rooms.add(room);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving rooms: " + e.getMessage());
        }
        return rooms;
    }

    @Override
    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM Service";

        try ( Connection conn = dbManager.getConnection();  PreparedStatement ps = conn.prepareStatement(query);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Service service = new Service();
                service.setId(rs.getInt("service_id"));
                service.setName(rs.getString("name"));
                service.setPrice(rs.getDouble("price"));
                services.add(service);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving services: " + e.getMessage());
        }
        return services;
    }

    @Override
    public void saveBooking(Booking booking) {
        //save booking to file

    }
}
