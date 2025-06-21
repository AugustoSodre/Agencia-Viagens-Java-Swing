package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import dao.Insert;

public class CadastrarPedidoServico extends JFrame {
    private JTextField txtIdPedido, txtIdServico;
    private JButton btnSalvar, btnLimpar, btnCancelar;
    private TelaPedido telaPedido;

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(248, 249, 250);
    private static final Color DARK_GRAY = new Color(52, 58, 64);

    public CadastrarPedidoServico(TelaPedido telaPedido) {
        this.telaPedido = telaPedido;
        setTitle("Vincular Serviço ao Pedido");
        setSize(540, 320);
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
        txtIdPedido = createModernTextField(10, "ID do Pedido");
        txtIdServico = createModernTextField(10, "ID do Serviço");

        btnSalvar = createModernButton("Vincular", SUCCESS_COLOR, "Vincular serviço ao pedido");
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
            "Dados do Pedido-Serviço",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            PRIMARY_COLOR
        );
        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // ID Pedido
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createModernLabel("ID Pedido:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtIdPedido, gbc);

        // ID Serviço
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(createModernLabel("ID Serviço:*"), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtIdServico, gbc);

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
        btnSalvar.addActionListener(e -> salvar());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());

        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void salvar() {
        String idPedidoStr = txtIdPedido.getText().trim();
        String idServicoStr = txtIdServico.getText().trim();

        if (!idPedidoStr.matches("\\d+")) {
            showModernMessage("ID Pedido deve ser um número inteiro.", "Validação", JOptionPane.WARNING_MESSAGE);
            txtIdPedido.requestFocus();
            return;
        }
        if (!idServicoStr.matches("\\d+")) {
            showModernMessage("ID Serviço deve ser um número inteiro.", "Validação", JOptionPane.WARNING_MESSAGE);
            txtIdServico.requestFocus();
            return;
        }

        int idPedido = Integer.parseInt(idPedidoStr);
        int idServico = Integer.parseInt(idServicoStr);

        Insert.inserirPedidoServico(idPedido, idServico);
        if (telaPedido != null) {
            telaPedido.reloadData();
        }
        limparCampos();
        setVisible(false);
    }

    private void showModernMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void limparCampos() {
        txtIdPedido.setText("");
        txtIdServico.setText("");
        txtIdPedido.requestFocus();

        JTextField[] campos = {txtIdPedido, txtIdServico};
        for (JTextField campo : campos) {
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        }
    }
}
