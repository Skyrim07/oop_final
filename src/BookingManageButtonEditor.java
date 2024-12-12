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
        button.addActionListener(e -> {
            if (currentRow >= 0 && currentRow < bookings.size()) {
                try {
                    fireEditingStopped();
                } catch (IndexOutOfBoundsException ex) {
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (row >= 0 && row < bookings.size()) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            currentRow = row;
            isPushed = true;
        }
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed && currentRow >= 0 && currentRow < bookings.size()) {
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
