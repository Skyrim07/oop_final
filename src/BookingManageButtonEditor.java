import javax.swing.*;
import java.awt.*;
import java.util.List;

class BookingManageButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JFrame parentFrame;
    private List<BookingInfo> bookings;
    private BookingHistoryUI bookingHistoryUI;
    private int currentRow;

    public BookingManageButtonEditor(JCheckBox checkBox, JFrame parentFrame, List<BookingInfo> bookings, BookingHistoryUI bookingHistoryUI) {
        super(checkBox);
        this.parentFrame = parentFrame;
        this.bookings = bookings;
        this.bookingHistoryUI = bookingHistoryUI;
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
            BookingInfo booking = bookings.get(currentRow);
            new ManageBookingUI(parentFrame, "Manage Booking", booking, bookingHistoryUI);
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
