import javax.swing.*;
import java.awt.*;

class BikeDetailsUI {
    public BikeDetailsUI(JFrame parentFrame, String title, BookingInfo booking, BrowseBikesUI browseBikesUI) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.add(new JLabel("Customer: " + booking.getCustomerName()));
        panel.add(new JLabel("Bike Model: " + booking.getBikeModel()));
        panel.add(new JLabel("Rental Date: " + booking.getRentalDate()));
        panel.add(new JLabel("Duration (days): " + booking.getRentalDuration()));
        panel.add(new JLabel("Availability: " + (booking.isAvailable() ? "Available" : "Unavailable")));

        JButton bookButton = new JButton("Book");
        bookButton.setEnabled(booking.isAvailable());
        bookButton.addActionListener(e -> {
            if (booking.isAvailable()) {
                booking.setAvailable(false);
                BookingDataManager.getInstance().saveBookingData();
                browseBikesUI.refreshTableData(); // Refresh the table in BrowseBikesUI
                JOptionPane.showMessageDialog(dialog, "Bike booked successfully!");
                dialog.dispose(); // Close the details dialog
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bookButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
