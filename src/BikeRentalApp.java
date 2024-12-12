import javax.swing.*;
import java.awt.*;

public class BikeRentalApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Bike Rental Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLayout(new BorderLayout(10, 10));

            JLabel titleLabel = new JLabel("Welcome to the Bike Rental Management System!", SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            frame.add(titleLabel, BorderLayout.NORTH);

            JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
            BookingDataManager.getInstance();
            JButton renterButton = new JButton("I am bike renter");
            JButton adminButton = new JButton("I am administrator");

            renterButton.addActionListener(e -> {
                new RenterUI();
                frame.dispose();
            });

            adminButton.addActionListener(e -> {
                new AdminUI();
                frame.dispose();
            });

            mainPanel.add(renterButton);
            mainPanel.add(adminButton);

            frame.add(mainPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
