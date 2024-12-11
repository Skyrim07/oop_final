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
        button.addActionListener(e -> fireEditingStopped());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        isPushed = true;
        currentRow = row;
        System.out.println("Row selected: " + currentRow); // Debug statement
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            System.out.println("View Details button clicked for row: " + currentRow);
            BookingInfo booking = bookings.get(currentRow);
            new BikeDetailsUI(parentFrame, "Bike Details", booking, browseBikesUI);
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
