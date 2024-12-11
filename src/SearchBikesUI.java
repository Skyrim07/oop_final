import javax.swing.*;
import java.awt.*;

class SearchBikesUI {
    public SearchBikesUI() {
        JFrame frame = new JFrame("Search Bikes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Search functionality is under construction.", SwingConstants.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new RenterUI();
            frame.dispose();
        });

        panel.add(label, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }
}
