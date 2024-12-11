import javax.swing.*;
import java.awt.*;
import java.util.List;

class RentalRequestButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JFrame parentFrame;
    private List<BookingInfo> requests;
    private ManageRentalRequestsUI manageRequestsUI;
    private int currentRow;

    public RentalRequestButtonEditor(JCheckBox checkBox, JFrame parentFrame, List<BookingInfo> requests, ManageRentalRequestsUI manageRequestsUI) {
        super(checkBox);
        this.parentFrame = parentFrame;
        this.requests = requests;
        this.manageRequestsUI = manageRequestsUI;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        currentRow = row;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            BookingInfo request = requests.get(currentRow);
            int action = JOptionPane.showOptionDialog(parentFrame, "Choose action for the request.", "Manage Request",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Approve", "Reject"}, null);
            if (action == 0) {
                request.setBookingPending(false);
                request.setAvailable(false);
                JOptionPane.showMessageDialog(parentFrame, "Request approved!");
            } else if (action == 1) {
                request.setBookingPending(false);
                JOptionPane.showMessageDialog(parentFrame, "Request rejected!");
            }
            BookingDataManager.getInstance().saveBookingData();
            manageRequestsUI.refreshTableData();
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
