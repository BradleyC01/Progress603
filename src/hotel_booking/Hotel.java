/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;
/**
 *
 * @author shaikasif
 */
public class Hotel {
    private int hotelId; //primary key.
    private String name;
    private String address;
    private String description;
    private double price;
    private int locationId;//locations will be get based on the location foregin key
    private String imageUrl;

    public Hotel(int hotelId, String name, String address, String description, double price, String imageUrl, int locationId) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.locationId = locationId;
    }

    // Add getters and setters as needed

    public int getHotelId() {
        return hotelId;
    }

    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

//modifiled the hotel class