package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import control.ClienteController;
import dao.SelectAll;
import view.helpers.AbstractTela;
import view.helpers.ButtonXEditor;
import view.helpers.ButtonXRenderer;

public class TelaCliente extends AbstractTela<Object> {
    private MainFrame mainFrame;
    private JTextField idSearchField;
    private JButton idSearchButton;
    private JButton refreshButton;
    private JLabel statusLabel;
    private ClienteController clienteController = new ClienteController();
    
    // Nova paleta de cores elegante
    private static final Color PRIMARY = new Color(150, 60, 70);      // #963C46
    private static final Color SECONDARY = new Color(225, 188, 190);  // #E1BCBE
    private static final Color BACKGROUND = new Color(255, 245, 238); // #FFF5EE
    private static final Color TEXT = new Color(45, 45, 45);          // #2D2D2D
    private static final Color SUCCESS = new Color(71, 191, 145);     // #47BF91
    private static final Color WARNING = new Color(255, 127, 80);     // #FF7F50
    private static final Color ERROR = new Color(192, 57, 43);        // #C0392B
    private static final Color BORDER = new Color(217, 217, 217);         // #D9D9D9

    public TelaCliente(MainFrame mainFrame) {
        super("Clientes");
        this.mainFrame = mainFrame;
        setupModernUI();
        addSearchComponents();
    }

    private void setupModernUI() {
        // Configurar look and feel moderno
        try {
            UIManager.setLookAndFeel(UIManager.getLookAndFeel());
        } catch (Exception e) {
            // Fallback para default
        }
        
        // Aplicar tema moderno
        applyModernTheme();
    }

    private void applyModernTheme() {
        setBackground(BACKGROUND);
        
        // Customizar fontes
        Font headerFont = new Font("Segoe UI", Font.BOLD, 16);
        Font bodyFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font smallFont = new Font("Segoe UI", Font.PLAIN, 11);
        
        UIManager.put("Button.font", bodyFont);
        UIManager.put("Label.font", bodyFont);
        UIManager.put("TextField.font", bodyFont);
        UIManager.put("Table.font", bodyFont);
        UIManager.put("TableHeader.font", headerFont);
    }

    @Override
    protected DefaultTableModel createTableModel() {
        SelectAll selectAll = new SelectAll();
        DefaultTableModel model = selectAll.listarTodosComoTableModel("cliente");
        model.addColumn("Ações");
        updateStatusLabel(model.getRowCount());
        return filterModel(model);
    }

    @Override
    protected void customizeTable(JTable table) {
        // Configurar renderização moderna da tabela
        setupModernTable(table);
        
        // Configurar coluna de ações
        table.getColumn("Ações").setCellRenderer(new ButtonXRenderer());
        table.getColumn("Ações").setCellEditor(new ButtonXEditor(new JCheckBox(), model, table, this, "cliente"));
        
        // Definir larguras das colunas
        int[] larguras = {60, 150, 120, 200, 100, 150, 100};
        for (int i = 0; i < larguras.length && i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(larguras[i]);
        }
    }

    private void setupModernTable(JTable table) {
    	//Centraliza todos menos a última coluna "Ações"
    	DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            String colName = table.getColumnName(i);
            if (!colName.equals("Ações")) {
                table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
    	
    	
        // Cores e estilos modernos
        table.setBackground(Color.WHITE);
        table.setForeground(TEXT);
        table.setSelectionBackground(new Color(230, 247, 255));
        table.setSelectionForeground(TEXT);
        table.setGridColor(new Color(230, 230, 230));
        table.setRowHeight(35);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        
        // Fonte e alinhamento
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // Header moderno
        JTableHeader header = table.getTableHeader();
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));
        header.setBorder(BorderFactory.createEmptyBorder());
        
        // Efeito hover (básico)
        table.setRowSelectionAllowed(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Centraliza coluna "Ações"
        //setupModernTable(table);
        table.getColumn("Ações").setCellRenderer(new ButtonXRenderer());
        table.getColumn("Ações").setCellEditor(new ButtonXEditor(new JCheckBox(), model, table, this, "cliente"));
        int[] larguras = {60, 150, 120, 200, 100, 150, 100};
        for (int i = 0; i < larguras.length && i < table.getColumnModel().getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(larguras[i]);
        }
    }

    @Override
    protected void onAddClicked(java.awt.event.ActionEvent e) {
        // Animação de click (visual feedback)
        JButton source = (JButton) e.getSource();
        animateButtonClick(source);
        
        new CadastrarCliente(this).setVisible(true);
    }

