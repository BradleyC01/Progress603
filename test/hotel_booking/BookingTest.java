package hotel_booking;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BookingTest {

    private Booking booking;
    private Location location;
    private Hotel hotel;
    private Room room;
    private Service service;

    @Before
    public void setUp() {
        booking = new Booking();
        location = new Location(1, "Test Location");
        hotel = new Hotel(1, "Test Hotel", "Test Address", "Test Description", 100.0, "Test Image", 1);
        room = new Room("Test Room", 50.0, "Test Room Description", "Test Room Image");
        service = new Service("Test Service", 20.0);

        booking.setLocation(location);
        booking.setHotel(hotel);
        booking.setRoom(room);
    }

    @Test
    public void testGetAndSetBookingId() {
        booking.setBookingId(101);
        assertEquals(101, booking.getBookingId());
    }

    @Test
    public void testGetAndSetLocation() {
        assertEquals(location, booking.getLocation());
        Location newLocation = new Location(2, "New Location");
        booking.setLocation(newLocation);
        assertEquals(newLocation, booking.getLocation());
    }

    @Test
    public void testGetAndSetCheckInDate() {
        LocalDate checkInDate = LocalDate.of(2024, 6, 1);
        booking.setCheckInDate(checkInDate);
        assertEquals(checkInDate, booking.getCheckInDate());
    }

    @Test
    public void testGetAndSetCheckOutDate() {
        LocalDate checkOutDate = LocalDate.of(2024, 6, 5);
        booking.setCheckOutDate(checkOutDate);
        assertEquals(checkOutDate, booking.getCheckOutDate());
    }

    @Test
    public void testGetAndSetNights() {
        booking.setNights(4);
        assertEquals(4, booking.getNights());
    }

    @Test
    public void testGetAndSetAdults() {
        booking.setAdults(2);
        assertEquals(2, booking.getAdults());
    }

    @Test
    public void testGetAndSetChildren() {
        booking.setChildren(1);
        assertEquals(1, booking.getChildren());
    }

    @Test
    public void testGetAndSetHotel() {
        assertEquals(hotel, booking.getHotel());
        Hotel newHotel = new Hotel(2, "New Hotel", "New Address", "New Description", 200.0, "New Image", 2);
        booking.setHotel(newHotel);
        assertEquals(newHotel, booking.getHotel());
    }

    @Test
    public void testGetAndSetRoom() {
        assertEquals(room, booking.getRoom());
        Room newRoom = new Room("New Room", 100.0, "New Room Description", "New Room Image");
        booking.setRoom(newRoom);
        assertEquals(newRoom, booking.getRoom());
    }

    @Test
    public void testGetAndSetServices() {
        List<Service> services = new ArrayList<>();
        services.add(service);
        booking.setServices(services);
        assertEquals(services, booking.getServices());
    }

    @Test
    public void testGetAndSetCustomerName() {
        booking.setCustomerName("John Doe");
        assertEquals("John Doe", booking.getCustomerName());
    }

    @Test
    public void testGetAndSetDob() {
        booking.setDob("01/01/1980");
        assertEquals("01/01/1980", booking.getDob());
    }

    @Test
    public void testIsAndSetMarried() {
        booking.setMarried(true);
        assertTrue(booking.isMarried());
    }

    @Test
    public void testGetAndSetContactNumber() {
        booking.setContactNumber("1234567890");
        assertEquals("1234567890", booking.getContactNumber());
    }

    @Test
    public void testGetAndSetStatus() {
        booking.setStatus("Booked");
        assertEquals("Booked", booking.getStatus());
    }

    @Test
    public void testCalculateTax() {
        booking.setTotalServicePrice(100.0);
        assertEquals(2.0, booking.calculateTax(), 0.01);
    }

    @Test
    public void testCalculateTotalPriceWithTax() {
        booking.setTotalServicePrice(100.0);
        assertEquals(102.0, booking.calculateTotalPriceWithTax(), 0.01);
    }

    @Test
    public void testGetTotalRoomPrice() {
        booking.setNights(2);
        assertEquals(100.0, booking.getTotalRoomPrice(), 0.01);
    }

    @Test
    public void testGetServiceNames() {
        List<Service> services = new ArrayList<>();
        services.add(new Service("Service1", 50.0));
        services.add(new Service("Service2", 100.0));
        booking.setServices(services);
        assertEquals("Service1, Service2", booking.getServiceNames());
    }
}


