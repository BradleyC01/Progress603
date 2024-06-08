package hotel_booking;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private final String name;
    private final String address;
    private final String description;
    private final Location location;
    private final List<Room> rooms;
    float price;

    public Hotel(String name, String address, Location location, String description, float price) {
        this.name = name;
        this.address = address;
        this.description = description;
        this.location = location;
        this.price = price;
        this.rooms = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Location getLocation() {
        return location;
    }

    public void setPrice(float price) {
        this.price = price;
    }

}
