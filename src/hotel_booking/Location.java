/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author shaikasif
 */
public class Location {

    private int locationId; //for primary key
    private String name;
    private final List<Hotel> hotels = new ArrayList<>();

    public Location(int locationId, String name) {
        this.locationId = locationId;
        this.name = name;
    }

    public int getLocationId() {
        return locationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    @Override
    public String toString() {
        return name;  //for further when we create the booking frame we need this to display
    }
}
//modifiled location