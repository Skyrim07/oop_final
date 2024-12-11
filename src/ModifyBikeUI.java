import javax.swing.*;
import java.awt.*;

class ModifyBikeUI {
    public ModifyBikeUI(BookingInfo bike, ManageBikesUI manageBikesUI) {
        JFrame frame = new JFrame("Modify Bike");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 10, 10));

        frame.add(new JLabel("Brand:"));
        JTextField brandField = new JTextField(bike.getBrand());
        frame.add(brandField);

        frame.add(new JLabel("Type:"));
        JComboBox<String> typeComboBox = new JComboBox<>(new String[]{"Mountain Bike", "Electric Bike", "Casual Bike"});
        typeComboBox.setSelectedItem(bike.getType());
        frame.add(typeComboBox);

        frame.add(new JLabel("Price per day:"));
        JTextField priceField = new JTextField(String.valueOf(bike.getPrice()));
        frame.add(priceField);

        JCheckBox availableCheckBox = new JCheckBox("Available");
        availableCheckBox.setSelected(bike.isAvailable());
        frame.add(new JLabel("Availability:"));
        frame.add(availableCheckBox);

        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> {
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

                bike.setBrand(brand);
                bike.setType(type);
                bike.setPrice(price);
                bike.setAvailable(available);

                BookingDataManager.getInstance().saveBookingData();
                manageBikesUI.refreshTableData();

                JOptionPane.showMessageDialog(frame, "Bike details updated successfully!");
                frame.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Price must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.add(new JLabel());
        frame.add(saveButton);

        frame.setVisible(true);
    }
}
