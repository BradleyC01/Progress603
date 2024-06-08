package hotel_booking;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FileIOUtility {

    private static final String LAST_BOOKING_ID_FILE = "lastBookingId.txt";

    public static void writeBookingToFile(Booking booking, String filename) {
        List<Booking> bookings = readAllBookings(filename);
        boolean found = false;
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getBookingId() == booking.getBookingId()) {
                bookings.set(i, booking);
                found = true;
                break;
            }
        }
        if (!found) {
            bookings.add(booking);
        }
        saveAllBookings(bookings, filename);
    }

    private static void saveAllBookings(List<Booking> bookings, String filename) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) {
            for (Booking booking : bookings) {
                out.println(serializeBooking(booking));
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static List<Booking> readAllBookings(String filename) {
        List<Booking> bookings = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            StringBuilder bookingData = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                if (line.equals("----------------------------------------")) {
                    Booking booking = deserializeBooking(bookingData.toString());
                    if (booking != null) {
                        bookings.add(booking);
                    }
                    bookingData = new StringBuilder();
                } else {
                    bookingData.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return bookings;
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

    public static Booking readBookingFromFile(String referenceNumber, String filename) {
        List<Booking> bookings = readAllBookings(filename);
        for (Booking booking : bookings) {
            if (String.valueOf(booking.getBookingId()).equals(referenceNumber)) {
                return booking;
            }
        }
        return null;
    }

   private static String serializeBooking(Booking booking) {
    StringBuilder sb = new StringBuilder();
    sb.append("References Number : ").append(booking.getBookingId()).append("\n")
      .append("Location Selected: ").append(booking.getLocation().getName()).append("\n")
      .append("Check In Date: ").append(booking.getCheckInDate()).append("\n")
      .append("Check Out Date: ").append(booking.getCheckOutDate()).append("\n")
      .append("Total Nights: ").append(booking.getNights()).append("\n")
      .append("Hotel Selected: ").append(booking.getHotel().getName()).append("\n")
      .append("Room Selected: ").append(booking.getRoom().getName()).append("\n")
      .append("Services Selected: ").append(booking.getServiceNames()).append("\n")
      .append("Customer Name: ").append(booking.getCustomerName()).append("\n")
      .append("Date of Birth: ").append(booking.getDob()).append("\n")
      .append("Married: ").append(booking.isMarried()).append("\n")
      .append("Contact Number: ").append(booking.getContactNumber()).append("\n")
      .append("Room Price: $").append(booking.getRoom().getPrice()).append("\n")
      .append("Number of Adults: ").append(booking.getAdults()).append("\n")
      .append("Number of Children: ").append(booking.getChildren()).append("\n")
      .append("Service Charge: $").append(booking.getTotalServicePrice()).append("\n")
      .append("Total Price with Tax: $").append(String.format("%.2f", booking.calculateTotalPriceWithTax())).append("\n") // Fixe
      .append("Booking Status: ").append(booking.getStatus()).append("\n")
      .append("----------------------------------------");
    return sb.toString();
}

private static Booking deserializeBooking(String data) {
    Booking booking = new Booking();
    String[] lines = data.split("\n");
    for (String line : lines) {
        String[] parts = line.split(":", 2);
        if (parts.length < 2) continue; // Skip invalid lines
        String key = parts[0].trim();
        String value = parts[1].trim();
        try {
            switch (key) {
                case "References Number":
                    booking.setBookingId(Integer.parseInt(value));
                    break;
                case "Location Selected":
                    booking.setLocation(new Location(0, value));
                    break;
                case "Check In Date":
                    booking.setCheckInDate(LocalDate.parse(value));
                    break;
                case "Check Out Date":
                    booking.setCheckOutDate(LocalDate.parse(value));
                    break;
                case "Total Nights":
                    booking.setNights(Long.parseLong(value));
                    break;
                case "Hotel Selected":
                    booking.setHotel(new Hotel(0, value, "", "", 0, "", 0));
                    break;
                case "Room Selected":
                    booking.setRoom(new Room(value, 0, "", ""));
                    break;
                case "Services Selected":
                    booking.setServices(deserializeServices(value));
                    break;
                case "Customer Name":
                    booking.setCustomerName(value);
                    break;
                case "Date of Birth":
                    booking.setDob(value);
                    break;
                case "Married":
                    booking.setMarried(value.equalsIgnoreCase("yes"));
                    break;
                case "Contact Number":
                    booking.setContactNumber(value);
                    break;
                case "Room Price":
                    booking.getRoom().setPrice(Double.parseDouble(value.substring(1)));
                    break;
                case "Number of Adults":
                    booking.setAdults(Integer.parseInt(value));
                    break;
                case "Number of Children":
                    booking.setChildren(Integer.parseInt(value));
                    break;
                case "Service Charge":
    booking.setTotalServicePrice(Double.parseDouble(value.substring(1))); // Add this line
    break;

                case "Tax 2%":
                    // This can be skipped as it is calculated from services
                    break;
                case "Total Price with Tax":
                    // This can be skipped as it is calculated from services
                    break;
                case "Booking Status":
                    booking.setStatus(value);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line);
            e.printStackTrace();
        }
    }
    return booking;
}

    private static List<Service> deserializeServices(String serviceNames) {
        List<Service> services = new ArrayList<>();
        String[] names = serviceNames.split(", ");
        for (String name : names) {
            services.add(new Service(name, 0)); // Simplified for demo
        }
        return services;
    }
}
