/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;
/**
 *
 * @author shaikasif
 */
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class BookingFrame extends JFrame {

    private  List<Location> locations;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Booking currentBooking;
    private Location selectedLocation;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adults;
    private int children;
    private long nights;

    public BookingFrame(List<Location> locations) {
        this.locations = locations;
        setTitle("Hotel Booking System");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Main container with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        setContentPane(mainPanel);

        // Load the background image from the images directory 
        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        // Create the background panel
        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(1000, 400));
        backgroundPanel.setLayout(new GridBagLayout());

        // Create the welcome message label
        JLabel welcomeLabel = new JLabel("WELCOME HOTEL BOOKING SYSTEM", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 50));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setOpaque(false);

        // Add the welcome message to the background panel which overlaps the welcome message
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(welcomeLabel, gbc);

        // Create buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonsPanel.setOpaque(false);

        // Create buttons
        JButton newBookingButton = createStyledButton("BOOK NOW");
        newBookingButton.addActionListener(e -> openNewBookingDialog());

        JButton readBookingButton = createStyledButton("CHECK BOOKING");
        readBookingButton.addActionListener(e -> openCheckBookingDialog());

        JButton exitButton = createStyledButton("EXIT");
        exitButton.addActionListener(e -> System.exit(0));

        // Add buttons to the buttons panel
        buttonsPanel.add(newBookingButton);
        buttonsPanel.add(readBookingButton);
        buttonsPanel.add(exitButton);

        // Add the background panel and buttons panel to the main panel
        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    //Reference to make style button
    //Kanwaljeet Singh. (2019, January 24). Lect 2.7 - Cool Button Styles in JAVA || Make a Round Buttons in java ||. YouTube. https://www.youtube.com/watch?app=desktop&v=ULipnRop_8A
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(getForeground());
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                Rectangle stringBounds = fm.getStringBounds(getText(), g2).getBounds();
                int textX = (getWidth() - stringBounds.width) / 2;
                int textY = (getHeight() - stringBounds.height) / 2 + fm.getAscent();
                g2.drawString(getText(), textX, textY);
                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getForeground());
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
                g2.dispose();
            }
        };
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(250, 75));
        button.setForeground(Color.WHITE);
        button.setBackground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    // when theuser click on new booking this new panel will open 
    private void openNewBookingDialog() {
        JDialog dialog = new JDialog(this, "Book Now", true);
        dialog.setSize(1200, 800);
        dialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        dialog.setContentPane(mainPanel);

        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(1200, 400));
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("BOOK NOW", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 50));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(welcomeLabel, gbc);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JComboBox<Location> locationComboBox = new JComboBox<>(locations.toArray(new Location[0]));
        JTextField checkInDateField = new JTextField();
        JTextField checkOutDateField = new JTextField();
        JSpinner adultsSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));
        JSpinner childrenSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10, 1));

        formPanel.add(new JLabel("Location:"));
        formPanel.add(locationComboBox);
        formPanel.add(new JLabel("Check in Date:"));
        formPanel.add(checkInDateField);
        formPanel.add(new JLabel("Check out Date:"));
        formPanel.add(checkOutDateField);
        formPanel.add(new JLabel("Adult:"));
        formPanel.add(adultsSpinner);
        formPanel.add(new JLabel("Children:"));
        formPanel.add(childrenSpinner);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));

        JButton searchButton = createStyledButton("Search");
        searchButton.addActionListener(e -> {
            selectedLocation = (Location) locationComboBox.getSelectedItem();
            String checkInDateText = checkInDateField.getText();
            String checkOutDateText = checkOutDateField.getText();
            adults = (Integer) adultsSpinner.getValue();
            children = (Integer) childrenSpinner.getValue();

            if (adults < 1) {
                JOptionPane.showMessageDialog(dialog, "Please select at least one adult.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedLocation != null) {
                try {
                    checkInDate = LocalDate.parse(checkInDateText, dateFormatter);
                    checkOutDate = LocalDate.parse(checkOutDateText, dateFormatter);

                    if (checkInDate.isBefore(LocalDate.now())) {
                        JOptionPane.showMessageDialog(dialog, "Check-in date cannot be in the past.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (checkOutDate.isBefore(checkInDate) || checkOutDate.equals(checkInDate)) {
                        JOptionPane.showMessageDialog(dialog, "Check-out date must be after the check-in date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

                    DatabaseService service = new DatabaseService();
                    List<Hotel> availableHotels = service.getHotelsByLocation(selectedLocation.getLocationId());

                    currentBooking = new Booking();
                    currentBooking.setLocation(selectedLocation);
                    currentBooking.setCheckInDate(checkInDate);
                    currentBooking.setCheckOutDate(checkOutDate);
                    currentBooking.setNights(nights);
                    currentBooking.setAdults(adults);
                    currentBooking.setChildren(children);

                    displayAvailableHotels(availableHotels, dialog, nights, adults, children);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid date format. Please enter the date as dd/MM/yyyy.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(dialog, "Please select a location.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JButton cancelButton = createStyledButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonsPanel.add(searchButton);
        buttonsPanel.add(cancelButton);

        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    //extract data from database for selected hotel
    private void displayAvailableHotels(List<Hotel> availableHotels, JDialog parentDialog, long nights, int adults, int children) {
        parentDialog.dispose();

        JDialog hotelsDialog = new JDialog(this, "Available Hotels", true);
        hotelsDialog.setSize(1200, 800);
        hotelsDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        hotelsDialog.setContentPane(mainPanel);

        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(800, 200));
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel headerLabel = new JLabel("AVAILABLE HOTELS", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 40));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(headerLabel, gbc);

        JPanel hotelsPanel = new JPanel();
        hotelsPanel.setLayout(new BoxLayout(hotelsPanel, BoxLayout.Y_AXIS));

        for (Hotel hotel : availableHotels) {
            JPanel hotelPanel = new JPanel(new BorderLayout(30, 30));

            ImageIcon hotelImageIcon = new ImageIcon(hotel.getImageUrl());
            Image hotelImage = hotelImageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(hotelImage));
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel detailsPanel = new JPanel(new BorderLayout());

            // used gpt to split the discrption has discription was too long
            String description = hotel.getDescription();
            String[] descriptionParts = description.split(" ");
            StringBuilder firstPart = new StringBuilder();
            StringBuilder secondPart = new StringBuilder();

            for (int i = 0; i < descriptionParts.length; i++) {
                if (i < 15) { // First 15 words go to the first part
                    firstPart.append(descriptionParts[i]).append(" ");
                } else { // Remaining words go to the second part
                    secondPart.append(descriptionParts[i]).append(" ");
                }
            }

            //used gpt for guidlines , position for labels
            JLabel hotelLabel = new JLabel("<html><b style='font-size:20px;'>" + hotel.getName() + "</b><br>"
                    + "Address: " + hotel.getAddress() + "<br>"
                    + "Description: " + firstPart.toString().trim() + "<br>"
                    + (secondPart.length() > 0 ? secondPart.toString().trim() : "")
                    + "</html>");

            hotelLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Calculate estimated total cost
            double baseCost = hotel.getPrice() * nights;
            double extraAdultCost = (adults - 1) * 11 * nights;
            double childrenCost = children * 9 * nights;
            double estimatedTotalCost = baseCost + extraAdultCost + childrenCost;

            JLabel priceLabel = new JLabel("<html><b style='font-size:18px;'>Price: $" + String.format("%.2f", estimatedTotalCost) + "</b></html>");
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Next Button
            JButton nextButton = createStyledButton("Next");

            nextButton.addActionListener(e -> {
                DatabaseService service = new DatabaseService();
                List<Room> availableRooms = service.getRooms();
                currentBooking.setHotel(hotel); 
                displayAvailableRooms(availableRooms, hotelsDialog, hotel, nights, adults, children);
            });

            
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(priceLabel, BorderLayout.NORTH);
            buttonPanel.add(nextButton, BorderLayout.SOUTH);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

         
            detailsPanel.add(hotelLabel, BorderLayout.CENTER);

            hotelPanel.add(imageLabel, BorderLayout.WEST);
            hotelPanel.add(detailsPanel, BorderLayout.CENTER);
            hotelPanel.add(buttonPanel, BorderLayout.EAST);
            hotelPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            hotelsPanel.add(hotelPanel);
            hotelsPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
        }

        JScrollPane scrollPane = new JScrollPane(hotelsPanel);
        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        hotelsDialog.setVisible(true);
    }

    //we are creatting default rooms not room particularyy for each hotel has the databse data for it is big, will take time to add dara
    private void displayAvailableRooms(List<Room> availableRooms, JDialog parentDialog, Hotel hotel, long nights, int adults, int children) {
        parentDialog.dispose();

        JDialog roomsDialog = new JDialog(this, "Available Rooms", true);
        roomsDialog.setSize(1200, 800);
        roomsDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        roomsDialog.setContentPane(mainPanel);

        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(1200, 200));
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel headerLabel = new JLabel("AVAILABLE ROOMS", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 50));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(headerLabel, gbc);

        JPanel roomListPanel = new JPanel();
        roomListPanel.setLayout(new BoxLayout(roomListPanel, BoxLayout.Y_AXIS));

        double basePrice = hotel.getPrice();
        double extraAdultCost = (adults - 1) * 11 * nights;
        double childrenCost = children * 9 * nights;

        for (Room room : availableRooms) {
            JPanel roomPanel = new JPanel(new BorderLayout(30, 30));

            // Room Image
            ImageIcon roomImageIcon = new ImageIcon(room.getImageUrl());
            Image roomImage = roomImageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(roomImage));
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Room Details
            JPanel detailsPanel = new JPanel(new BorderLayout());

            // used gpt to poition labels
            JLabel roomLabel = new JLabel("<html><b style='font-size:20px;'>" + room.getName() + "</b><br>"
                    + room.getDescription() + "</html>");

            roomLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            double additionalPrice = room.getPrice();
            double estimatedTotalCost = (basePrice + additionalPrice) * nights + extraAdultCost + childrenCost;

            // used gpt to poition labels
            JLabel priceLabel = new JLabel("<html><b style='font-size:18px;'>Price: $" + String.format("%.2f", estimatedTotalCost) + "</b></html>");
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Next Button
            JButton nextButton = createStyledButton("Next");

            nextButton.addActionListener(e -> {
                DatabaseService service = new DatabaseService();
                List<Service> services = service.getAllServices();
                currentBooking.setRoom(room); // Set selected room
                displayServicesDialog(services, roomsDialog);
            });

            // Panel for Next Button
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(priceLabel, BorderLayout.NORTH);
            buttonPanel.add(nextButton, BorderLayout.SOUTH);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            detailsPanel.add(roomLabel, BorderLayout.CENTER);

         
            roomPanel.add(imageLabel, BorderLayout.WEST);
            roomPanel.add(detailsPanel, BorderLayout.CENTER);
            roomPanel.add(buttonPanel, BorderLayout.EAST);
            roomPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            roomListPanel.add(roomPanel);
            roomListPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between room panels
        }

        JScrollPane scrollPane = new JScrollPane(roomListPanel);
        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        roomsDialog.setVisible(true);
    }

    // services also same default same services avelables for all hotels on all location , user can click which ever he wants
    private void displayServicesDialog(List<Service> services, JDialog parentDialog) {
        parentDialog.dispose();

        JDialog servicesDialog = new JDialog(this, "Services", true);
        servicesDialog.setSize(1200, 800);
        servicesDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        servicesDialog.setContentPane(mainPanel);

        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(1200, 200));
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel headerLabel = new JLabel("SERVICES", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 50));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(headerLabel, gbc);

        JPanel servicesPanel = new JPanel();
        servicesPanel.setLayout(new BoxLayout(servicesPanel, BoxLayout.Y_AXIS));

        for (Service service : services) {
            JPanel servicePanel = new JPanel(new BorderLayout(5, 5)); 

            //used gpt to position the lable
            JLabel serviceLabel = new JLabel("<html><b style='font-size:18px;'>" + service.getName() + "</b></html>"); // Adjusted font size
            serviceLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            //used gpt to position the lable
            JLabel priceLabel = new JLabel("<html><b style='font-size:18px;'>Price: $" + service.getPrice() + "</b></html>"); // Adjusted font size
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JCheckBox selectBox = new JCheckBox();
            selectBox.setOpaque(false);
            selectBox.putClientProperty("service", service); // checkbox to select services wanted

            JPanel selectPanel = new JPanel(new BorderLayout());
            selectPanel.add(priceLabel, BorderLayout.NORTH);
            selectPanel.add(selectBox, BorderLayout.SOUTH);
            selectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            servicePanel.add(serviceLabel, BorderLayout.CENTER);
            servicePanel.add(selectPanel, BorderLayout.EAST);
            servicePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            servicesPanel.add(servicePanel);
            servicesPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        }

        JScrollPane scrollPane = new JScrollPane(servicesPanel);
        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> servicesDialog.dispose());

        JButton continueButton = createStyledButton("Continue");
        //itereatt to each check service selected
        continueButton.addActionListener(e -> {
            List<Service> selectedServices = new ArrayList<>();
            for (Component component : servicesPanel.getComponents()) {
                if (component instanceof JPanel) {
                    JPanel panel = (JPanel) component;
                    for (Component subComponent : panel.getComponents()) {
                        if (subComponent instanceof JPanel) {
                            JPanel subPanel = (JPanel) subComponent;
                            for (Component checkBoxComponent : subPanel.getComponents()) {
                                if (checkBoxComponent instanceof JCheckBox) {
                                    JCheckBox checkBox = (JCheckBox) checkBoxComponent;
                                    if (checkBox.isSelected()) {
                                        Service service = (Service) checkBox.getClientProperty("service");
                                        if (service != null) {
                                            selectedServices.add(service);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            currentBooking.setServices(selectedServices);
            currentBooking.updateTotalServicePrice(); 

            displayCustomerDetailsDialog(servicesDialog);
        }); //next panel

        buttonPanel.add(backButton);
        buttonPanel.add(continueButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        servicesDialog.setVisible(true);
    }

    private void displayCustomerDetailsDialog(JDialog parentDialog) {
        parentDialog.dispose();

        JDialog customerDialog = new JDialog(this, "Customer Details", true);
        customerDialog.setSize(1200, 800);
        customerDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        customerDialog.setContentPane(mainPanel);

        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(1200, 200));
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel headerLabel = new JLabel("CUSTOMER DETAILS", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 50));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(headerLabel, gbc);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        //used gpt and many youtube vedios to make proper interface
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(10, 30, 10, 30); 
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField nameField = new JTextField(20);
        JTextField dobField = new JTextField(20);
        JCheckBox marriedCheckBox = new JCheckBox();
        JTextField contactField = new JTextField(20);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 20)); 

        JLabel marriedLabel = new JLabel("Married:");
        marriedLabel.setFont(new Font("Arial", Font.PLAIN, 20)); 

        JLabel contactLabel = new JLabel("Contact Number:");
        contactLabel.setFont(new Font("Arial", Font.PLAIN, 20)); 

        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formPanel.add(nameLabel, formGbc);

        formGbc.gridx = 1;
        formPanel.add(nameField, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formPanel.add(dobLabel, formGbc);

        formGbc.gridx = 1;
        formPanel.add(dobField, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formPanel.add(marriedLabel, formGbc);

        formGbc.gridx = 1;
        formPanel.add(marriedCheckBox, formGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 3;
        formPanel.add(contactLabel, formGbc);

        formGbc.gridx = 1;
        formPanel.add(contactField, formGbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> customerDialog.dispose());

        JButton nextButton = createStyledButton("Next");
        nextButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String dob = dobField.getText().trim();
            String contact = contactField.getText().trim();

            //creating error handeling
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(customerDialog, "Name must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (dob.isEmpty()) {
                JOptionPane.showMessageDialog(customerDialog, "Date of Birth must not be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                LocalDate dobDate = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate now = LocalDate.now();
                if (ChronoUnit.YEARS.between(dobDate, now) < 18) {
                    JOptionPane.showMessageDialog(customerDialog, "You are under 18, you cannot book.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(customerDialog, "Invalid Date of Birth format. Please use DD/MM/YYYY.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!contact.matches("\\d{10}")) {
                JOptionPane.showMessageDialog(customerDialog, "Contact Number must be 10 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Save customer details to currentBooking
            currentBooking.setCustomerName(nameField.getText());
            currentBooking.setDob(dobField.getText());
            currentBooking.setMarried(marriedCheckBox.isSelected());
            currentBooking.setContactNumber(contactField.getText());

            displayBookingDetailsDialog(customerDialog);
        });

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        customerDialog.setVisible(true);
    }

    
    //very important class this class displays all the details which we slected to make booking
    //every above methods gave using booking class and booking operrations to get ans set selected options and this class 
    //displays all that selected options
    private void displayBookingDetailsDialog(JDialog parentDialog) {
        parentDialog.dispose();

        JDialog bookingDetailsDialog = new JDialog(this, "Booking Details", true);
        bookingDetailsDialog.setSize(1200, 800);
        bookingDetailsDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        bookingDetailsDialog.setContentPane(mainPanel);

        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(1200, 200));
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel headerLabel = new JLabel("BOOKING DETAILS", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 50));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(headerLabel, gbc);

        double basePrice = currentBooking.getHotel().getPrice();
        double additionalPrice = currentBooking.getRoom().getPrice();
        double totalRoomPrice = (basePrice + additionalPrice) * currentBooking.getNights();

        double additionalAdultCharge = (adults - 1) * 11 * nights;
        double childrenCharge = children * 9 * nights;
        double servicesTotalPrice = currentBooking.getTotalServicePrice();
        double totalPriceBeforeTax = totalRoomPrice + additionalAdultCharge + childrenCharge + servicesTotalPrice;
        double taxAmount = totalPriceBeforeTax * 0.02;
        double totalPriceWithTax = totalPriceBeforeTax + taxAmount;

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        GridBagConstraints gbcDetails = new GridBagConstraints();
        gbcDetails.insets = new Insets(10, 20, 10, 120);
        gbcDetails.anchor = GridBagConstraints.WEST;
        gbcDetails.fill = GridBagConstraints.HORIZONTAL;

        Font boldFont = new Font("Arial", Font.BOLD, 18);

        //format of which it will be showed like left label
        JLabel[] labels = {
            new JLabel("Location Selected:"),
            new JLabel("Check In Date:"),
            new JLabel("Check Out Date:"),
            new JLabel("Total Nights:"),
            new JLabel("Hotel Selected:"),
            new JLabel("Room Selected:"),
            new JLabel("Services Selected:"),
            new JLabel("Customer Name:"),
            new JLabel("Date of Birth:"),
            new JLabel("Married:"),
            new JLabel("Contact Number:"),
            new JLabel("Room Price:"),
            new JLabel("Number of Adults:"),
            new JLabel("Number of Children:"),
            new JLabel("Service Charge:"),
            new JLabel("Tax 2%:"),
            new JLabel("Total Price with Tax:")
        };

        for (JLabel label : labels) {
            label.setFont(boldFont);
        }

        // this gets the selected values from each panle methods
        JLabel[] values = {
            new JLabel(currentBooking.getLocation().getName()),
            new JLabel(currentBooking.getCheckInDate().toString()),
            new JLabel(currentBooking.getCheckOutDate().toString()),
            new JLabel(String.valueOf(nights)),
            new JLabel(currentBooking.getHotel().getName()),
            new JLabel(currentBooking.getRoom().getName()),
            new JLabel(currentBooking.getServiceNames()), 
            new JLabel(currentBooking.getCustomerName()),
            new JLabel(currentBooking.getDob()),
            new JLabel(currentBooking.isMarried() ? "Yes" : "No"),
            new JLabel(currentBooking.getContactNumber()),
            new JLabel("$" + String.format("%.2f", totalRoomPrice)),
            new JLabel(String.valueOf(adults)),
            new JLabel(String.valueOf(children)),
            new JLabel("$" + String.format("%.2f", servicesTotalPrice)), 
            new JLabel("$" + String.format("%.2f", taxAmount)),
            new JLabel("$" + String.format("%.2f", totalPriceWithTax))
        };

        for (int i = 0; i < labels.length; i++) {
            gbcDetails.gridx = 0;
            gbcDetails.gridy = i;
            detailsPanel.add(labels[i], gbcDetails);

            gbcDetails.gridx = 1;
            detailsPanel.add(values[i], gbcDetails);
        }

        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));

        //now we have to options save booking and confirm booking , if user wants to save booking and book later he can select this
        //option and then this details will be saved to booking.txt file , and provide a unique booking references number wher we can use this in check booking.
        JButton saveBookingButton = createStyledButton("Save Booking");
        saveBookingButton.addActionListener(e -> {
            currentBooking.setStatus("Not Booked");
            DatabaseService service = new DatabaseService();
            service.saveBooking(currentBooking);
            FileIOUtility.writeBookingToFile(currentBooking, "bookingDetails.txt");

            JOptionPane.showMessageDialog(this, "Your booking details are saved. Your reference number is: " + currentBooking.getBookingId());
            bookingDetailsDialog.dispose();
        });

        //directly confirms the booking and status is booked gicvien unique references number , and saved to bookingdetails.txt file
        JButton confirmBookingButton = createStyledButton("Confirm Booking");
        confirmBookingButton.addActionListener(e -> {
            currentBooking.setStatus("Booked");
            DatabaseService service = new DatabaseService() {};
            service.saveBooking(currentBooking);
            FileIOUtility.writeBookingToFile(currentBooking, "bookingDetails.txt");

            JOptionPane.showMessageDialog(this, "Congratulations! Your hotel room is booked. Your reference number is: " + currentBooking.getBookingId());
            bookingDetailsDialog.dispose();
        });

        buttonPanel.add(saveBookingButton);
        buttonPanel.add(confirmBookingButton);

        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        bookingDetailsDialog.setVisible(true);
    }

    //the main panel has 3 options , the second is checkbooking here when user selects this option ,
    // it is asked for referenes number then serch, then it use file class to retrive the booking details for that number.
    private void openCheckBookingDialog() {
    JDialog checkBookingDialog = new JDialog(this, "Check Booking", true);
    checkBookingDialog.setSize(1200, 800);
    checkBookingDialog.setLocationRelativeTo(this);

    JPanel mainPanel = new JPanel(new BorderLayout());
    checkBookingDialog.setContentPane(mainPanel);

    File backgroundImageFile = new File("src/images/Main.png");
    Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

    BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
    backgroundPanel.setPreferredSize(new Dimension(1200, 200));
    backgroundPanel.setLayout(new GridBagLayout());

    JLabel headerLabel = new JLabel("CHECK YOUR BOOKING", SwingConstants.CENTER);
    headerLabel.setFont(new Font("Serif", Font.BOLD, 50));
    headerLabel.setForeground(Color.WHITE);
    headerLabel.setOpaque(false);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    backgroundPanel.add(headerLabel, gbc);

    JPanel centerPanel = new JPanel(new GridBagLayout());
    mainPanel.add(backgroundPanel, BorderLayout.NORTH);
    mainPanel.add(centerPanel, BorderLayout.CENTER);

    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
    inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

    JLabel promptLabel = new JLabel("Enter Your Booking Reference");
    promptLabel.setFont(new Font("Serif", Font.BOLD, 20));
    promptLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    inputPanel.add(promptLabel);

    JTextField referenceField = new JTextField();
    referenceField.setFont(new Font("Serif", Font.PLAIN, 20));
    referenceField.setMaximumSize(new Dimension(400, 40));
    referenceField.setAlignmentX(Component.CENTER_ALIGNMENT);
    inputPanel.add(referenceField);

    JButton searchButton = new JButton("SEARCH");
    searchButton.setFont(new Font("Serif", Font.BOLD, 24));
    searchButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    inputPanel.add(searchButton);

    searchButton.addActionListener(e -> {
        String referenceNumber = referenceField.getText().trim();
        Booking booking = FileIOUtility.readBookingFromFile(referenceNumber, "bookingDetails.txt");
        if (booking == null) {
            JOptionPane.showMessageDialog(checkBookingDialog, "Your booking reference number is incorrect.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            checkBookingDialog.dispose();
            displayBookingDetailsForCheck(booking);
        }
    });

    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.anchor = GridBagConstraints.CENTER;
    centerPanel.add(inputPanel, gbc);

    checkBookingDialog.setVisible(true);
}


    
    // displays booking dialog 
    private void displayBookingDetailsForCheck(Booking booking) {
        JDialog bookingDetailsDialog = new JDialog(this, "Booking Details", true);
        bookingDetailsDialog.setSize(1200, 800);
        bookingDetailsDialog.setLocationRelativeTo(this);

        JPanel mainPanel = new JPanel(new BorderLayout());
        bookingDetailsDialog.setContentPane(mainPanel);

        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(1200, 200));
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel headerLabel = new JLabel("BOOKING DETAILS", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 50));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(headerLabel, gbc);

        JTextArea bookingDetailsArea = new JTextArea();
        bookingDetailsArea.setFont(new Font("Serif", Font.PLAIN, 20));
        bookingDetailsArea.setEditable(false);
        bookingDetailsArea.setText(getBookingDetailsText(booking));

        JScrollPane scrollPane = new JScrollPane(bookingDetailsArea);
        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));

        //here if the user has clicked on confirm booking while booking then this optionwont be showed 
        // showed only when save booking is selected , to confirm booking and updated the booking status.
        if (!"Booked".equalsIgnoreCase(booking.getStatus())) {
            JButton confirmButton = new JButton("Confirm Booking");
            confirmButton.setFont(new Font("Serif", Font.BOLD, 24));
            buttonPanel.add(confirmButton);

            confirmButton.addActionListener(e -> {
                booking.setStatus("Booked");
                FileIOUtility.writeBookingToFile(booking, "bookingDetails.txt");
                JOptionPane.showMessageDialog(bookingDetailsDialog, "Your booking is now confirmed.", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                confirmButton.setEnabled(false); // Disable the button after confirming
                bookingDetailsDialog.dispose(); // Close the dialog
            });
        }

        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif", Font.BOLD, 24));
        exitButton.addActionListener(e -> bookingDetailsDialog.dispose());
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        bookingDetailsDialog.setVisible(true);
    }

    // format to display and get data from file
    private String getBookingDetailsText(Booking booking) {
        StringBuilder details = new StringBuilder();
        details.append("Location Selected:                             ").append(booking.getLocation().getName()).append("\n");
        details.append("Check In Date:                                   ").append(booking.getCheckInDate()).append("\n");
        details.append("Check Out Date:                                ").append(booking.getCheckOutDate()).append("\n");
        details.append("Total Nights:                                      ").append(booking.getNights()).append("\n");
        details.append("Hotel Selected:                                  ").append(booking.getHotel().getName()).append("\n");
        details.append("Room Selected:                                 ").append(booking.getRoom().getName()).append("\n");
        details.append("Services Selected:                             ").append(booking.getServiceNames()).append("\n");
        details.append("Customer Name:                                ").append(booking.getCustomerName()).append("\n");
        details.append("Date of Birth:                                     ").append(booking.getDob()).append("\n");
        details.append("Married:                                             ").append(booking.isMarried() ? "Yes" : "No").append("\n");
        details.append("Contact Number:                               ").append(booking.getContactNumber()).append("\n");

        double basePrice = currentBooking.getHotel().getPrice();
        double additionalPrice = currentBooking.getRoom().getPrice();
        double totalRoomPrice = (basePrice + additionalPrice) * currentBooking.getNights();

        details.append("Room Price:                                      $").append(String.format("%.2f", totalRoomPrice)).append("\n");

        details.append("Number of Adults:                            ").append(booking.getAdults()).append("\n");
        details.append("Number of Children:                         ").append(booking.getChildren()).append("\n");

        double additionalAdultCharge = (booking.getAdults() - 1) * 11 * booking.getNights();
        double childrenCharge = booking.getChildren() * 9 * booking.getNights();
        double servicesTotalPrice = booking.getTotalServicePrice();
        double totalPriceBeforeTax = totalRoomPrice + additionalAdultCharge + childrenCharge + servicesTotalPrice;
        double taxAmount = totalPriceBeforeTax * 0.02;
        double totalPriceWithTax = totalPriceBeforeTax + taxAmount;

        details.append("Service Charge:                                $").append(booking.getTotalServicePrice()).append("\n");
        details.append("Tax 2%:                                            $").append(String.format("%.2f", taxAmount)).append("\n");
        details.append("Total Price with Tax:                       $").append(String.format("%.2f", totalPriceWithTax)).append("\n");
        details.append("Booking Status:                                ").append(booking.getStatus()).append("\n");
        return details.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseService service = new DatabaseService();
            List<Location> locations = service.getAllLocations();
            new BookingFrame(locations);
        });
    }
}
