import javax.swing.*;
import java.awt.*;

class AddBikeUI {
    public AddBikeUI(ManageBikesUI manageBikesUI) {
        JFrame frame = new JFrame("Add Bike");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        frame.add(new JLabel("Brand:"));
        JTextField brandField = new JTextField();
        frame.add(brandField);

        frame.add(new JLabel("Type:"));
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Mountain Bike", "Electric Bike", "Casual Bike"});
        frame.add(typeComboBox);

        frame.add(new JLabel("Price per day:"));
        JTextField priceField = new JTextField();
        frame.add(priceField);

        JCheckBox availableCheckBox = new JCheckBox("Available");
        availableCheckBox.setSelected(true);
        frame.add(new JLabel("Availability:"));
        frame.add(availableCheckBox);

        JButton addButton = new JButton("Add Bike");
        addButton.addActionListener(e -> {
            String brand = brandField.getText().trim();
            String type = (String) typeComboBox.getSelectedItem();
            String priceText = priceField.getText().trim();
            boolean available = availableCheckBox.isSelected();

            if (brand.isEmpty() || type == null || priceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                BookingInfo newBike = new BookingInfo(brand, type, price, available);
                BookingDataManager.getInstance().getBookings().add(newBike);

                BookingDataManager.getInstance().saveBookingData();
                manageBikesUI.refreshTableData();

                JOptionPane.showMessageDialog(frame, "Bike added successfully!");
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Price must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(new JLabel());
        frame.add(addButton);

        frame.setVisible(true);
    }
}
