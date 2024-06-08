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

    private String name;
    private final List<Hotel> hotels = new ArrayList<>();

    public Location(String name) {
        this.name = name;
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
}
