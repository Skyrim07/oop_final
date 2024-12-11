import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;

class BrowseBikesUI {
    private JTable bikeTable;
    private Object[][] tableData;
    private String[] columnNames = {"Brand", "Type", "Price", "Availability", "Action"};
    private BookingDataManager dataManager;
    private List<BookingInfo> bookings;
    private JTextField brandField;
    private JComboBox<String> typeComboBox;
    private JCheckBox availabilityCheckBox;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    JFrame frame;

    public BrowseBikesUI() {
        frame = new JFrame("Browse Bikes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        dataManager = BookingDataManager.getInstance();
        bookings = dataManager.getBookings();

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel searchPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Filters"));

        searchPanel.add(new JLabel("Brand:"));
        brandField = new JTextField();
        searchPanel.add(brandField);

        searchPanel.add(new JLabel("Type:"));
        typeComboBox = new JComboBox<>(new String[]{"All", "Mountain Bike", "Electric Bike", "Casual Bike"});
        searchPanel.add(typeComboBox);

        searchPanel.add(new JLabel("Availability:"));
        availabilityCheckBox = new JCheckBox("Available Only");
        searchPanel.add(availabilityCheckBox);

        searchPanel.add(new JLabel("Min Price:"));
        minPriceField = new JTextField();
        searchPanel.add(minPriceField);

        searchPanel.add(new JLabel("Max Price:"));
        maxPriceField = new JTextField();
        searchPanel.add(maxPriceField);

        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> applyFilters());
        searchPanel.add(searchButton);

        mainPanel.add(searchPanel, BorderLayout.NORTH);

        tableData = prepareTableData(bookings);
        bikeTable = new JTable(tableData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        bikeTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        bikeTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), frame, this, bookings));


        JScrollPane scrollPane = new JScrollPane(bikeTable);
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
            data[i][3] = booking.isAvailable() ? "Available" : "Unavailable";
            data[i][4] = "Details & Booking";
        }
        return data;
    }

    private void applyFilters() {
        String brand = brandField.getText().trim().toLowerCase();
        String type = (String) typeComboBox.getSelectedItem();
        boolean availableOnly = availabilityCheckBox.isSelected();
        String minPriceText = minPriceField.getText().trim();
        String maxPriceText = maxPriceField.getText().trim();

        double minPrice = minPriceText.isEmpty() ? Double.MIN_VALUE : Double.parseDouble(minPriceText);
        double maxPrice = maxPriceText.isEmpty() ? Double.MAX_VALUE : Double.parseDouble(maxPriceText);

        List<BookingInfo> filteredBookings = FilterData(brand, type, availableOnly, minPrice, maxPrice);

        if (!filteredBookings.isEmpty()) {
            tableData = prepareTableData(filteredBookings);
        } else {
            tableData = new Object[0][6];
        }

        bikeTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        });

        bikeTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        bikeTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), frame, this, bookings));
    }

    public List<BookingInfo> FilterData(String brand, String type, boolean availableOnly, double minPrice, double maxPrice)
    {
        List<BookingInfo> filteredBookings = new ArrayList<>();
        for (BookingInfo booking : bookings) {
            boolean matches = true;

            if (!brand.isEmpty() && !booking.getBrand().toLowerCase().contains(brand)) {
                matches = false;
            }

            if (!type.equals("All") && !booking.getType().toLowerCase().contains(type.toLowerCase())) {
                matches = false;
            }

            if (availableOnly && !booking.isAvailable()) {
                matches = false;
            }

            double price = booking.getPrice();
            if (price < minPrice || price > maxPrice) {
                matches = false;
            }

            if (matches) {
                filteredBookings.add(booking);
            }
        }
        return filteredBookings;
    }


    public void refreshTableData() {
        tableData = prepareTableData(bookings);

        bikeTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 5;
            }
        });

        bikeTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
        bikeTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox(), frame, this, bookings));

    }

}
