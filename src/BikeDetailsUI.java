import javax.swing.*;
import java.awt.*;

class BikeDetailsUI {
    public BikeDetailsUI(JFrame parentFrame, String title, BookingInfo booking, BrowseBikesUI browseBikesUI) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.add(new JLabel("Brand: " + booking.getBrand()));
        panel.add(new JLabel("Type: " + booking.getType()));
        panel.add(new JLabel("Price: $" + booking.getPrice()));
        panel.add(new JLabel("Availability: " + (booking.isAvailable() ? "Available" : "Unavailable")));

        JButton bookButton = new JButton("Book");
        bookButton.setEnabled(booking.isAvailable());
        bookButton.addActionListener(e -> {
            if (booking.isAvailable()) {
                booking.setAvailable(false);
                BookingDataManager.getInstance().saveBookingData();
                browseBikesUI.refreshTableData();
                JOptionPane.showMessageDialog(dialog, "Bike booked successfully!");
                dialog.dispose();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(bookButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
