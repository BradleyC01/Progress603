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
import java.util.ArrayList;

import javax.swing.JFrame;

import java.util.List;

public class BookingFrame extends JFrame {

    private List<Location> locations;

    public BookingFrame(List<Location> locations) {
        setTitle("Hotel Booking System");
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setOpaque(false);
        setContentPane(mainPanel);

        File backgroundImageFile = new File("src/images/Main.png");
        Image backgroundImage = new ImageIcon(backgroundImageFile.getAbsolutePath()).getImage();

        BackgroundPanel backgroundPanel = new BackgroundPanel(backgroundImage);
        backgroundPanel.setPreferredSize(new Dimension(1000, 400));
        backgroundPanel.setLayout(new GridBagLayout());

        JLabel welcomeLabel = new JLabel("WELCOME HOTEL BOOKING SYSTEM", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 50));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        backgroundPanel.add(welcomeLabel, gbc);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonsPanel.setOpaque(false);

        JButton newBookingButton = createStyledButton("BOOK NOW");
        newBookingButton.addActionListener(e -> openNewBookingDialog());

        JButton readBookingButton = createStyledButton("CHECK BOOKING");
        readBookingButton.addActionListener(e -> openCheckBookingDialog());

        JButton exitButton = createStyledButton("EXIT");
        exitButton.addActionListener(e -> System.exit(0));

