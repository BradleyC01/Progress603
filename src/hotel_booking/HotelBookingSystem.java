/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

/**
 *
 * @author shaikasif
 */
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HotelBookingSystem {

    private static final List<Location> locations = new ArrayList<>();
    private static final String FILE_PATH = "bookingDetails.txt";
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeLocations();
        welcome();
        mainLoop();
    }

    private static void welcome() {
        System.out.println("\n________________________________________________\n");
        System.out.println("     Welcome to the Hotel Booking System!      ");
      
    }

    private static void mainLoop() {
    boolean quit = false;
    while (!quit) {
        System.out.println("\n________________________________________________\n");
        System.out.println("\nPlease choose an option:");
        System.out.println("\n1. Retrieve Booking Details");
        System.out.println("2. New Booking");
        System.out.println("3. Exit");
        System.out.print("\nChoice: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    BookingDetails();
                    break;
                case 2:
                    NewBooking();
                    break;
                case 3:
                    quit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }
}


    //Case 1
    private static void BookingDetails() {
        System.out.print("Enter Booking Reference ID: ");
        String bookingId = scanner.nextLine();

        try {
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            boolean found = false;  

            for (String line : lines) {
                if (line.contains("Booking Reference ID: " + bookingId)) {
                    found = true;
                } else if (line.trim().isEmpty() && found) {
                    
                    break;
                }

                if (found) {
                    System.out.println(line);
                }
            }

            if (!found) {
                System.out.println("No booking found with ID: " + bookingId);
            }

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }



    //case 2 
    private static void NewBooking() {

        Location selectedLocation = selectLocation();
        System.out.println("\n________________________________________________________________________________________________");

        LocalDate checkInDate = getDate("check-in");
        System.out.println("\n________________________________________________________________________________________________");

        LocalDate checkOutDate = getDate("check-out", checkInDate);
        System.out.println("\n________________________________________________________________________________________________");

        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        System.out.println("\nNumber of nights: " + nights);

        System.out.println("\n________________________________________________________________________________________________");

        int adults = getNumber("adults");

        int children = getNumber("children");
        System.out.println("\n________________________________________________________________________________________________");

        Hotel selectedHotel = selectHotel(selectedLocation, nights, adults, children);
        System.out.println("\n________________________________________________________________________________________________\n");

        Room selectedRoom = selectRoom(selectedHotel);
        System.out.println("\n________________________________________________________________________________________________");

        Guest guest = enterGuestDetails();
        System.out.println("\n________________________________________________________________________________________________");

        List<Service> selectedServices = selectServices(selectedRoom);
        System.out.println("\n________________________________________________________________________________________________");

        double totalPriceWithTax = calculateAndDisplayBookingDetails(selectedRoom, nights, adults, children, selectedServices);
        System.out.println("\n________________________________________________________________________________________________");

        if (confirmBooking()) {
            finalizeBooking(guest, selectedRoom, selectedServices, checkInDate, checkOutDate, adults, children, totalPriceWithTax);
        } else {
            System.out.println("Booking cancelled.");
        }
    }
    
           //*************************************************** Method to Select Booking Location ***************************************************
    
    private static Location selectLocation() {
        System.out.println("\n________________________________________________\n");
        System.out.println("Available Locations:\n");
        
        for (int i = 0; i < locations.size(); i++) {
            System.out.println((i + 1) + ": " + locations.get(i).getName());
        }
        System.out.print("\nPlease select a location by number: ");
        int locationChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        if (locationChoice < 1 || locationChoice > locations.size()) {
            System.out.println("\nInvalid location. Please try again.");
            return selectLocation();
        }
        return locations.get(locationChoice - 1);

    }
    
           //************************************* Method to Write Check In Data And Check Out Date in format *************************************

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Used Chatgpt for format

    private static LocalDate getDate(String type) { //type is the promt for check check in check out 
        LocalDate date = null;

        System.out.print("\nEnter " + type + " Date (dd/MM/yyyy): ");
        
        
        while (date == null) {
            String input = scanner.nextLine();
            
            try {
                date = LocalDate.parse(input, dateFormatter);
                if (date.isBefore(LocalDate.now())) {
                    System.out.println("The " + type + "\ndate cannot be in the past. Please enter a valid date: ");
                    date = null; 
                }
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format. Please enter the date as dd/MM/yyyy: ");
            }
        }
        return date;
    }

    private static LocalDate getDate(String type, LocalDate checkInDate) {
        LocalDate date = null;
        
        System.out.print("\nEnter " + type + " Date (dd/MM/yyyy): ");
        
        while (date == null || date.isBefore(checkInDate)) {
            String input = scanner.nextLine();
            
            try {
                date = LocalDate.parse(input, dateFormatter);
                if (date.isBefore(checkInDate)) {
                    System.out.println("The " + type + " date must be after the check-in date. Please enter a valid date: ");
                    date = null; // reset the date
                }
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date format. Please enter the date as dd/MM/yyyy: ");
            }
        }
        return date;
    }
        
               //******************************************************************************************************************************************************

    private static int getNumber(String type) {
        int number = 0;

        System.out.print("\nEnter the number of " + type + ": ");
        
        while (!scanner.hasNextInt()) {
            System.out.println("\nThat's not a valid number. Please enter a valid number of " + type + ": ");
            scanner.next();
        }
        number = scanner.nextInt();
        scanner.nextLine();
        return number;
    }

    
    
           //*************************************************** Method to Select Hoteles In Choosen Location ***************************************************

    private static List<Hotel> getHotelsForLocation(Location location) {
        return location.getHotels();
    }
    
    private static Hotel selectHotel(Location location, long nights, int adults, int children) {
        System.out.println("\nAvailable Hotels in " + location.getName() + ":\n");
        List<Hotel> hotels = getHotelsForLocation(location);

        // Display all hotels with estimated costs
        for (int i = 0; i < hotels.size(); i++) {
            Hotel hotel = hotels.get(i);
            double baseCost = hotel.getPrice() * nights; 
            double AdultPrice = (adults - 1) * 11 * nights;  //11 dollor for extra adults each/
            double childrenPrice = children * 9 * nights;         // 9 dollor for each children
            
            double estimatedTotalCost = baseCost + AdultPrice + childrenPrice;
            System.out.printf("\n%d: %s - %s, Estimated Total Cost for stay: $%.2f\n", i + 1, hotel.getName(),
                    hotel.getDescription().replace("\n", " ").trim(), estimatedTotalCost );
    }

        System.out.println("\n________________________________________________\n");
        System.out.print("Please select a hotel by number: ");
        int hotelChoice = -1;
        
        while (hotelChoice < 1 || hotelChoice > hotels.size()) {
            if (scanner.hasNextInt()) {
                hotelChoice = scanner.nextInt();
                scanner.nextLine(); 
                if (hotelChoice < 1 || hotelChoice > hotels.size()) {
                    System.out.println("Invalid selection. Please select a number between 1 and " + hotels.size() + ".");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }

        return hotels.get(hotelChoice - 1);
    }
    
    
               //*************************************************** Mehtod To Select Room In Hotel ***************************************************
        
    private static Room selectRoom(Hotel hotel) {
        List<Room> hotelRooms = hotel.getRooms();

        System.out.println("\nAvailable Rooms:\n");
        for (Room room : hotelRooms) {
            System.out.println("Room Number: " + room.getNumber() + " - " + room.getDescription() + ", Price: $" + room.getPrice());
        }

        System.out.println("\n________________________________________________\n");
        System.out.print("Select a room by room number: ");

        while (true) {
            int roomNumber = scanner.nextInt();
            scanner.nextLine();
            Room selectedRoom = null;

            for (int i = 0; i < hotelRooms.size(); i++) {
                Room room = hotelRooms.get(i);
                if (room.getNumber() == roomNumber) {
                    selectedRoom = room;
                    selectedRoom.setHotel(hotel);
                    break;
                }
            }

            if (selectedRoom != null) {
                return selectedRoom;
            } else {
                System.out.println("Invalid room number. Please select another room.");
            }
        }
    }


      //*************************************************** Mehtod To Write Guest Details ***************************************************
    
    private static Guest enterGuestDetails() {
        String firstName, lastName, email, country, telephone;
        LocalDate dob = null;
        boolean validDate = false;

        System.out.print("\nEnter your first name: ");
        firstName = scanner.nextLine();
        System.out.print("Enter your last name: ");
        lastName = scanner.nextLine();

        
        do {
            System.out.print("Enter your date of birth (dd/MM/yyyy): ");
            String dobString = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                dob = LocalDate.parse(dobString, formatter);
                validDate = true; 
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please try again using dd/MM/yyyy format.");
            }
        } while (!validDate);

        
        System.out.print("Enter your email address: ");
        email = scanner.nextLine();
        System.out.print("Enter your country: ");
        country = scanner.nextLine();
        System.out.print("Enter your telephone number: ");
        telephone = scanner.nextLine();

        return new Guest(firstName, lastName, dob, email, country, telephone);
    }

    //*************************************************** Method to add services to bokking ***************************************************

 private static List<Service> selectServices(Room room) {
    List<Service> services = room.getServices();

    if (services.isEmpty()) {
        System.out.println("No additional services available for this room.");
        return Collections.emptyList();
    }

    System.out.println("\nAvailable Services:");
    for (int i = 0; i < services.size(); i++) {
        System.out.println((i + 1) + ". " + services.get(i).getName() + " - $" + services.get(i).getPrice());
    }

    System.out.println("\n________________________________________________\n");
    System.out.print("\nPlease enter the numbers of the services you want, separated by commas: ");

    String[] selectedIndices = scanner.nextLine().split(",");
    List<Service> selectedServices = new ArrayList<>();
    for (String index : selectedIndices) {
        try {
            int selectedIndex = Integer.parseInt(index.trim());
            if (selectedIndex >= 1 && selectedIndex <= services.size()) {
                selectedServices.add(services.get(selectedIndex - 1));
            } else {
                System.out.println("Service number " + selectedIndex + " is not valid.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input: " + index);
        }
    }
    return selectedServices;
}


           //*************************************************** Mehtod To calculate total price with Tax ***************************************************
    
    private static double calculateAndDisplayBookingDetails(Room selectedRoom, long nights, int adults, int children, List<Service> selectedServices) {

        //calculations for total Price with tax
        double roomPricePerNight = selectedRoom.getPrice();
        double totalRoomPrice = roomPricePerNight * nights;
        double additionalAdultCharge = (adults - 1) * 11 * nights;  //Extra Adults charge $ 11 Each
        double childrenCharge = children * 9 * nights;              //Children Charge $ 9 each
        double servicesTotalPrice = selectedServices.stream().mapToDouble(Service::getPrice).sum() * nights;
        double totalPriceBeforeTax = totalRoomPrice + additionalAdultCharge + childrenCharge + servicesTotalPrice;
        double taxAmount = totalPriceBeforeTax * 0.02;             //TAX 2% on Sum up
        double totalPriceWithTax = totalPriceBeforeTax + taxAmount;

        // Display the booking details
        System.out.println("\nBooking Details:              ");
        System.out.println("\nHotel:                         " + selectedRoom.getHotel().getName());
        System.out.println("Room Type:                     " + selectedRoom.getDescription());
        System.out.printf("Total Room Price (%d nights):   $%.2f\n", nights, totalRoomPrice); 
        System.out.println("Additional Adult Charge:       $" + String.format("%.2f", additionalAdultCharge));
        System.out.println("Children Charge:               $" + String.format("%.2f", childrenCharge));
        System.out.println("Services Charge:               $" + String.format("%.2f", servicesTotalPrice));
        System.out.println("Tax (2%):                      $" + String.format("%.2f", taxAmount));
        System.out.println("__________________________________________");
        System.out.println("Total Price with Tax:          $" + String.format("%.2f", totalPriceWithTax));
        System.out.println("__________________________________________");
        
         return totalPriceWithTax;
    }

           //************************************* Method to ask the user to congirm the booking and safe to file. *************************************
    
    private static boolean confirmBooking() {
        String input;
        
        do {
            System.out.print("\nConfirm booking (yes/no)? ");
            input = scanner.nextLine().trim().toLowerCase();
            switch (input) {
                case "yes":
                    return true;
                case "no":
                    System.out.println("Booking Cancelled , Your Details wont be save");
                    return false;
                default:
                    System.out.println("Please answer 'yes' or 'no'.");
                    break;
            }
        } while (!input.equals("yes") && !input.equals("no"));

        return false;
    }

          //************************************* Method to generate references id for current booking and safe to booking file*************************************   

   
    private static void finalizeBooking(Guest guest, Room selectedRoom, List<Service> selectedServices, LocalDate checkInDate, LocalDate checkOutDate, int adults, int children, double totalPriceWithTax) {
                                          int bookingId = FileIOUtility.loadLastBookingId() + 1;
        
        
        //1st timepending confirmation
        Booking booking = new Booking(bookingId, guest, selectedRoom, selectedServices, checkInDate, checkOutDate, adults, children, totalPriceWithTax, "Pending Confirmation");
        System.out.println(booking);

        //2 nd time confirmation
        boolean isConfirmed = confirmBooking();
        String finalStatus = isConfirmed ? "Confirmed" : "Cancelled";
        booking.setBookingStatus(finalStatus);

        System.out.println("Booking Reference ID: " + bookingId);
        System.out.println(booking);

        if (isConfirmed) {
            System.out.println("\n________________________________________________________________________________________________\n");
            System.out.println("                                   !!CONGRATULATIONS!!");
            System.out.println("                      THANK YOU FOR CHOOSING THIS HOTEL BOOKING SYSTEM");
            System.out.println("\n________________________________________________________________________________________________\n");
        } else {
            System.out.println("\n________________________________________________________________________________________________\n");
            System.out.println("                                   !!BOOKING CANCELLED!!");
            System.out.println("                      YOUR DETAILS ARE SAFE ,USE BOOKING REFRENCES ID ");
            System.out.println("\n________________________________________________________________________________________________\n");
        }

        FileIOUtility.writeBookingToFile(booking, "bookingDetails.txt");

        if (isConfirmed) {
            FileIOUtility.saveLastBookingId(bookingId);
        }
    }


              //************************************* Method adding locations abd addinf hotles and rooms data *************************************
    
    
    private static void initializeLocations() {

        Location auckland = new Location("Auckland");
        Location wellington = new Location("Wellington");
        Location christchursh = new Location("Christchursh");
        Location queenstown = new Location("Queenstown");
        Location rotorua = new Location("Rotorua");
        locations.add(auckland);
        locations.add(wellington);
        locations.add(christchursh);
        locations.add(queenstown);
        locations.add(rotorua);

        //*************************************************** AUCKLAND HOTELS ***************************************************
        
                     //******************************** HOTEL 1 ParkHyatt Auckland ********************************
        Hotel ParkHyattAuckland = new Hotel("Park Hyatt Auckland", "99 Halsey Street, Auckland CBD", auckland, "5 star hotel with 4.5 rating\n", 300);
        auckland.addHotel(ParkHyattAuckland);

                     //******************************** Rooms In ParkHyatt Auckland ********************************
        //Room 1             
        Room room1 = new Room(101, 300.00, "Deluxe Room with city view");
        room1.addService(new Service("WiFi", 0));
        room1.addService(new Service("Breakfast", 20));
        room1.addService(new Service("In-room espresso machine", 10));
        room1.addService(new Service("Insurances", 5));
        room1.addService(new Service("Lunch",22));
        
        
        ParkHyattAuckland.addRoom(room1);
        
        //Room 2
        Room room2 = new Room(102, 313, "Executive Suite");
        room2.addService(new Service("WiFi", 0));
        room2.addService(new Service("Breakfast", 20));
        room2.addService(new Service("Lunch", 22));
        room2.addService(new Service("Insurances",  4));
        room2.addService(new Service("business service", 30));
 

        ParkHyattAuckland.addRoom(room2);
        
        //Room 3
        
        Room room3 = new Room(103, 330, "Family Suite with Kitchenette");
        room3.addService(new Service("WiFi", 0));
        room3.addService(new Service("Breakfast", 12));
        room3.addService(new Service("Lunch", 15));
        room3.addService(new Service("Kitchen Service", 50));
        room3.addService(new Service("Children Activity Area", 6));
        room3.addService(new Service("Insurance", 3));

        ParkHyattAuckland.addRoom(room3);
        
        
        //Room 4
        
        Room room4 = new Room(104, 335, "Garden Terrace Room");
        room4.addService(new Service("WiFi", 0));
        room4.addService(new Service("Breakfast", 12));
        room4.addService(new Service("Lunch", 15));
        room4.addService(new Service("swiming Pool", 18));
        room4.addService(new Service("Terrace Gym", 25));
        room4.addService(new Service("Insurance", 7));

        ParkHyattAuckland.addRoom(room4);
        
        //Room 5
        
        Room room5 = new Room(105, 335, "Spa Room");
        room5.addService(new Service("WiFi", 0));
        room5.addService(new Service("Breakfast", 12));
        room5.addService(new Service("Lunch", 15));
        room5.addService(new Service("swiming Pool", 18));
        room5.addService(new Service("Spa", 2));
        room5.addService(new Service("Massagge Complete", 50));
        room5.addService(new Service("Insurance", 5));

        ParkHyattAuckland.addRoom(room5);

       
                    //******************************** HOTEL 2 Holto Auckland ********************************       
        Hotel HiltoAuckland = new Hotel("Hilton Auckland", "147 Quay Street, Princes Wharf, Auckland CBD, Auckland 1010", auckland, "5 star hotel with 4.4 rating\n", 312);
        auckland.addHotel(HiltoAuckland);

                     //******************************** Rooms In Holto Auckland ********************************
        //Room 1             
        Room room21 = new Room(201, 225, "Sharing Room");
        room21.addService(new Service("WiFi", 5));
        room21.addService(new Service("Breakfast", 5));
        room21.addService(new Service("Insurances", 1));
        room21.addService(new Service("Lunch",7));
        
        
        HiltoAuckland.addRoom(room21);
        
        //Room 2
        Room room22 = new Room(202, 255, "Single Room");
        room22.addService(new Service("WiFi", 0));
        room22.addService(new Service("Breakfast", 6));
        room22.addService(new Service("Lunch", 10));
        room22.addService(new Service("Insurances",  2));
 

        HiltoAuckland.addRoom(room22);
        
        //Room 3
        
        Room room23 = new Room(203, 260, "Family Room");
        room23.addService(new Service("WiFi", 0));
        room23.addService(new Service("Breakfast", 5));
        room23.addService(new Service("Lunch", 9));
        room23.addService(new Service("Kitchen Service", 35));
        room23.addService(new Service("Children Activity Area", 6));
        room23.addService(new Service("Insurance", 3));

        HiltoAuckland.addRoom(room23);
        
        
        //Room 4
        
        Room room24 = new Room(204, 265, "Forest veiw Room");
        room24.addService(new Service("WiFi", 0));
        room24.addService(new Service("Breakfast", 5));
        room24.addService(new Service("Lunch", 9));
        room24.addService(new Service("Garden Launch", 12));
        room24.addService(new Service("Insurance", 2));

         HiltoAuckland.addRoom(room24);
        
        //Room 5
        
        Room room25 = new Room(205, 300, "Spa Room");
        room25.addService(new Service("WiFi", 0));
        room25.addService(new Service("Breakfast", 5));
        room25.addService(new Service("Lunch", 9));
        room25.addService(new Service("swiming Pool", 10));
        room25.addService(new Service("Spa", 5));
        room25.addService(new Service("Massagge Complete", 45));
        room25.addService(new Service("Insurance", 5));

         HiltoAuckland.addRoom(room25);
                    //******************************** HOTEL 3 The Hotel Britomart ********************************     
         Hotel Britomart = new Hotel("The Hotel Britomart", "Ayckland central Business District", auckland, "5 star hotel with 4.4 rating\n", 305);       
         auckland.addHotel(Britomart);   

                     //******************************** Rooms In The Hotel Britomart ********************************
        
        //Room 1             
        Room room31 = new Room(301, 295, "Sharing Room");
        room31.addService(new Service("WiFi", 5));
        room31.addService(new Service("Breakfast", 5));
        room31.addService(new Service("Insurances", 1));
        room31.addService(new Service("Lunch",7));
        
        
        Britomart.addRoom(room31);
        
        //Room 2             
        Room room32 = new Room(302, 298, "Queen Size Room");
        room32.addService(new Service("WiFi", 5));
        room32.addService(new Service("Breakfast", 12));
        room32.addService(new Service("Insurances", 4));
        room32.addService(new Service("Lunch",15));
        
        
        Britomart.addRoom(room32);
        
        //Room 3             
        Room room33 = new Room(303, 305, "Family Room");
        room33.addService(new Service("WiFi", 5));
        room33.addService(new Service("Breakfast", 12));
        room33.addService(new Service("Insurances", 4));
        room33.addService(new Service("Lunch",15));
        room33.addService(new Service("Extra Bed",17));
        
        
        Britomart.addRoom(room33);
        
        
                   //******************************** HOTEL 4 M Social Auckland ********************************     
         Hotel MSocial = new Hotel("M Social Auckland", "Iconic Quat Street, M Social offers", auckland, "5 star hotel with 4.1 rating\n", 234);       
         auckland.addHotel(MSocial);   

                     //******************************** Rooms In The Hotel Britomart ********************************
        
        //Room 1             
        Room room41 = new Room(401, 225, "Sharing Room");
        room41.addService(new Service("WiFi", 5));
        room41.addService(new Service("Breakfast", 5));
        room41.addService(new Service("Insurances", 1));
        room41.addService(new Service("Lunch",7));
        
        
        MSocial.addRoom(room41);
        
        //Room 2             
        Room room42 = new Room(402, 234, "Queen Size Room");
        room42.addService(new Service("WiFi", 5));
        room42.addService(new Service("Breakfast", 6));
        room42.addService(new Service("Insurances", 3));
        room42.addService(new Service("Lunch",8));
        
        
        MSocial.addRoom(room42);
        
        //Room 3             
        Room room43 = new Room(403, 240, "Family Room");
        room43.addService(new Service("WiFi", 5));
        room43.addService(new Service("Breakfast", 6));
        room43.addService(new Service("Insurances", 8));
        room43.addService(new Service("Lunch",9));
        room43.addService(new Service("Extra Bed",13));
        
        
        MSocial.addRoom(room43);
        
        //Room 4
        
        Room room44 = new Room(404, 265, "Studio");
        room44.addService(new Service("WiFi", 0));
        room44.addService(new Service("Breakfast", 5));
        room44.addService(new Service("Lunch", 9));
        room44.addService(new Service("Full Acces", 35));
        room44.addService(new Service("Insurance", 2));

         MSocial.addRoom(room44);
                    
       
         
        //*************************************************** Wellington HOTELS ***************************************************
        
                     //******************************** HOTEL 1 InterContinental  ********************************
        Hotel InterContinental = new Hotel("InterContinental Wellington, an IHG Hotel", "2 Grey Street, Wellington Central, Wellington 6011", wellington, "4.5 rating,", 300);
        wellington.addHotel(InterContinental);
                     //******************************** Rooms In InterContinental  ********************************
        
        Room room211 = new Room(221, 350, "Executive Suite");
        room211.addService(new Service("WiFi", 0));
        room211.addService(new Service("Breakfast", 20));
        room211.addService(new Service("Gym Access", 15));
        room211.addService(new Service("Spa Access", 40));
        InterContinental.addRoom(room211);

        Room room12 = new Room(222, 300, "Deluxe Room");
        room12.addService(new Service("WiFi", 0));
        room12.addService(new Service("Breakfast", 20));
        room12.addService(new Service("Gym Access", 15));
        InterContinental.addRoom(room12);
       
                     //******************************** HOTEL 2 JamesCookHotel  ********************************
        Hotel JamesCookHotel = new Hotel("James Cook Hotel Grand Chancellor", "147 The Terrace, Wellington Central, Wellington 6011", wellington, "Comfortable, contemporary rooms with direct access to shopping precinct.", 250);
        wellington.addHotel(JamesCookHotel);
                     
                     //******************************** Rooms In JamesCookHotel  ********************************
        Room room221 = new Room(223, 240, "Standard Room");
        room221.addService(new Service("WiFi", 0));
        room221.addService(new Service("Breakfast", 18));
        room221.addService(new Service("Dinner Option", 30));
        JamesCookHotel.addRoom(room221);

        Room room222 = new Room(224, 260, "Superior Room");
        room222.addService(new Service("WiFi", 0));
        room222.addService(new Service("Breakfast", 18));
        room222.addService(new Service("Dinner Option", 30));
        room222.addService(new Service("Extra Bed", 50));
        JamesCookHotel.addRoom(room222);
                           
                     //******************************** HOTEL 3 QTWellington  ********************************
        Hotel QTWellington = new Hotel("QT Wellington", "90 Cable Street, Te Aro, Wellington 6011",wellington,"Eclectic art and hip atmosphere with uniquely designed rooms.",280);
        wellington.addHotel(QTWellington);
                     
                     //******************************** Rooms In QTWellington  ********************************
        Room room233 = new Room(225, 270, "Art Room");
        room233.addService(new Service("WiFi", 0));
        room233.addService(new Service("Breakfast", 22));
        room233.addService(new Service("Workshop Entry", 45));
        QTWellington.addRoom(room233);

        Room room244 = new Room(226, 290, "Harbor View Room");
        room244.addService(new Service("WiFi", 0));
        room244.addService(new Service("Breakfast", 22));
        room244.addService(new Service("Private Balcony Access", 70));
        QTWellington.addRoom(room244);
                     
                     //******************************** HOTEL 4 RydgesWellington  ********************************
        Hotel RydgesWellington = new Hotel("Rydges Wellington", "75 Featherston Street, Wellington Central, Wellington 6011", wellington, "Downtown location near the waterfront with spectacular views.", 240);
        wellington.addHotel(RydgesWellington);
                     
                     //******************************** Rooms In RydgesWellington  ********************************
        Room room255 = new Room(227, 220, "City View Room");
        room255.addService(new Service("WiFi", 0));
        room255.addService(new Service("Breakfast", 20));
        room255.addService(new Service("Fitness Center", 0));
        RydgesWellington.addRoom(room255);

        Room room266 = new Room(228, 250, "Luxury Suite");
        room266.addService(new Service("WiFi", 0));
        room266.addService(new Service("Breakfast", 20));
        room266.addService(new Service("Fitness Center", 0));
        room266.addService(new Service("VIP Lounge Access", 80));
        RydgesWellington.addRoom(room266);             
        
        //*************************************************** Christchurch HOTELS ***************************************************

                     //******************************** HOTEL 1 The George Christchurch ********************************
        Hotel TheGeorgeChristchurch = new Hotel("The George", "50 Park Terrace, Christchurch Central City, Christchurch 8013", christchursh, "Luxury boutique hotel with personalized service", 320);
        christchursh.addHotel(TheGeorgeChristchurch);

                     //******************************** Rooms In The George Christchurch ********************************
        Room georgeRoom1 = new Room(401, 330, "Park Suite");
        georgeRoom1.addService(new Service("WiFi", 0));
        georgeRoom1.addService(new Service("Breakfast", 25));
        georgeRoom1.addService(new Service("Private Balcony Access", 30));
        georgeRoom1.addService(new Service("Daily Newspaper", 5));
        TheGeorgeChristchurch.addRoom(georgeRoom1);

        Room georgeRoom2 = new Room(402, 310, "Executive Room");
        georgeRoom2.addService(new Service("WiFi", 0));
        georgeRoom2.addService(new Service("Breakfast", 25));
        georgeRoom2.addService(new Service("Gym Access", 10));
        TheGeorgeChristchurch.addRoom(georgeRoom2);

        Room georgeRoom3 = new Room(403, 295, "Standard Room");
        georgeRoom3.addService(new Service("WiFi", 0));
        georgeRoom3.addService(new Service("Breakfast", 25));
        TheGeorgeChristchurch.addRoom(georgeRoom3);

        Room georgeRoom4 = new Room(404, 350, "Junior Suite");
        georgeRoom4.addService(new Service("WiFi", 0));
        georgeRoom4.addService(new Service("Breakfast", 25));
        georgeRoom4.addService(new Service("Evening Turndown Service", 15));
        TheGeorgeChristchurch.addRoom(georgeRoom4);
        
       
                     //******************************** HOTEL 2 Heritage Christchurch ********************************
        Hotel HeritageChristchurch = new Hotel("Heritage Christchurch", "28-30 Cathedral Square, Christchurch Central City, Christchurch 8011", christchursh, "Historic hotel in a restored building from 1913", 315);
        christchursh.addHotel(HeritageChristchurch);

                     //******************************** Rooms In Heritage Christchurch ********************************
        Room heritageRoom1 = new Room(501, 325, "Heritage Suite");
        heritageRoom1.addService(new Service("WiFi", 0));
        heritageRoom1.addService(new Service("Breakfast", 30));
        heritageRoom1.addService(new Service("Evening Cocktails", 20));
        HeritageChristchurch.addRoom(heritageRoom1);

        Room heritageRoom2 = new Room(502, 300, "Deluxe King Room");
        heritageRoom2.addService(new Service("WiFi", 0));
        heritageRoom2.addService(new Service("Breakfast", 30));
        HeritageChristchurch.addRoom(heritageRoom2);

        Room heritageRoom3 = new Room(503, 280, "Superior Room");
        heritageRoom3.addService(new Service("WiFi", 0));
        heritageRoom3.addService(new Service("Breakfast", 30));
        HeritageChristchurch.addRoom(heritageRoom3);

        Room heritageRoom4 = new Room(504, 340, "Penthouse Suite");
        heritageRoom4.addService(new Service("WiFi", 0));
        heritageRoom4.addService(new Service("Breakfast", 30));
        heritageRoom4.addService(new Service("Private Terrace Access", 40));
        HeritageChristchurch.addRoom(heritageRoom4);

                     //******************************** HOTEL 3 Rendezvous Hotel Christchurch ********************************
        Hotel RendezvousHotelChristchurch = new Hotel("Rendezvous Hotel Christchurch", "166 Gloucester Street, Christchurch Central City, Christchurch 8011", christchursh, "Stylish high-rise hotel with panoramic city views", 295);
        christchursh.addHotel(RendezvousHotelChristchurch);

                     //******************************** Rooms In Rendezvous Hotel Christchurch ********************************
        Room rendezvousRoom1 = new Room(601, 290, "Guest Room");
        rendezvousRoom1.addService(new Service("WiFi", 0));
        rendezvousRoom1.addService(new Service("Breakfast", 25));
        RendezvousHotelChristchurch.addRoom(rendezvousRoom1);

        Room rendezvousRoom2 = new Room(602, 310, "Deluxe Room");
        rendezvousRoom2.addService(new Service("WiFi", 0));
        rendezvousRoom2.addService(new Service("Breakfast", 25));
        rendezvousRoom2.addService(new Service("City View", 15));
        RendezvousHotelChristchurch.addRoom(rendezvousRoom2);

        Room rendezvousRoom3 = new Room(603, 330, "Executive Suite");
        rendezvousRoom3.addService(new Service("WiFi", 0));
        rendezvousRoom3.addService(new Service("Breakfast", 25));
        rendezvousRoom3.addService(new Service("Exclusive Lounge Access", 25));
        RendezvousHotelChristchurch.addRoom(rendezvousRoom3);

        Room rendezvousRoom4 = new Room(604, 350, "Presidential Suite");
        rendezvousRoom4.addService(new Service("WiFi", 0));
        rendezvousRoom4.addService(new Service("Breakfast", 25));
        rendezvousRoom4.addService(new Service("Full Bar Access", 50));
        RendezvousHotelChristchurch.addRoom(rendezvousRoom4);

                     //******************************** HOTEL 4 Novotel Christchurch Cathedral Square Hotel ********************************
        Hotel NovotelChristchurch = new Hotel("Novotel Christchurch Cathedral Square Hotel", "52 Cathedral Square, Christchurch Central City, Christchurch 8011", christchursh, "Modern hotel with sleek accommodations", 305);
        christchursh.addHotel(NovotelChristchurch);

                     //******************************** Rooms In Novotel Christchurch ********************************
        Room novotelRoom1 = new Room(701, 285, "Standard Room");
        novotelRoom1.addService(new Service("WiFi", 0));
        novotelRoom1.addService(new Service("Breakfast", 20));
        NovotelChristchurch.addRoom(novotelRoom1);

        Room novotelRoom2 = new Room(702, 305, "Superior Room");
        novotelRoom2.addService(new Service("WiFi", 0));
        novotelRoom2.addService(new Service("Breakfast", 20));
        NovotelChristchurch.addRoom(novotelRoom2);

        Room novotelRoom3 = new Room(703, 325, "Executive Room");
        novotelRoom3.addService(new Service("WiFi", 0));
        novotelRoom3.addService(new Service("Breakfast", 20));
        novotelRoom3.addService(new Service("Nespresso Machine", 10));
        NovotelChristchurch.addRoom(novotelRoom3);

        Room novotelRoom4 = new Room(704, 345, "Suite");
        novotelRoom4.addService(new Service("WiFi", 0));
        novotelRoom4.addService(new Service("Breakfast", 20));
        novotelRoom4.addService(new Service("Separate Living Area", 30));
        NovotelChristchurch.addRoom(novotelRoom4);


        //*************************************************** Queenstown HOTELS ***************************************************

                     //******************************** HOTEL 1 Kamana Lakehouse ********************************
        Hotel KamanaLakehouse = new Hotel("Kamana Lakehouse", "139 Fernhill Road, Fernhill, Queenstown 9300", queenstown, "High-end hotel with stunning lake views", 285);
        queenstown.addHotel(KamanaLakehouse);

                     //******************************** Rooms In Kamana Lakehouse ********************************

        Room kamanaRoom1 = new Room(501, 300, "Lakeview Suite");
        kamanaRoom1.addService(new Service("WiFi", 0));
        kamanaRoom1.addService(new Service("Breakfast", 30));
        kamanaRoom1.addService(new Service("Hot Tub Access", 40));
        KamanaLakehouse.addRoom(kamanaRoom1);

        Room kamanaRoom2 = new Room(502, 270, "Standard Room");
        kamanaRoom2.addService(new Service("WiFi", 0));
        kamanaRoom2.addService(new Service("Breakfast", 30));
        KamanaLakehouse.addRoom(kamanaRoom2);

        Room kamanaRoom3 = new Room(503, 320, "Executive Room with View");
        kamanaRoom3.addService(new Service("WiFi", 0));
        kamanaRoom3.addService(new Service("Breakfast", 30));
        kamanaRoom3.addService(new Service("Private Balcony", 25));
        KamanaLakehouse.addRoom(kamanaRoom3);

        Room kamanaRoom4 = new Room(504, 340, "Family Room");
        kamanaRoom4.addService(new Service("WiFi", 0));
        kamanaRoom4.addService(new Service("Breakfast", 30));
        kamanaRoom4.addService(new Service("Board Games and Books", 10));
        KamanaLakehouse.addRoom(kamanaRoom4);
        
        
        //******************************** HOTEL 2 The Rees Hotel & Luxury Apartments ********************************
        Hotel TheReesHotel = new Hotel("The Rees Hotel & Luxury Apartments", "377 Frankton Road, Queenstown 9300", queenstown, "Sophisticated hotel with lakeside apartments and superior service", 310);
        queenstown.addHotel(TheReesHotel);

        //******************************** Rooms In The Rees Hotel ********************************
        Room reesRoom1 = new Room(601, 330, "Lakeside Apartment");
        reesRoom1.addService(new Service("WiFi", 0));
        reesRoom1.addService(new Service("Breakfast", 35));
        reesRoom1.addService(new Service("Private Jetty Access", 50));
        TheReesHotel.addRoom(reesRoom1);

        Room reesRoom2 = new Room(602, 350, "Hotel Suite");
        reesRoom2.addService(new Service("WiFi", 0));
        reesRoom2.addService(new Service("Breakfast", 35));
        reesRoom2.addService(new Service("Spa Facility Access", 40));
        TheReesHotel.addRoom(reesRoom2);

        Room reesRoom3 = new Room(603, 315, "Executive Room");
        reesRoom3.addService(new Service("WiFi", 0));
        reesRoom3.addService(new Service("Breakfast", 35));
        TheReesHotel.addRoom(reesRoom3);

        Room reesRoom4 = new Room(604, 290, "Standard Room");
        reesRoom4.addService(new Service("WiFi", 0));
        reesRoom4.addService(new Service("Breakfast", 35));
        TheReesHotel.addRoom(reesRoom4);

        //******************************** HOTEL 3 Sofitel Queenstown Hotel and Spa ********************************
        Hotel SofitelQueenstown = new Hotel("Sofitel Queenstown Hotel and Spa", "8 Duke Street, Queenstown 9300", queenstown, "French-inspired luxury hotel with exquisite design and spa", 320);
        queenstown.addHotel(SofitelQueenstown);

        //******************************** Rooms In Sofitel Queenstown ********************************
        Room sofitelRoom1 = new Room(701, 340, "Prestige Suite");
        sofitelRoom1.addService(new Service("WiFi", 0));
        sofitelRoom1.addService(new Service("Breakfast", 40));
        sofitelRoom1.addService(new Service("Spa Access", 45));
        SofitelQueenstown.addRoom(sofitelRoom1);

        Room sofitelRoom2 = new Room(702, 360, "Luxury Room");
        sofitelRoom2.addService(new Service("WiFi", 0));
        sofitelRoom2.addService(new Service("Breakfast", 40));
        sofitelRoom2.addService(new Service("Evening Turndown Service", 15));
        SofitelQueenstown.addRoom(sofitelRoom2);

        Room sofitelRoom3 = new Room(703, 300, "Superior Room");
        sofitelRoom3.addService(new Service("WiFi", 0));
        sofitelRoom3.addService(new Service("Breakfast", 40));
        SofitelQueenstown.addRoom(sofitelRoom3);

        Room sofitelRoom4 = new Room(704, 285, "Classic Room");
        sofitelRoom4.addService(new Service("WiFi", 0));
        sofitelRoom4.addService(new Service("Breakfast", 40));
        SofitelQueenstown.addRoom(sofitelRoom4);

        //******************************** HOTEL 4 Millennium Hotel Queenstown ********************************
        Hotel MillenniumHotel = new Hotel("Millennium Hotel Queenstown", "32 Frankton Road, Queenstown 9300", queenstown, "Modern hotel with comfortable rooms and quality amenities", 295);
        queenstown.addHotel(MillenniumHotel);

        //******************************** Rooms In Millennium Hotel ********************************
        Room millenniumRoom1 = new Room(801, 310, "Business Suite");
        millenniumRoom1.addService(new Service("WiFi", 0));
        millenniumRoom1.addService(new Service("Breakfast", 30));
        millenniumRoom1.addService(new Service("Conference Room Access", 60));
        MillenniumHotel.addRoom(millenniumRoom1);

        Room millenniumRoom2 = new Room(802, 280, "Deluxe Room");
        millenniumRoom2.addService(new Service("WiFi", 0));
        millenniumRoom2.addService(new Service("Breakfast", 30));
        MillenniumHotel.addRoom(millenniumRoom2);

        Room millenniumRoom3 = new Room(803, 270, "Double Room");
        millenniumRoom3.addService(new Service("WiFi", 0));
        millenniumRoom3.addService(new Service("Breakfast", 30));
        MillenniumHotel.addRoom(millenniumRoom3);

        Room millenniumRoom4 = new Room(804, 260, "Standard Room");
        millenniumRoom4.addService(new Service("WiFi", 0));
        millenniumRoom4.addService(new Service("Breakfast", 30));
        MillenniumHotel.addRoom(millenniumRoom4);


        //*************************************************** Rotorua HOTELS ***************************************************

                     //******************************** HOTEL 1 Pullman Rotorua ********************************
        Hotel PullmanRotorua = new Hotel("Pullman Rotorua", "1135 Arawa Street, Rotorua 3010", rotorua, "Modern hotel with geothermal baths", 295);
        rotorua.addHotel(PullmanRotorua);

                     //******************************** Rooms In Pullman Rotorua ********************************

        Room pullmanRoom1 = new Room(601, 310, "Deluxe Room");
        pullmanRoom1.addService(new Service("WiFi", 0));
        pullmanRoom1.addService(new Service("Breakfast", 28));
        pullmanRoom1.addService(new Service("Spa Access", 20));
        PullmanRotorua.addRoom(pullmanRoom1);

        Room pullmanRoom2 = new Room(602, 290, "Superior Room");
        pullmanRoom2.addService(new Service("WiFi", 0));
        pullmanRoom2.addService(new Service("Breakfast", 28));
        PullmanRotorua.addRoom(pullmanRoom2);

        Room pullmanRoom3 = new Room(603, 275, "Standard Room");
        pullmanRoom3.addService(new Service("WiFi", 0));
        pullmanRoom3.addService(new Service("Breakfast", 28));
        PullmanRotorua.addRoom(pullmanRoom3);

        Room pullmanRoom4 = new Room(604, 330, "Suite");
        pullmanRoom4.addService(new Service("WiFi", 0));
        pullmanRoom4.addService(new Service("Breakfast", 28));
        pullmanRoom4.addService(new Service("Private Hot Spring Access", 50));
        PullmanRotorua.addRoom(pullmanRoom4);           
    
    
                     //******************************** HOTEL 2 Novotel Rotorua Lakeside ********************************
        Hotel NovotelRotoruaLakeside = new Hotel("Novotel Rotorua Lakeside", "Lake End Tutanekai Street, Rotorua 3010", rotorua, "Lakeside accommodation with cultural experiences", 280);
        rotorua.addHotel(NovotelRotoruaLakeside);

                     //******************************** Rooms In Novotel Rotorua Lakeside ********************************
        Room novotelRoom5 = new Room(701, 260, "City View Room");
        novotelRoom5.addService(new Service("WiFi", 0));
        novotelRoom5.addService(new Service("Breakfast", 25));
        NovotelRotoruaLakeside.addRoom(novotelRoom5);

        Room novotelRoom6 = new Room(702, 280, "Lake View Room");
        novotelRoom6.addService(new Service("WiFi", 0));
        novotelRoom6.addService(new Service("Breakfast", 25));
        novotelRoom6.addService(new Service("Cultural Show Entry", 30));
        NovotelRotoruaLakeside.addRoom(novotelRoom6);

        Room novotelRoom7 = new Room(703, 300, "Executive Room");
        novotelRoom7.addService(new Service("WiFi", 0));
        novotelRoom7.addService(new Service("Breakfast", 25));
        novotelRoom7.addService(new Service("Business Lounge Access", 35));
        NovotelRotoruaLakeside.addRoom(novotelRoom7);

        Room novotelRoom8 = new Room(704, 320, "Suite with Spa Bath");
        novotelRoom8.addService(new Service("WiFi", 0));
        novotelRoom8.addService(new Service("Breakfast", 25));
        novotelRoom8.addService(new Service("Private Spa Bath", 40));
        NovotelRotoruaLakeside.addRoom(novotelRoom8);

                     //******************************** HOTEL 3 Holiday Inn Rotorua ********************************
        Hotel HolidayInnRotorua = new Hotel("Holiday Inn Rotorua", "10 Tryon Street, Whakarewarewa, Rotorua 3010", rotorua, "Family-friendly hotel with thermal pool", 270);
        rotorua.addHotel(HolidayInnRotorua);

                     //******************************** Rooms In Holiday Inn Rotorua ********************************
        Room holidayRoom1 = new Room(801, 250, "Standard Room");
        holidayRoom1.addService(new Service("WiFi", 0));
        holidayRoom1.addService(new Service("Breakfast", 20));
        HolidayInnRotorua.addRoom(holidayRoom1);

        Room holidayRoom2 = new Room(802, 275, "Deluxe Room");
        holidayRoom2.addService(new Service("WiFi", 0));
        holidayRoom2.addService(new Service("Breakfast", 20));
        holidayRoom2.addService(new Service("Thermal Pool Access", 15));
        HolidayInnRotorua.addRoom(holidayRoom2);

        Room holidayRoom3 = new Room(803, 295, "Family Suite");
        holidayRoom3.addService(new Service("WiFi", 0));
        holidayRoom3.addService(new Service("Breakfast", 20));
        holidayRoom3.addService(new Service("Game Room Access", 10));
        HolidayInnRotorua.addRoom(holidayRoom3);

        Room holidayRoom4 = new Room(804, 310, "Executive Suite");
        holidayRoom4.addService(new Service("WiFi", 0));
        holidayRoom4.addService(new Service("Breakfast", 20));
        holidayRoom4.addService(new Service("Exclusive Lounge Access", 25));
        HolidayInnRotorua.addRoom(holidayRoom4);

                     //******************************** HOTEL 4 Prince's Gate Hotel ********************************
        Hotel PrincesGateHotel = new Hotel("Prince's Gate Hotel", "1057 Arawa Street, Rotorua 3010", rotorua, "Historic hotel with a unique charm", 290);
        rotorua.addHotel(PrincesGateHotel);

                     //******************************** Rooms In Prince's Gate Hotel ********************************
        Room princesRoom1 = new Room(901, 275, "Vintage Room");
        princesRoom1.addService(new Service("WiFi", 0));
        princesRoom1.addService(new Service("Breakfast", 22));
        PrincesGateHotel.addRoom(princesRoom1);

        Room princesRoom2 = new Room(902, 300, "Heritage Room");
        princesRoom2.addService(new Service("WiFi", 0));
        princesRoom2.addService(new Service("Breakfast", 22));
        princesRoom2.addService(new Service("Heritage Tour", 20));
        PrincesGateHotel.addRoom(princesRoom2);

        Room princesRoom3 = new Room(903, 325, "Suite with Courtyard");
        princesRoom3.addService(new Service("WiFi", 0));
        princesRoom3.addService(new Service("Breakfast", 22));
        princesRoom3.addService(new Service("Private Courtyard Access", 30));
        PrincesGateHotel.addRoom(princesRoom3);

        Room princesRoom4 = new Room(904, 350, "Royal Suite");
        princesRoom4.addService(new Service("WiFi", 0));
        princesRoom4.addService(new Service("Breakfast", 22));
        princesRoom4.addService(new Service("Full Butler Service", 50));
        PrincesGateHotel.addRoom(princesRoom4);

    }

}
