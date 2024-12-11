import javax.swing.*;
import java.awt.*;
import java.util.List;

class DeleteButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JFrame parentFrame;
    private List<BookingInfo> bikes;
    private ManageBikesUI manageBikesUI;
    private int currentRow;

    public DeleteButtonEditor(JCheckBox checkBox, JFrame parentFrame, List<BookingInfo> bikes, ManageBikesUI manageBikesUI) {
        super(checkBox);
        this.parentFrame = parentFrame;
        this.bikes = bikes;
        this.manageBikesUI = manageBikesUI;
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
            int confirm = JOptionPane.showConfirmDialog(parentFrame, "Are you sure you want to delete this bike?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bikes.remove(currentRow);
                BookingDataManager.getInstance().saveBookingData();
                manageBikesUI.refreshTableData();
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
