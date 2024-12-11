import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import java.awt.*;

class BikeDetailsUI {
    public BikeDetailsUI(JFrame parentFrame, String title, BookingInfo booking, BrowseBikesUI browseBikesUI) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(400, 400);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(new JLabel("Brand: " + booking.getBrand()));
        panel.add(new JLabel("Type: " + booking.getType()));
        panel.add(new JLabel("Price: $" + booking.getPrice()));
        panel.add(new JLabel("Availability: " + (booking.isAvailable() ? "Available" : "Unavailable")));

        JPanel bookingPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        bookingPanel.setBorder(BorderFactory.createTitledBorder("Booking"));
        bookingPanel.add(new JLabel("Duration (days):"));

        JTextField durationField = new JTextField();
        durationField.setPreferredSize(new Dimension(50, 5));
        bookingPanel.add(durationField);

        JButton bookButton = new JButton("Book");
        bookButton.setEnabled(booking.isAvailable());
        bookButton.addActionListener(e -> {
            String durationText = durationField.getText().trim();
            if (durationText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please specify the booking duration.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int duration = Integer.parseInt(durationText);
                if (duration <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Duration must be a positive number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (booking.isAvailable()) {
                    booking.setAvailable(false);

                    LocalDate currentDate = LocalDate.now();
                    LocalDate rentalDate = currentDate.plusDays(duration);
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    booking.setRentalDate(rentalDate.format(formatter));

                    BookingDataManager.getInstance().saveBookingData();
                    browseBikesUI.refreshTableData();
                    JOptionPane.showMessageDialog(dialog, "Bike booked successfully until " + rentalDate.format(formatter) + "!");
                    dialog.dispose();
                } else {
                    JOptionPane.showMessageDialog(dialog, "Bike is already unavailable.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Duration must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bookButton);

        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.add(panel, BorderLayout.NORTH);
        contentPanel.add(bookingPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(contentPanel);
        dialog.setVisible(true);
    }
}
