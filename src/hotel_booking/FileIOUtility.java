    package hotel_booking;

    import java.io.*;
    import java.nio.file.Files;
    import java.nio.file.Paths;

    public class FileIOUtility {

        private static final String LAST_BOOKING_ID_FILE = "lastBookingId.txt"; //creating this file to store Unique the booking refrences id & generate Unique refrences id
        //used chatgpt to understand equiping the last booking refrencing number

        public static void writeBookingToFile(Booking booking, String filename) {
            try {
                saveLastBookingId(booking.getBookingId());
                try ( PrintWriter out = new PrintWriter(new FileWriter(filename, true))) {
                    out.println(booking.toString());
                }
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }

        static void saveLastBookingId(int lastId) {
            try {
                Files.write(Paths.get(LAST_BOOKING_ID_FILE), String.valueOf(lastId).getBytes());
            } catch (IOException e) {
                System.err.println("Error saving last booking ID: " + e.getMessage());
            }
        }

        public static int loadLastBookingId() {
            try {
                if (new File(LAST_BOOKING_ID_FILE).exists()) {
                    String lastIdStr = new String(Files.readAllBytes(Paths.get(LAST_BOOKING_ID_FILE)));
                    return Integer.parseInt(lastIdStr.trim());
                }
            } catch (IOException | NumberFormatException e) {
                System.err.println("Error loading last booking ID: " + e.getMessage());
            }
            return 7863310;
        }
    }