    private void animateButtonClick(JButton button) {
        Color originalColor = button.getBackground();
        button.setBackground(SUCCESS.brighter());
        
        Timer timer = new Timer(100, evt -> button.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected String getAddButtonText() {
        return "Novo Cliente";
    }

    @Override
    protected String getSearchTooltip() {
        return "Digite para buscar por nome, email ou documento";
    }

    @Override
    protected String getAddButtonTooltip() {
        return "Adicionar um novo cliente ao sistema";
    }

    @Override
    protected Icon getAddButtonIcon() {
        return createModernIcon("+", SUCCESS);
    }

    private Icon createModernIcon(String symbol, Color color) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                g2.drawString(symbol, x, y + 12);
                g2.dispose();
            }

            @Override
            public int getIconWidth() { return 16; }

            @Override
            public int getIconHeight() { return 16; }
        };
    }

    @Override
    public void reloadData() {
        // Mostrar indicador de carregamento
        showLoadingIndicator(true);
        
        SwingUtilities.invokeLater(() -> {
            DefaultTableModel newModel = createTableModel();
            table.setModel(newModel);
            customizeTable(table);
            showLoadingIndicator(false);
            
            // Feedback visual
            statusLabel.setText("Dados atualizados • " + newModel.getRowCount() + " registros");
            statusLabel.setForeground(SUCCESS);
        });
    }

    private void showLoadingIndicator(boolean show) {
        if (show) {
            statusLabel.setText("Carregando dados...");
            statusLabel.setForeground(PRIMARY);
        }
    }

    private void addSearchComponents() {
        JPanel searchPanel = getSearchPanel();
        searchPanel.setBackground(BACKGROUND);
        
        // Painel principal de busca com design moderno
        JPanel modernSearchPanel = createModernSearchPanel();
        
        // Adicionar com espaçamento
        searchPanel.add(Box.createHorizontalStrut(15));
        searchPanel.add(modernSearchPanel);
        
        // Status label no final
        statusLabel = new JLabel("Carregando...");
        statusLabel.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        statusLabel.setForeground(TEXT);
        searchPanel.add(Box.createHorizontalGlue());
        searchPanel.add(statusLabel);
        searchPanel.add(Box.createHorizontalStrut(15));
    }

    private JPanel createModernSearchPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        // Campo de busca por ID moderno
        idSearchField = createModernTextField();
        idSearchField.setPreferredSize(new Dimension(100, 32));
        idSearchField.setMaximumSize(new Dimension(100, 32));

        // Botões modernos
        idSearchButton = createModernButton("Buscar", PRIMARY);
        refreshButton = createModernButton("Limpar", WARNING);

        // Eventos
        setupSearchEvents();

        // Layout
        panel.add(new JLabel("ID:"));
        panel.add(Box.createHorizontalStrut(5));
        panel.add(idSearchField);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(idSearchButton);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(refreshButton);

        return panel;
    }

    private JTextField createModernTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        
        // Placeholder effect
        field.setForeground(Color.GRAY);
        field.setText("Digite o ID...");
        
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals("Digite o ID...")) {
                    field.setText("");
                    field.setForeground(TEXT);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY, 2),
                    BorderFactory.createEmptyBorder(4, 8, 4, 8)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText("Digite o ID...");
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 8, 5, 8)
                ));
            }
        });
        
        return field;
    }

    private JButton createModernButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efeito hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private void setupSearchEvents() {
        // Enter key para buscar
        idSearchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchById();
                }
            }
        });
        
        idSearchButton.addActionListener(e -> searchById());
        refreshButton.addActionListener(e -> {
            idSearchField.setText("Digite o ID...");
            idSearchField.setForeground(Color.GRAY);
            reloadData();
        });
    }

    private void searchById() {
        String idText = idSearchField.getText().trim();

        // Verificar placeholder
        if (idText.equals("Digite o ID...") || idText.isEmpty()) {
            showModernMessage("Digite um ID para buscar", "Campo obrigatório", JOptionPane.WARNING_MESSAGE);
            idSearchField.requestFocus();
            return;
        }

        if (!idText.matches("\\d+")) {
            showModernMessage("Digite apenas números", "ID inválido", JOptionPane.ERROR_MESSAGE);
            idSearchField.requestFocus();
            return;
        }

        // Mostrar feedback de busca
        statusLabel.setText("Buscando cliente ID " + idText + "...");
        statusLabel.setForeground(PRIMARY);

        int id = Integer.parseInt(idText);
        DefaultTableModel model = clienteController.buscarClientePorId(id);

        if (model != null && model.getRowCount() > 0) {
            model.addColumn("Ações");
            table.setModel(filterModel(model));
            customizeTable(table);
            statusLabel.setText("Cliente encontrado!");
            statusLabel.setForeground(SUCCESS);
        } else {
            statusLabel.setText("Cliente não encontrado");
            statusLabel.setForeground(ERROR);
            showModernMessage("Nenhum cliente encontrado com o ID " + id,
                            "Busca sem resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void showModernMessage(String message, String title, int messageType) {
        JOptionPane pane = new JOptionPane(message, messageType);
        JDialog dialog = pane.createDialog(this, title);
        dialog.setModal(true);
        dialog.setVisible(true);
    }

    private void updateStatusLabel(int recordCount) {
        if (statusLabel != null) {
            statusLabel.setText(recordCount + " cliente" + (recordCount != 1 ? "s" : "") + " encontrado" + (recordCount != 1 ? "s" : ""));
            statusLabel.setForeground(TEXT);
        }
    }
}