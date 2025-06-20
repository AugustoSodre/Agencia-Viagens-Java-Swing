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
import dao.deletes.DeletePacote;
import dao.deletes.DeletePedido;
import dao.deletes.DeleteServico;

public class ButtonXEditor extends DefaultCellEditor {
    private JButton button;
    private JTable table;
    private DefaultTableModel model;
    private int selectedRow;

    public ButtonXEditor(JCheckBox checkBox, DefaultTableModel model, JTable table, AbstractTela<?> tela, String tipo) {
        super(checkBox);
        this.table = table;
        this.model = model;

        button = new JButton("X");
        button.setForeground(Color.RED);
        button.addActionListener(e -> deleteRow(tela, tipo));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        this.selectedRow = row;
        return button;
    }

    private void deleteRow(AbstractTela<?> tela, String tipo) {
        int id = Integer.parseInt(table.getValueAt(selectedRow, 0).toString());

        // Chama DAO para deletar do banco
        switch (tipo.toLowerCase()) {
			case "cliente":
				DeleteCliente.delete(id);
				break;
			case "pacote":
				DeletePacote.delete(id);
				break;
			case "pedido":
				DeletePedido.delete(id);
				break;
			case "servico":
				DeleteServico.delete(id);
				break;
			default:
				throw new IllegalArgumentException("Tipo desconhecido: " + tipo);
		}
        

        // Query novamente
        tela.reloadData();
    }

    @Override
    public Object getCellEditorValue() {
        return "X";
    }
}
