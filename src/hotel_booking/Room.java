/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;
/**
 *
 * @author shaikasif
 */
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private List<Service> services;

    public Room(String name, double price, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.services = new ArrayList<>();
    }

    // Add getters and setters
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public List<Service> getServices() {
        return services;
    }

    public void addService(Service service) {
        services.add(service);
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
}

//modifled the room class