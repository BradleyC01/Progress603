package hotel_booking;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Booking implements BookingManager {

    private final Guest guest;
    private final Room room;
    private final List<Service> services;
    private final String checkInDate;
    private final String checkOutDate;
    private final int adults;
    private final int children;
    private final int bookingId;
    private final double totalPriceWithTax;
    private String bookingStatus;

    public Booking(int bookingId, Guest guest, Room room, List<Service> services, LocalDate checkInLocalDate, LocalDate checkOutLocalDate, int adults, int children, double totalPriceWithTax, String bookingStatus) {
        this.bookingId = bookingId;
        this.guest = guest;
        this.room = room;
        this.services = services;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.checkInDate = checkInLocalDate.format(dateFormatter);
        this.checkOutDate = checkOutLocalDate.format(dateFormatter);
        this.adults = adults;
        this.children = children;
        this.totalPriceWithTax = totalPriceWithTax;
        this.bookingStatus = bookingStatus;

    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingStatus(String newStatus) {  
        this.bookingStatus = newStatus;
        this.room.setBooked("Confirmed".equals(newStatus));
    }

    @Override
    public String toString() {

        return "________________________________________________________________________________________________\n"
                + "Booking Reference ID: " + bookingId
                + "\nBooking Details:" + "\n"
                + "Guest Name:            " + getFullName() + "\n"
                + "Contact Details:       " + getContactDetails() + "\n"
                + "Room Number:           " + room.getNumber() + "\n"
                + "Hotel Name:            " + (room.getHotel() != null ? room.getHotel().getName() : "N/A") + "\n"
                + "Address:               " + (room.getHotel() != null ? room.getHotel().getAddress() : "N/A") + "\n"
                + "Room Description:      " + room.getDescription() + "\n"
                + "Booked:                " + (room.isBooked() ? "Yes" : "No") + "\n"
                + "Services Added:        " + getServiceDetails(services) + "\n"
                + "Check-in Date:         " + checkInDate + "\n"
                + "Check-out Date:        " + checkOutDate + "\n"
                + "Adults:                " + adults + "\n"
                + "Children:              " + children + "\n"
                + "Total Price with Tax:  $" + String.format("%.2f", totalPriceWithTax) + "\n"
                + "Booked Status:         " + bookingStatus;

    }

    @Override
    public String getContactDetails() {
        return "Mobile Number : " + guest.getTelephone() + " | " + "Mail Id : " + guest.getEmail(); 
    }

    
    @Override
    public String getServiceDetails(List<Service> services) {

        StringBuilder serviceDetails = new StringBuilder();
        for (int i = 0; i < services.size(); i++) {
            if (i > 0) {
                serviceDetails.append(", ");
            }
            serviceDetails.append(services.get(i).getName());
        }
        return serviceDetails.toString();
    }

    
    @Override
    public List<Service> getServices() {
        return services;
    }

    
    @Override
    public String getFullName() {
        return guest.getFirstName() + " " + guest.getLastName();
    }
}
