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
        button.addActionListener(e -> handleButtonClick());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        button.setText(label);
        currentRow = row;
        return button;
    }

    private void handleButtonClick() {
        if (currentRow >= 0 && currentRow < bookings.size()) {
            BookingInfo booking = bookings.get(currentRow);
            new BikeDetailsUI(parentFrame, "Bike Details", booking, browseBikesUI);
            fireEditingStopped();
        } else {
            JOptionPane.showMessageDialog(parentFrame, "Invalid row selection.");
        }
    }

    @Override
    public Object getCellEditorValue() {
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
