/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hotel_booking;

/**
 *
 * @author Bradl
 */
import javax.swing.*;
import java.awt.*;
import java.io.File;

import javax.swing.JFrame;

import java.util.List;

public class BookingFrame extends JFrame {

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
        dialog.setVisible(true);
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