        buttonsPanel.add(newBookingButton);
        buttonsPanel.add(readBookingButton);
        buttonsPanel.add(exitButton);

        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        setVisible(true);
    }

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
            Location selectedLocation = (Location) locationComboBox.getSelectedItem();
            String checkInDateText = checkInDateField.getText();
            String checkOutDateText = checkOutDateField.getText();
            int adults = (Integer) adultsSpinner.getValue();
            int children = (Integer) childrenSpinner.getValue();

            if (adults < 1) {
                JOptionPane.showMessageDialog(dialog, "Please select at least one adult.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (selectedLocation != null) {
                try {
                    LocalDate checkInDate = LocalDate.parse(checkInDateText, dateFormatter);
                    LocalDate checkOutDate = LocalDate.parse(checkOutDateText, dateFormatter);

                    if (checkInDate.isBefore(LocalDate.now())) {
                        JOptionPane.showMessageDialog(dialog, "Check-in date cannot be in the past.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (checkOutDate.isBefore(checkInDate) || checkOutDate.equals(checkInDate)) {
                        JOptionPane.showMessageDialog(dialog, "Check-out date must be after the check-in date.", "Invalid Date", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

                    DatabaseService service = new DatabaseService();
                    List<Hotel> availableHotels = service.getHotelsByLocation(selectedLocation.getLocationId());

                    Booking currentBooking = new Booking();
                    currentBooking.setLocation(selectedLocation);
                    currentBooking.setCheckInDate(checkInDate);
                    currentBooking.setCheckOutDate(checkOutDate);
                    currentBooking.setNights(nights);
                    currentBooking.setAdults(adults);
                    currentBooking.setChildren(children);

                    displayAvailableHotels(availableHotels, dialog, nights, adults, children, currentBooking);
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

    private void displayAvailableHotels(List<Hotel> availableHotels, JDialog parentDialog, long nights, int adults, int children, Booking currentBooking) {
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

            // Hotel Image
            ImageIcon hotelImageIcon = new ImageIcon(hotel.getImageUrl());
            Image hotelImage = hotelImageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
            JLabel imageLabel = new JLabel(new ImageIcon(hotelImage));
            imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Hotel Details
            JPanel detailsPanel = new JPanel(new BorderLayout());

            // Split the description into multiple lines if it's too long
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
                currentBooking.setHotel(hotel); // Set selected hotel
                displayAvailableRooms(availableRooms, hotelsDialog, hotel, nights, adults, children, currentBooking);
            });

            // Panel for Next Button
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(priceLabel, BorderLayout.NORTH);
            buttonPanel.add(nextButton, BorderLayout.SOUTH);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add components to details panel
            detailsPanel.add(hotelLabel, BorderLayout.CENTER);

            // Add components to hotel panel
            hotelPanel.add(imageLabel, BorderLayout.WEST);
            hotelPanel.add(detailsPanel, BorderLayout.CENTER);
            hotelPanel.add(buttonPanel, BorderLayout.EAST);
            hotelPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            hotelsPanel.add(hotelPanel);
            hotelsPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add space between hotel panels
        }

        JScrollPane scrollPane = new JScrollPane(hotelsPanel);
        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        hotelsDialog.setVisible(true);
    }

    private void displayAvailableRooms(List<Room> availableRooms, JDialog parentDialog, Hotel hotel, long nights, int adults, int children, Booking currentBooking) {
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

            JLabel roomLabel = new JLabel("<html><b style='font-size:20px;'>" + room.getName() + "</b><br>"
                    + room.getDescription() + "</html>");

            roomLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            double additionalPrice = room.getPrice();
            double estimatedTotalCost = (basePrice + additionalPrice) * nights + extraAdultCost + childrenCost;

            JLabel priceLabel = new JLabel("<html><b style='font-size:18px;'>Price: $" + String.format("%.2f", estimatedTotalCost) + "</b></html>");
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            // Next Button
            JButton nextButton = createStyledButton("Next");

            nextButton.addActionListener(e -> {
                DatabaseService service = new DatabaseService();
                List<Service> services = service.getAllServices();
                currentBooking.setRoom(room); // Set selected room
                displayServicesDialog(services, roomsDialog, currentBooking);
            });

            // Panel for Next Button
            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.add(priceLabel, BorderLayout.NORTH);
            buttonPanel.add(nextButton, BorderLayout.SOUTH);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            // Add components to details panel
            detailsPanel.add(roomLabel, BorderLayout.CENTER);

            // Add components to room panel
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

    private void displayServicesDialog(List<Service> services, JDialog parentDialog, Booking currentBooking) {
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
            JPanel servicePanel = new JPanel(new BorderLayout(5, 5)); // Decreased gap

            JLabel serviceLabel = new JLabel("<html><b style='font-size:18px;'>" + service.getName() + "</b></html>"); // Adjusted font size
            serviceLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel priceLabel = new JLabel("<html><b style='font-size:18px;'>Price: $" + service.getPrice() + "</b></html>"); // Adjusted font size
            priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JCheckBox selectBox = new JCheckBox();
            selectBox.setOpaque(false);
            selectBox.putClientProperty("service", service); // Associate the service with the checkbox

            JPanel selectPanel = new JPanel(new BorderLayout());
            selectPanel.add(priceLabel, BorderLayout.NORTH);
            selectPanel.add(selectBox, BorderLayout.SOUTH);
            selectPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            servicePanel.add(serviceLabel, BorderLayout.CENTER);
            servicePanel.add(selectPanel, BorderLayout.EAST);
            servicePanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            servicesPanel.add(servicePanel);
            servicesPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Decreased vertical space between service panels
        }

        JScrollPane scrollPane = new JScrollPane(servicesPanel);
        mainPanel.add(backgroundPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));

        JButton backButton = createStyledButton("Back");
        backButton.addActionListener(e -> servicesDialog.dispose());

        JButton continueButton = createStyledButton("Continue");
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
            displayCustomerDetailsDialog(servicesDialog, currentBooking);
        });

        buttonPanel.add(backButton);
        buttonPanel.add(continueButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        servicesDialog.setVisible(true);
    }

    private void openCheckBookingDialog() {
        JDialog dialog = new JDialog(this, "Check Booking", true);
        dialog.setSize(1200, 800);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DatabaseService service = new DatabaseService();
            List<Location> locations = service.getAllLocations();
            new BookingFrame(locations);
        });
    }
}

//created the first panel were we choose to 3 options book now then checkbooking then exit
