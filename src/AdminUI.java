import javax.swing.*;
import java.awt.*;

class AdminUI {
    public AdminUI() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(2, 1, 10, 10));

        JButton manageBikesButton = new JButton("Manage Bikes");
        manageBikesButton.addActionListener(e -> {new ManageBikesUI();frame.dispose();});

        JButton manageRequestsButton = new JButton("Manage Rental Requests");
        manageRequestsButton.addActionListener(e -> {new ManageRentalRequestsUI();frame.dispose();});

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            BikeRentalApp.main(new String[]{});
            frame.dispose();
        });

        frame.add(manageBikesButton);
        frame.add(manageRequestsButton);
        frame.add(backButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new AdminUI();
    }
}
