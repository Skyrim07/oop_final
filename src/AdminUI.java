import javax.swing.*;
import java.awt.*;

class AdminUI {
    public AdminUI() {
        JFrame frame = new JFrame("Admin Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JTextField makeField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField typeField = new JTextField();
        JTextField priceField = new JTextField();

        JButton addButton = new JButton("Add Bike");
        addButton.addActionListener(e -> {
            String make = makeField.getText();
            String model = modelField.getText();
            String type = typeField.getText();
            String price = priceField.getText();
            System.out.println("Bike Added: " + make + ", " + model + ", " + type + ", $" + price);
        });

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new BikeRentalApp();
            frame.dispose();
        });

        panel.add(new JLabel("Make:"));
        panel.add(makeField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("Type:"));
        panel.add(typeField);
        panel.add(new JLabel("Price/Day:"));
        panel.add(priceField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}
