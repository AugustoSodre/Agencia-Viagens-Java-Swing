// File: agencia_viagens/src/main/java/view/helpers/AbstractTela.java
package view.helpers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public abstract class AbstractTela<T> extends JPanel {
    protected JTable table;
    protected DefaultTableModel model;
    protected JButton btnAdd;
    protected JTextField searchField;
    protected JLabel lblTitle;
    protected JPanel searchPanel; // Added field

    public AbstractTela(String title) {
        setLayout(new BorderLayout());

        // Top panel com BoxLayout para centralizar verticalmente
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));

        lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setAlignmentY(Component.CENTER_ALIGNMENT);
        topPanel.add(lblTitle);

        topPanel.add(Box.createHorizontalStrut(24));

        // searchPanel já é um JPanel com FlowLayout
        searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        searchPanel.setOpaque(false);
        searchField = new JTextField(18);
        searchField.setToolTipText(getSearchTooltip());
        searchPanel.add(searchField);
        searchPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        topPanel.add(searchPanel);

        topPanel.add(Box.createHorizontalGlue());

        btnAdd = new JButton(getAddButtonText(), getAddButtonIcon());
        btnAdd.setToolTipText(getAddButtonTooltip());
        btnAdd.setBackground(new Color(76, 175, 80));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFocusPainted(false);
        btnAdd.setAlignmentY(Component.CENTER_ALIGNMENT);
        btnAdd.setHorizontalAlignment(SwingConstants.CENTER); // <-- Centraliza o texto horizontalmente
        btnAdd.setHorizontalTextPosition(SwingConstants.CENTER); // <-- Centraliza o texto em relação ao ícone
        btnAdd.setVerticalTextPosition(SwingConstants.CENTER);   // <-- Centraliza o texto em relação ao ícone
        btnAdd.addActionListener(this::onAddClicked);
        topPanel.add(btnAdd);

        add(topPanel, BorderLayout.NORTH);

        // Table
        model = createTableModel();
        table = new JTable(model);
        customizeTable(table);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Search filter
        searchField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                reloadData();
            }
        });

        // Ajuste de alinhamento vertical
        topPanel.setPreferredSize(new Dimension(topPanel.getPreferredSize().width, 60));
        searchPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
        btnAdd.setAlignmentY(Component.CENTER_ALIGNMENT);
        lblTitle.setAlignmentY(Component.CENTER_ALIGNMENT);

        // Forçar alinhamento vertical dos componentes do topPanel
        for (Component comp : topPanel.getComponents()) {
            if (comp instanceof JComponent) {
                ((JComponent) comp).setAlignmentY(Component.CENTER_ALIGNMENT);
            }
        }
    }

    // Added getter for searchPanel
    public JPanel getSearchPanel() {
        return searchPanel;
    }

    protected abstract DefaultTableModel createTableModel();
    protected abstract void onAddClicked(ActionEvent e);
    protected abstract String getAddButtonText();
    protected abstract String getSearchTooltip();
    protected abstract String getAddButtonTooltip();
    protected Icon getAddButtonIcon() { return null; }
    protected void customizeTable(JTable table) {}
    public abstract void reloadData();

    // Generic filter for table models
    protected DefaultTableModel filterModel(DefaultTableModel model) {
        String filter = searchField != null ? searchField.getText().trim().toLowerCase() : "";
        if (filter.isEmpty()) return model;

        DefaultTableModel filtered = new DefaultTableModel();
        for (int i = 0; i < model.getColumnCount(); i++) {
            filtered.addColumn(model.getColumnName(i));
        }
        for (int i = 0; i < model.getRowCount(); i++) {
            boolean match = false;
            for (int j = 0; j < model.getColumnCount(); j++) {
                Object value = model.getValueAt(i, j);
                if (value != null && value.toString().toLowerCase().contains(filter)) {
                    match = true;
                    break;
                }
            }
            if (match) filtered.addRow(getRow(model, i));
        }
        return filtered;
    }

    protected Object[] getRow(DefaultTableModel model, int row) {
        Object[] rowData = new Object[model.getColumnCount()];
        for (int i = 0; i < model.getColumnCount(); i++) {
            rowData[i] = model.getValueAt(row, i);
        }
        return rowData;
    }
}
