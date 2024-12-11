import javax.swing.*;
import java.awt.*;
import java.util.List;

class ModifyButtonEditor extends DefaultCellEditor {
    private JButton button;
    private String label;
    private boolean isPushed;
    private JFrame parentFrame;
    private List<BookingInfo> bikes;
    private ManageBikesUI manageBikesUI;
    private int currentRow;

    public ModifyButtonEditor(JCheckBox checkBox, JFrame parentFrame, List<BookingInfo> bikes, ManageBikesUI manageBikesUI) {
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
            new ModifyBikeUI(bike, manageBikesUI);
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
