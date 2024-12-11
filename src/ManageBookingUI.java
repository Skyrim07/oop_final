import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

class ManageBookingUI {
    public ManageBookingUI(JFrame parentFrame, String title, BookingInfo booking, BookingHistoryUI bookingHistoryUI) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(parentFrame);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.add(new JLabel("Brand:"));
        panel.add(new JLabel(booking.getBrand()));

        panel.add(new JLabel("Type:"));
        panel.add(new JLabel(booking.getType()));

        panel.add(new JLabel("Price:"));
        panel.add(new JLabel("$" + booking.getPrice()));

        panel.add(new JLabel("Rental Date:"));
        panel.add(new JLabel(booking.getRentalDate()));

        panel.add(new JLabel("Change Duration (days):"));
        JTextField durationField = new JTextField();
        panel.add(durationField);

        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
            String durationText = durationField.getText().trim();
            if (!durationText.isEmpty()) {
                try {
                    int newDuration = Integer.parseInt(durationText);
                    if (newDuration > 0) {
                        LocalDate newRentalDate = LocalDate.now().plusDays(newDuration);
                        booking.setRentalDate(newRentalDate.toString());
                        BookingDataManager.getInstance().saveBookingData();
                        JOptionPane.showMessageDialog(dialog, "Booking updated successfully!");
                        bookingHistoryUI.refreshTableData();
                        dialog.dispose();
                    } else {
                        JOptionPane.showMessageDialog(dialog, "Duration must be greater than 0.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid duration input.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton cancelButton = new JButton("Cancel Booking");
        cancelButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(dialog, "Are you sure you want to cancel this booking?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                booking.setAvailable(true);
                booking.setRentalDate("");
                BookingDataManager.getInstance().saveBookingData();
                JOptionPane.showMessageDialog(dialog, "Booking canceled successfully!");
                bookingHistoryUI.refreshTableData();
                dialog.dispose();
            }
        });

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
