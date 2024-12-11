import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

class BookingHistoryUI {
    private JFrame frame;
    private JTable bookingTable;
    private Object[][] tableData;
    private String[] columnNames = {"Brand", "Type", "Price", "Rental Date", "Action"};
    private List<BookingInfo> rentedBikes;

    public BookingHistoryUI() {
        frame = new JFrame("Booking History");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        List<BookingInfo> bookings = BookingDataManager.getInstance().getBookings();

        // Filter only rented bikes
        rentedBikes = bookings.stream()
                .filter(booking -> !booking.getRentalDate().isEmpty() && !booking.isBookingPending())
                .collect(Collectors.toList());

        JPanel mainPanel = new JPanel(new BorderLayout());

        tableData = prepareTableData(rentedBikes);
        bookingTable = new JTable(tableData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        bookingTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        bookingTable.getColumn("Action").setCellEditor(new BookingManageButtonEditor(new JCheckBox(), frame, rentedBikes, this));

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new RenterUI();
            frame.dispose();
        });
        mainPanel.add(backButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private Object[][] prepareTableData(List<BookingInfo> bookings) {
        Object[][] data = new Object[bookings.size()][5];
        for (int i = 0; i < bookings.size(); i++) {
            BookingInfo booking = bookings.get(i);
            data[i][0] = booking.getBrand();
            data[i][1] = booking.getType();
            data[i][2] = booking.getPrice();
            data[i][3] = booking.getRentalDate();
            data[i][4] = "Manage";
        }
        return data;
    }

    public void refreshTableData() {
        rentedBikes = BookingDataManager.getInstance().getBookings().stream()
                .filter(booking -> !booking.getRentalDate().isEmpty())
                .collect(Collectors.toList());
        tableData = prepareTableData(rentedBikes);
        bookingTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        bookingTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        bookingTable.getColumn("Action").setCellEditor(new BookingManageButtonEditor(new JCheckBox(), frame, rentedBikes, this));
    }
}

