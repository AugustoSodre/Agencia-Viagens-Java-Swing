package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import control.PedidoController;
import model.Pedido;

public class CadastrarPedido extends JFrame {

    private JTextField txtIdCliente, txtIdPacote, txtData;
    private JButton btnSalvar, btnLimpar, btnCancelar;
    private PedidoController controller = new PedidoController();
    private TelaPedido telaPedido;

    // Cores modernas (mesmo padrão das outras telas)
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(248, 249, 250);
    private static final Color DARK_GRAY = new Color(52, 58, 64);

    public CadastrarPedido(TelaPedido telaPedido) {
        this.telaPedido = telaPedido;
        setTitle("Cadastro de Pedido - Agência de Viagens");
        setSize(540, 370);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setupModernUI();
        initComponents();
        setupLayout();
        setupEvents();
    }

    private void setupModernUI() {
        setBackground(LIGHT_GRAY);
        Font bodyFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 14);
        UIManager.put("Button.font", bodyFont);
        UIManager.put("Label.font", bodyFont);
        UIManager.put("TextField.font", bodyFont);
        UIManager.put("TitledBorder.font", headerFont);
    }

    private void initComponents() {
        txtIdCliente = createModernTextField(10, "ID do Cliente");
        txtIdPacote = createModernTextField(10, "ID do Pacote");
        txtData = createModernTextField(10, "Data (YYYY-MM-DD)");

        btnSalvar = createModernButton("Salvar", SUCCESS_COLOR, "Salvar o pedido");
        btnLimpar = createModernButton("Limpar", WARNING_COLOR, "Limpar todos os campos");
        btnCancelar = createModernButton("Cancelar", DANGER_COLOR, "Cancelar e fechar a janela");

        Dimension btnSize = new Dimension(140, 40);
        btnSalvar.setPreferredSize(btnSize);
        btnLimpar.setPreferredSize(btnSize);
        btnCancelar.setPreferredSize(btnSize);
    }

    private JTextField createModernTextField(int columns, String tooltip) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(DARK_GRAY);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(250, 36));
        field.setToolTipText(tooltip);

        // Efeito de foco moderno
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                    BorderFactory.createEmptyBorder(7, 12, 7, 12)
                ));
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                    BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        return field;
    }

    private JLabel createModernLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(DARK_GRAY);
        return label;
    }

    private JButton createModernButton(String text, Color backgroundColor, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);

        // Efeito hover
        Color hoverColor = backgroundColor.brighter();
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(LIGHT_GRAY);
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelFormulario = createModernFormPanel();
        JPanel painelBotoes = createModernButtonPanel();

        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JPanel createModernFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);

        TitledBorder border = new TitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ),
            "Dados do Pedido",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            PRIMARY_COLOR
        );
        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // ID Cliente
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createModernLabel("ID Cliente:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtIdCliente, gbc);

        // ID Pacote
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(createModernLabel("ID Pacote:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtIdPacote, gbc);

        // Data
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(createModernLabel("Data:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtData, gbc);

        return panel;
    }

    private JPanel createModernButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panel.setBackground(LIGHT_GRAY);
        panel.add(btnSalvar);
        panel.add(btnLimpar);
        panel.add(btnCancelar);
        return panel;
    }

    private void setupEvents() {
        btnSalvar.addActionListener(e -> salvarPedido());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());

        // ESC fecha a janela
        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void salvarPedido() {
        String idClienteStr = txtIdCliente.getText().trim();
        String idPacoteStr = txtIdPacote.getText().trim();
        String data = txtData.getText().trim();

        if (!validarCampos()) return;

        int idCliente, idPacote;
        try {
            idCliente = Integer.parseInt(idClienteStr);
        } catch (NumberFormatException e) {
            showModernMessage("ID Cliente deve ser um número inteiro!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtIdCliente.requestFocus();
            return;
        }
        try {
            idPacote = Integer.parseInt(idPacoteStr);
        } catch (NumberFormatException e) {
            showModernMessage("ID Pacote deve ser um número inteiro!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtIdPacote.requestFocus();
            return;
        }
        if (!data.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            showModernMessage("Data deve estar no formato YYYY-MM-DD!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtData.requestFocus();
            return;
        }

        Pedido pedido = new Pedido(idCliente, idPacote, data);
        controller.salvarPedido(pedido);

        if (telaPedido != null) {
            telaPedido.reloadData();
        }
        limparCampos();
        setVisible(false);
    }

    private boolean validarCampos() {
        if (txtIdCliente.getText().trim().isEmpty()) {
            showValidation("O campo ID Cliente é obrigatório!", txtIdCliente);
            return false;
        }
        if (txtIdPacote.getText().trim().isEmpty()) {
            showValidation("O campo ID Pacote é obrigatório!", txtIdPacote);
            return false;
        }
        if (txtData.getText().trim().isEmpty()) {
            showValidation("O campo Data é obrigatório!", txtData);
            return false;
        }
        return true;
    }

    private void showValidation(String msg, JTextField field) {
        showModernMessage(msg, "Validação", JOptionPane.WARNING_MESSAGE);
        SwingUtilities.invokeLater(() -> {
            field.requestFocusInWindow();
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(DANGER_COLOR, 2, true),
                BorderFactory.createEmptyBorder(7, 12, 7, 12)
            ));
        });
    }

    private void showModernMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void limparCampos() {
        txtIdCliente.setText("");
        txtIdPacote.setText("");
        txtData.setText("");
        txtIdCliente.requestFocus();

        JTextField[] campos = {txtIdCliente, txtIdPacote, txtData};
        for (JTextField campo : campos) {
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        }
    }
}