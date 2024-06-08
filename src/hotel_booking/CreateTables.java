package hotel_booking;

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
                    // Hotels in Auckland
                    + "('SkyCity Hotel Auckland', 'Cnr of Victoria & Federal Streets, 1010 Auckland, New Zealand', '8.4 Rating Very Good. Located in the heart of Auckland CBD, next to the iconic Sky Tower and SkyCity Casino.', 285, 'src/images/Skycity.jpg', 1), "
                    + "('Cordis Auckland', '83 Symonds Street, 1140 Auckland, New Zealand', '8.5 rating very good. Located in the lively uptown area near Upper Queen Street and Karangahape Road.', 215, 'src/images/Cordis.jpg', 1), "
                    + "('Airport Gateway Hotel', '206 Kirkbride Road, 2022 Auckland, New Zealand', '6.6 rating Pleasant. Just 5 minutes drive from Auckland Airport, free on-site parking.', 135, 'src/images/Gateway.jpg', 1), "
                    + "('Auckland Rose Park Hotel', '92-102 Gladstone Road, Parnell, 1010 Auckland, New Zealand', '7.7 rating good. Located opposite the Parnell Rose Gardens, 5 minutesâ€™ drive from city centre.', 175, 'src/images/Rosepark.jpg', 1), "
                    + "('Grand Millennium Auckland', '71 Mayoral Dr, Cnr Vincent St, 1010 Auckland, New Zealand', '8.1 rating Very Good. Strives to provide a superior experience through excellent service.', 200, 'src/images/Grand.jpg', 1), "
                    // Hotels in Wellington
                    + "('QT Museum Wellington', '90 Cable Street, 6011 Wellington, New Zealand', '8.7 rating Excellent. An artsy design hotel with a creative ambience on Wellingtonâ€™s waterfront.', 250, 'src/images/Qt.jpg', 2), "
                    + "('InterContinental Wellington', '2 Grey Street, 6011 Wellington, New Zealand', '8.8 rating Excellent. Centrally located by the harbor waterfront with an indoor heated pool, spa, and sauna.', 290, 'src/images/Intercontinental.jpg', 2), "
                    + "('James Cook Hotel Grand Chancellor', '147 The Terrace, 6011 Wellington, New Zealand', '8.0 rating Very Good. Located centrally with easy access to the Wellington cable car and CBD.', 200, 'src/images/Jamescook.jpg', 2), "
                    + "('Rydges Wellington', '75 Featherston Street, 6011 Wellington, New Zealand', '7.8 rating Good. Offers luxurious rooms with stunning views and top-notch amenities.', 220, 'src/images/Rydges.jpg', 2), "
                    + "('Bolton Hotel Wellington', 'Corner of Bolton & Mowbray Streets, 6011 Wellington, New Zealand', '8.6 rating Excellent. Located centrally with access to various outdoor activities.', 240, 'src/images/Bolton.jpg', 2), "
                    // Hotels in Christchurch
                    + "('Hotel Montreal Christchurch', '351 Montreal Street', '5 star hotel with 4.5 rating. Stylish boutique hotel in the heart of Christchurch CBD.', 350, 'src/images/Montreal.jpg', 3), "
                    + "('The George Christchurch', '50 Park Terrace', '5 star hotel with 4.5 rating. Stylish boutique hotel in the heart of Christchurch CBD.', 400, 'src/images/George.jpg', 3), "
                    + "('Crowne Plaza Christchurch', '764 Colombo Street, 8011 Christchurch, New Zealand', '8.9 rating Excellent. Centrally located with modern rooms and top-notch amenities.', 320, 'src/images/Crown.jpg', 3), "
                    + "('Rendezvous Hotel Christchurch', '166 Gloucester Street, 8011 Christchurch, New Zealand', '8.5 rating Very Good. Modern hotel in the heart of Christchurch.', 290, 'src/images/Rendezvous.jpeg', 3), "
                    + "('Sudima Christchurch City', '47-49 Salisbury Street, 8013 Christchurch, New Zealand', '8.7 rating Excellent. Stylish hotel with luxurious rooms and excellent amenities.', 370, 'src/images/Sudima.jpg', 3), "
                    // Hotels in Queenstown
                    + "('Sofitel Queenstown', '8 Duke Street, Queenstown 9300, New Zealand', '5 star hotel with 4.5 rating. Stylish boutique hotel in the heart of Queenstown CBD.', 450, 'src/images/Sofitel.jpg', 4), "
                    + "('Eichardt''s Private Hotel', 'Marine Parade, Queenstown 9300, New Zealand', '5 star hotel with 4.5 rating. Stylish boutique hotel in the heart of Queenstown CBD.', 500, 'src/images/Eicherds.jpeg', 4), "
                    + "('Novotel Queenstown Lakeside', 'Marine Parade, Queenstown 9300, New Zealand', '8.5 rating Very Good. Located on the waterfront with stunning lake views.', 320, 'src/images/Novotel.jpg', 4), "
                    + "('Crowne Plaza Queenstown', '93 Beach Street, Queenstown 9300, New Zealand', '8.7 rating Excellent. Modern hotel with luxurious rooms and top-notch amenities.', 380, 'src/images/Courtyard.jpg', 4), "
                    + "('Hilton Queenstown Resort & Spa', 'Kawarau Village, Peninsula Rd, Queenstown 9300, New Zealand', '8.5 rating Very Good. Luxurious resort with stunning lake views and top-notch amenities.', 420, 'src/images/Hilton.jpg', 4), "
                    // Hotels in Rotorua
                    + "('Prince''s Gate Hotel', '1057 Arawa Street, Rotorua 3010, New Zealand', '4 star hotel with 4.5 rating. Stylish boutique hotel in the heart of Rotorua CBD.', 180, 'src/images/Princes.jpg', 5), "
                    + "('Jet Park Hotel Rotorua', '237 Fenton Street, Rotorua 3010, New Zealand', '4 star hotel with 4.5 rating. Stylish boutique hotel in the heart of Rotorua CBD.', 160, 'src/images/Getpark.jpg', 5), "
                    + "('Novotel Rotorua Lakeside', 'Lake End, Tutanekai Street, Rotorua 3010, New Zealand', '8.2 rating Very Good. Modern hotel with stunning lake views and top-notch amenities.', 210, 'src/images/Rotruvanovotel.jpg', 5), "
                    + "('Millennium Hotel Rotorua', '1270 Hinemaru Street, Rotorua 3010, New Zealand', '8.1 rating Very Good. Luxurious hotel with excellent amenities and lake views.', 230, 'src/images/Milinium.jpg', 5), "
                    + "('Pullman Rotorua', '1135 Arawa Street, Rotorua 3010, New Zealand', '8.3 rating Excellent. Modern hotel with luxurious rooms and top-notch amenities.', 250, 'src/images/Pulman.jpg', 5)";

            statement.executeUpdate(insertHotel);

            // Insert data into Room table
            String insertRoom = "INSERT INTO Room (name, price, description, image_url) VALUES "
                    + "('Superior King Room', 0.00, 'Basic room, default room no extra charge', 'src/images/room1.jpeg'), "
                    + "('Deluxe King Room', 32.00, 'Spacious and stylish, perfect for a comfortable stay.', 'src/images/Superior.jpg'), "
                    + "('Deluxe King Room with Open Balcony', 35.00, 'Enjoy modern luxury with a spacious balcony for relaxing views.', 'src/images/room5.jpg'), "
                    + "('Junior Suite with Balcony', 34.00, 'This elegant suite offers a private balcony with stunning views.', 'src/images/room6.jpeg'), "
                    + "('Luxury Room', 50.00, 'Elegantly designed with premium amenities and plush furnishings,', 'src/images/Room.jpg')";
            statement.executeUpdate(insertRoom);

            // Insert data into Service table
            String insertService = "INSERT INTO Service (name, price) VALUES "
                    + "('WiFi', 0), "
                    + "('Breakfast', 20), "
                    + "('Parking', 15), "
                    + "('Swimming Pool', 23), "
                    + "('Spa', 50), "
                    + "('Gym', 30), "
                    + "('Airport Shuttle', 40), "
                    + "('Laundry Service', 25), "
                    + "('Room Service', 35), "
                    + "('Mini Bar', 45), "
                    + "('Massage', 60)";
            statement.executeUpdate(insertService);

            System.out.println("Data inserted successfully");

        } catch (SQLException e) {
        }
    }

    public static void main(String[] args) {
        CreateTables createTables = new CreateTables();
        createTables.createDatabaseTables();
    }
}


// created the main method in create table and any changes to data will be here.
