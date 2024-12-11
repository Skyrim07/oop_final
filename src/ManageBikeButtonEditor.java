import javax.swing.*;
import java.awt.*;
import java.util.List;

class ManageBikeButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JFrame parentFrame;
    private List<BookingInfo> bikes;
    private ManageBikesUI manageBikesUI;
    private int currentRow;

    public ManageBikeButtonEditor(JCheckBox checkBox, JFrame parentFrame, List<BookingInfo> bikes, ManageBikesUI manageBikesUI) {
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
            BookingInfo bike = bikes.get(currentRow);
            int action = JOptionPane.showOptionDialog(parentFrame, "Choose action for the bike.", "Manage Bike",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Modify", "Delete"}, null);
            if (action == 0) {
                new ModifyBikeUI(bike, manageBikesUI);
            } else if (action == 1) {
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
