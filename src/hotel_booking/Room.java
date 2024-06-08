package hotel_booking;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final int number;
    private final double price;
    private boolean isBooked;
    private final String description;
    private final List<Service> services;
    private Hotel hotel; 
    

    public Room(int number, double price, String description) {
        this.number = number;
        this.price = price;
        this.isBooked = false;
        this.description = description;
        this.services = new ArrayList<>();
        
    }
    
    

    // Method to add a service to the room
    public void addService(Service service) {
        services.add(service);
    }

    // Getters and setters
    public int getNumber() {
        return number;
    }
    
    public double getPrice() {
        return price; }
    
    public boolean isBooked() {
        return isBooked; 
    }
    public void setBooked(boolean booked) {
        isBooked = booked; 
    }
    
    public String getDescription() {
        return description;
    }
    public List<Service> getServices() {
        return services;
    }
    
    public Hotel getHotel() {
        return hotel; 
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
  
   
}
