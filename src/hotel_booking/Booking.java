package hotel_booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// abstarcts of booking frames implemted
public class Booking implements BookingOperations {

    private static int lastBookingId = FileIOUtility.loadLastBookingId(); // to get the last booking number & need to update file class
    private int bookingId;
    private Location location;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Hotel hotel;
    private Room room;
    private List<Service> services;
    private String customerName;
    private String dob;
    private boolean married;
    private String contactNumber;
    private long nights;
    private int adults;
    private int children;
    private String status;
    private double totalServicePrice;

    public Booking() {
        this.bookingId = ++lastBookingId;
        this.services = new ArrayList<>();
    }

    @Override
    public int getBookingId() {
        return bookingId;
    }

    @Override
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    @Override
    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    @Override
    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    @Override
    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    @Override
    public long getNights() {
        return nights;
    }

    @Override
    public void setNights(long nights) {
        this.nights = nights;
    }

    @Override
    public int getAdults() {
        return adults;
    }

    @Override
    public void setAdults(int adults) {
        this.adults = adults;
    }

    @Override
    public int getChildren() {
        return children;
    }

    @Override
    public void setChildren(int children) {
        this.children = children;
    }

    @Override
    public Hotel getHotel() {
        return hotel;
    }

    @Override
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    @Override
    public Room getRoom() {
        return room;
    }

    @Override
    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public List<Service> getServices() {
        return services;
    }

    @Override
    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String getCustomerName() {
        return customerName;
    }

    @Override
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    @Override
    public String getDob() {
        return dob;
    }

    @Override
    public void setDob(String dob) {
        this.dob = dob;
    }

    @Override
    public boolean isMarried() {
        return married;
    }

    @Override
    public void setMarried(boolean married) {
        this.married = married;
    }

    @Override
    public String getContactNumber() {
        return contactNumber;
    }

    @Override
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public double getTotalServicePrice() {
        return totalServicePrice;
    }

    @Override
    public void updateTotalServicePrice() {
        totalServicePrice = services.stream().mapToDouble(Service::getPrice).sum();
    }

    @Override
    public double calculateTax() {
        return getTotalServicePrice() * 0.02;
    }

    @Override
    public double calculateTotalPriceWithTax() {
        return getTotalServicePrice() * 1.02;
    }

    @Override
    public double calculateTotalRoomPrice(double basePrice, double additionalPrice, long nights) {
        return (basePrice + additionalPrice) * nights;
    }

    @Override
    public void setTotalServicePrice(double totalServicePrice) {
        this.totalServicePrice = totalServicePrice;
    }

    @Override
    public double getTotalRoomPrice() {
        return room.getPrice() * nights;
    }

    @Override
    public String getServiceNames() {
        return services.stream().map(Service::getName).collect(Collectors.joining(", "));
    }
}
