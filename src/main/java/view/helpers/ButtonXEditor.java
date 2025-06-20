package view.helpers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.SelectAll;
import dao.deletes.DeleteCliente;

public class ButtonXEditor extends DefaultCellEditor {
    private JButton button;
    private JTable table;
    private DefaultTableModel model;
    private int selectedRow;

    public ButtonXEditor(JCheckBox checkBox, DefaultTableModel model, JTable table, AbstractTela<?> tela) {
        super(checkBox);
        this.table = table;
        this.model = model;

        button = new JButton("X");
        button.setForeground(Color.RED);
        button.addActionListener(e -> deleteRow(tela));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.selectedRow = row;
        return button;
    }

    private void deleteRow(AbstractTela<?> tela) {
        int idCliente = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

        // Chama DAO para deletar do banco
        DeleteCliente.delete(idCliente);

        // Query novamente
        tela.reloadData();
    }

    @Override
    public Object getCellEditorValue() {
        return "X";
    }
}
