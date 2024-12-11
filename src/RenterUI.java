import javax.swing.*;
import java.awt.*;

class RenterUI {
    public RenterUI() {
        JFrame frame = new JFrame("Bike Renter Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton browseBikesButton = new JButton("Browse Bikes");
        JButton viewHistoryButton = new JButton("View Booking History");

        browseBikesButton.addActionListener(e -> {
            new BrowseBikesUI();
            frame.dispose();
        });

        viewHistoryButton.addActionListener(e -> {
            new BookingHistoryUI();
            frame.dispose();
        });

        mainPanel.add(browseBikesButton);
        mainPanel.add(viewHistoryButton);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}