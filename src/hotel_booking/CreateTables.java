/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

/**
 *
 * @author shaikasif
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTables {

    private DataManager dbManager;
    private Connection conn;
    private Statement statement;

    public CreateTables() {
        dbManager = DataManager.getInstance(); // Use singleton instance
        conn = dbManager.getConnection();
        if (conn != null) {
            try {
                statement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Error creating statement: " + ex.getMessage());
            }
        } else {
            System.out.println("Failed to establish database connection.");
        }
    }

    public void createDatabaseTables() {
        if (statement == null) {
            System.out.println("Statement is null, aborting table creation.");
            return;
        }

        try {
            // Drop tables if they already exist
            dropTables();

            // Create Location table
            String createLocationTable = "CREATE TABLE Location ("
                    + "location_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "name VARCHAR(50)"
                    + ")";
            statement.execute(createLocationTable);

            // Create Hotel table
            String createHotelTable = "CREATE TABLE Hotel ("
                    + "hotel_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "name VARCHAR(100),"
                    + "address VARCHAR(200),"
                    + "description VARCHAR(500),"
                    + "price DOUBLE,"
                    + "image_url VARCHAR(200),"
                    + "location_id INT,"
                    + "FOREIGN KEY (location_id) REFERENCES Location(location_id)"
                    + ")";
            statement.execute(createHotelTable);

            // Create Room table
            String createRoomTable = "CREATE TABLE Room ("
                    + "room_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "name VARCHAR(100),"
                    + "price DOUBLE,"
                    + "description VARCHAR(500),"
                    + "image_url VARCHAR(200)"
                    + ")";
            statement.execute(createRoomTable);

            // Create Service table
            String createServiceTable = "CREATE TABLE Service ("
                    + "service_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                    + "name VARCHAR(100),"
                    + "price DOUBLE"
                    + ")";
            statement.execute(createServiceTable);

            // Insert sample data
            insertSampleData();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            dbManager.closeConnections();
        }
    }

    private void dropTables() {
        try {
            statement.execute("DROP TABLE Booking_Services");
        } catch (SQLException e) {
            System.out.println("Error dropping Booking_Services table: " + e.getMessage());
        }
        try {
            statement.execute("DROP TABLE Booking");
        } catch (SQLException e) {
            System.out.println("Error dropping Booking table: " + e.getMessage());
        }
        try {
            statement.execute("DROP TABLE Service");
        } catch (SQLException e) {
            System.out.println("Error dropping Service table: " + e.getMessage());
        }
        try {
            statement.execute("DROP TABLE Room");
        } catch (SQLException e) {
            System.out.println("Error dropping Room table: " + e.getMessage());
        }
        try {
            statement.execute("DROP TABLE Hotel");
        } catch (SQLException e) {
            System.out.println("Error dropping Hotel table: " + e.getMessage());
        }
        try {
            statement.execute("DROP TABLE Location");
        } catch (SQLException e) {
            System.out.println("Error dropping Location table: " + e.getMessage());
        }
    }

    private void insertSampleData() {
        try {
            // Insert data into Location table
            String insertLocation = "INSERT INTO Location (name) VALUES "
                    + "('Auckland'), "
                    + "('Wellington'), "
                    + "('Christchurch'), "
                    + "('Queenstown'), "
                    + "('Rotorua')";
            statement.executeUpdate(insertLocation);

            // Insert data into Hotel table
            String insertHotel = "INSERT INTO Hotel (name, address, description, price, image_url, location_id) VALUES "
                    + "('Park Hyatt Auckland', '99 Halsey Street, Auckland CBD', '5 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Auckland CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 300, 'src/images/Parkhyatt.png', 1), "
                    + "('Cordis Auckland', '83 Symonds Street, Grafton', '4 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Auckland CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 200, 'src/images/Parkhyatt.png', 1), "
                    + "('Asif Auckland', '83 Symonds Street, Grafton ', '4 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Auckland CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 300, 'src/images/Parkhyatt.png', 1), "
                    + "('Novotel Wellington', '133 The Terrace', '4 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Wellington CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 150, 'src/images/Parkhyatt.png', 2), "
                    + "('InterContinental Wellington', '2 Grey Street', '5 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Wellington CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 250, 'src/images/Parkhyatt.png', 2), "
                    + "('Hotel Montreal Christchurch', '351 Montreal Street', '5 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Christchurch CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 350, 'src/images/Parkhyatt.png', 3), "
                    + "('The George Christchurch', '50 Park Terrace', '5 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Christchurch CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 400, 'src/images/Parkhyatt.png', 3), "
                    + "('Sofitel Queenstown', '8 Duke Street', '5 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Queenstown CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 450, 'src/images/Parkhyatt.png', 4), "
                    + "('Eichardt''s Private Hotel', 'Marine Parade', '5 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Queenstown CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 500, 'src/images/Parkhyatt.png', 4), "
                    + "('Prince''s Gate Hotel', '1057 Arawa Street', '4 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Rotorua CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 180, 'src/images/Parkhyatt.png', 5), "
                    + "('Jet Park Hotel Rotorua', '237 Fenton Street', '4 star hotel with 4.5 rating. This stylish boutique hotel in the heart of Rotorua CBD (Central Business District) is a short walk from the Viaduct Harbour and the Sky Tower.', 160, 'src/images/Parkhyatt.png', 5)";
            statement.executeUpdate(insertHotel);

            // Insert data into Room table
            String insertRoom = "INSERT INTO Room (name, price, description, image_url) VALUES "
                    + "('Room 101: Superior King Room', 0.00, 'Superior King Room', 'src/images/Room.jpg'), "
                    + "('Room 102: Deluxe King Room', 32.00, 'Deluxe King Room', 'src/images/Room.jpg'), "
                    + "('Room 103: Deluxe King Room with Open Balcony', 35.00, 'Deluxe King Room with Open Balcony', 'src/images/Room.jpg'), "
                    + "('Room 104: Junior Suite with Balcony', 34.00, 'Junior Suite with Balcony', 'src/images/Room.jpg'), "
                    + "('Room 105: Superior King Room', 20.00, 'Superior King Room', 'src/images/Room.jpg'), "
                    + "('Room 106: Deluxe King Room', 22.00, 'Deluxe King Room', 'src/images/Room.jpg'), "
                    + "('Room 107: Deluxe King Room with Open Balcony', 35.00, 'Deluxe King Room with Open Balcony', 'src/images/Room.jpg'), "
                    + "('Room 108: Junior Suite with Balcony', 20.00, 'Junior Suite with Balcony', 'src/images/Room.jpg')";
            statement.executeUpdate(insertRoom);

            // Insert data into Service table
            String insertService = "INSERT INTO Service (name, price) VALUES "
                    + "('WiFi', 0), "
                    + "('Breakfast', 20), "
                    + "('Parking', 15), "
                    + "('Spa', 50)";
            statement.executeUpdate(insertService);

            System.out.println("Sample data inserted successfully");

        } catch (SQLException e) {
        }
    }

    public static void main(String[] args) {
        CreateTables createTables = new CreateTables();
        createTables.createDatabaseTables();
    }
}


// created the main method and created a sample data , for tables we need.
