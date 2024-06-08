/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author shaikasif
 */
public interface BookingOperations {

    int getBookingId(); 

    //get and set the booking id
    
    void setBookingId(int bookingId); 

    //Sets the location associated with the booking.
    Location getLocation();

    void setLocation(Location location);

    //Gets the check-in date for the booking.
    LocalDate getCheckInDate();

    void setCheckInDate(LocalDate checkInDate);

    //Gets the check-out date for the booking.
    LocalDate getCheckOutDate();

    void setCheckOutDate(LocalDate checkOutDate);

    long getNights();
    //sets the number of nights, check out - checkin
    void setNights(long nights);

    int getAdults();

    void setAdults(int adults); //adults selected

    int getChildren();

    void setChildren(int children);//children selected

    Hotel getHotel();

    void setHotel(Hotel hotel); //hotel associated with booking

    Room getRoom();

    void setRoom(Room room); //room selected

    List<Service> getServices(); // multpme services selected

    void setServices(List<Service> services);

    String getCustomerName();

    void setCustomerName(String customerName);

    String getDob();

    void setDob(String dob);

    boolean isMarried();

    void setMarried(boolean married); //tick box yes or no

    String getContactNumber();

    void setContactNumber(String contactNumber); 
    // till here basic details bor customer
    

    String getStatus();// the status of the booking (booked, not booked).

    void setStatus(String status);

    
    double getTotalServicePrice(); // services selected price

    void updateTotalServicePrice(); //prices for services total

    double calculateTax(); //addiing 2% tax

    double calculateTotalPriceWithTax(); // calculated price wit tax

    double calculateTotalRoomPrice(double basePrice, double additionalPrice, long nights); // further calculations

    void setTotalServicePrice(double totalServicePrice);

    double getTotalRoomPrice(); // gets the room price selected 

    String getServiceNames();
}


// this class pleaces a vital role 
//It defines the necessary methods to manage booking details.