import javax.swing.*;
import java.awt.*;
import java.util.List;

class ButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JFrame parentFrame;
    private BrowseBikesUI browseBikesUI;
    private List<BookingInfo> bookings;
    private int currentRow;

    public ButtonEditor(JCheckBox checkBox, JFrame parentFrame, BrowseBikesUI browseBikesUI, List<BookingInfo> bookings) {
        super(checkBox);
        this.parentFrame = parentFrame;
        this.browseBikesUI = browseBikesUI;
        this.bookings = bookings;
        button = new JButton();
        button.setOpaque(true);

        button.addActionListener(e -> {
            try {
                fireEditingStopped();
            } catch (IndexOutOfBoundsException ex) {
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
        if (isPushed) {
            if (currentRow >= 0 && currentRow < bookings.size()) {
                BookingInfo booking = bookings.get(currentRow);
                new BikeDetailsUI(parentFrame, "Bike Details", booking, browseBikesUI);
            }
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
