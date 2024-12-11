import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class ManageRentalRequestsUI {
    private JFrame frame;
    private JTable requestTable;
    private Object[][] tableData;
    private String[] columnNames = {"Brand", "Type", "Price", "Rental Date", "Actions"};
    private BookingDataManager dataManager;

    public ManageRentalRequestsUI() {
        frame = new JFrame("Manage Rental Requests");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(10, 10));

        dataManager = BookingDataManager.getInstance();
        List<BookingInfo> pendingRequests = getPendingRequests();

        tableData = prepareTableData(pendingRequests);
        requestTable = new JTable(tableData, columnNames) {
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only Actions column is editable
            }
        };

        requestTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        requestTable.getColumn("Actions").setCellEditor(new RentalRequestButtonEditor(new JCheckBox(), frame, pendingRequests, this));

        JScrollPane scrollPane = new JScrollPane(requestTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new AdminUI();
            frame.dispose();
        });
        frame.add(backButton, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private List<BookingInfo> getPendingRequests() {
        return dataManager.getBookings().stream()
                .filter(BookingInfo::isBookingPending)
                .collect(Collectors.toList());
    }

    private Object[][] prepareTableData(List<BookingInfo> requests) {
        Object[][] data = new Object[requests.size()][5];
        for (int i = 0; i < requests.size(); i++) {
            BookingInfo request = requests.get(i);
            data[i][0] = request.getBrand();
            data[i][1] = request.getType();
            data[i][2] = "$" + request.getPrice();
            data[i][3] = request.getRentalDate().isEmpty() ? "N/A" : request.getRentalDate();
            data[i][4] = "Approve/Reject";
        }
        return data;
    }

    public void refreshTableData() {
        List<BookingInfo> pendingRequests = getPendingRequests();
        tableData = prepareTableData(pendingRequests);
        requestTable.setModel(new javax.swing.table.DefaultTableModel(tableData, columnNames));
        requestTable.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        requestTable.getColumn("Actions").setCellEditor(new RentalRequestButtonEditor(new JCheckBox(), frame, pendingRequests, this));
    }
}
