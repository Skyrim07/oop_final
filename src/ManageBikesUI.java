import javax.swing.*;
import java.awt.*;
import java.util.List;

class ManageBikesUI {
    private JFrame frame;
    private JTable bikeTable;
    private Object[][] tableData;
    private String[] columnNames = {"Brand", "Type", "Price", "Modify", "Delete"};
    private BookingDataManager dataManager;

    public ManageBikesUI() {
        frame = new JFrame("Manage Bikes");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(10, 10));

        dataManager = BookingDataManager.getInstance();
        List<BookingInfo> bikes = dataManager.getBookings();

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Bike");
        addButton.addActionListener(e -> new AddBikeUI(this));
        topPanel.add(addButton);

        tableData = prepareTableData(bikes);
        bikeTable = new JTable(tableData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4;
            }
        };

        bikeTable.getColumn("Modify").setCellRenderer(new ButtonRenderer());
        bikeTable.getColumn("Modify").setCellEditor(new ModifyButtonEditor(new JCheckBox(), frame, bikes, this));

        bikeTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        bikeTable.getColumn("Delete").setCellEditor(new DeleteButtonEditor(new JCheckBox(), frame, bikes, this));

        JScrollPane scrollPane = new JScrollPane(bikeTable);
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new AdminUI();
            frame.dispose();
        });

        frame.add(backButton, BorderLayout.SOUTH);


        frame.setVisible(true);
    }

    private Object[][] prepareTableData(List<BookingInfo> bikes) {
        Object[][] data = new Object[bikes.size()][5];
        for (int i = 0; i < bikes.size(); i++) {
            BookingInfo bike = bikes.get(i);
            data[i][0] = bike.getBrand();
            data[i][1] = bike.getType();
            data[i][2] = "$" + bike.getPrice();
            data[i][3] = "Modify";
            data[i][4] = "Delete";
        }
        return data;
    }

    public void refreshTableData() {
        List<BookingInfo> bikes = dataManager.getBookings();
        tableData = prepareTableData(bikes);
        bikeTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        bikeTable.getColumn("Modify").setCellRenderer(new ButtonRenderer());
        bikeTable.getColumn("Modify").setCellEditor(new ModifyButtonEditor(new JCheckBox(), frame, bikes, this));

        bikeTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        bikeTable.getColumn("Delete").setCellEditor(new DeleteButtonEditor(new JCheckBox(), frame, bikes, this));
    }
}

