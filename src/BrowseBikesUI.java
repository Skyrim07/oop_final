import javax.swing.*;
import java.awt.*;
import java.util.List;

class BrowseBikesUI {
    private JTable bikeTable;
    private Object[][] tableData;
    private String[] columnNames = {"Customer", "Bike Model", "Rental Date", "Duration (days)", "Availability", "Action"};
    private BookingDataManager dataManager;

    public BrowseBikesUI() {
        JFrame frame = new JFrame("Browse Bikes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel(new BorderLayout());

        dataManager = BookingDataManager.getInstance();
        List<BookingInfo> bookings = dataManager.getBookings();

        tableData = prepareTableData(bookings);

        bikeTable = new JTable(tableData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Only the "Action" column is editable
            }
        };

        bikeTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        bikeTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), frame, this, bookings));

        JScrollPane scrollPane = new JScrollPane(bikeTable);

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new RenterUI();
            frame.dispose();
        });

        panel.add(backButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private Object[][] prepareTableData(List<BookingInfo> bookings) {
        Object[][] data = new Object[bookings.size()][6];
        for (int i = 0; i < bookings.size(); i++) {
            BookingInfo booking = bookings.get(i);
            data[i][0] = booking.getCustomerName();
            data[i][1] = booking.getBikeModel();
            data[i][2] = booking.getRentalDate();
            data[i][3] = booking.getRentalDuration();
            data[i][4] = booking.isAvailable() ? "Available" : "Unavailable";
            data[i][5] = "View Details";
        }
        return data;
    }

    public void refreshTableData() {
        List<BookingInfo> bookings = dataManager.getBookings();
        for (int i = 0; i < bookings.size(); i++) {
            BookingInfo booking = bookings.get(i);
            tableData[i][4] = booking.isAvailable() ? "Available" : "Unavailable"; // Update availability
        }
        ((javax.swing.table.AbstractTableModel) bikeTable.getModel()).fireTableDataChanged();
    }
}

