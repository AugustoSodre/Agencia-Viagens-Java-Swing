package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import control.ClienteController;
import model.Cliente;

public class CadastrarCliente extends JFrame {

    private JTextField txtNome, txtTelefone, txtEmail, txtDocumento;
    private JRadioButton rbNacional, rbEstrangeiro;
    private ButtonGroup grupTipo;
    private JLabel lblDocumento;
    private JButton btnSalvar, btnLimpar, btnCancelar;
    private TelaCliente telaCliente;
    private ClienteController controller = new ClienteController();

    // Cores modernas - mesmas da TelaCliente
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(248, 249, 250);
    private static final Color DARK_GRAY = new Color(52, 58, 64);
    private static final Color ACCENT_COLOR = new Color(155, 89, 182);

    public CadastrarCliente(TelaCliente telaCliente) {
        this.telaCliente = telaCliente;
        setTitle("Cadastro de Cliente - Agência de Viagens");
        setSize(680, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        setupModernUI();
        initComponents();
        setupLayout();
        setupEvents();
    }

    private void setupModernUI() {
        // Aplicar tema moderno
        setBackground(LIGHT_GRAY);
        
        // Configurar fontes globais
        Font bodyFont = new Font("Segoe UI", Font.PLAIN, 13);
        Font headerFont = new Font("Segoe UI", Font.BOLD, 14);
        
        UIManager.put("Button.font", bodyFont);
        UIManager.put("Label.font", bodyFont);
        UIManager.put("TextField.font", bodyFont);
        UIManager.put("RadioButton.font", bodyFont);
        UIManager.put("TitledBorder.font", headerFont);
    }

    private void initComponents() {
        // Campos de texto modernos
        txtNome = createModernTextField(20, "Digite o nome completo do cliente");
        txtTelefone = createModernTextField(15, "Digite o telefone do cliente");
        txtEmail = createModernTextField(20, "Digite o email do cliente");
        txtDocumento = createModernTextField(15, "Digite o CPF no formato 000.000.000-00");

        // Radio buttons modernos
        rbNacional = createModernRadioButton("🇧🇷 Nacional", true);
        rbEstrangeiro = createModernRadioButton("🌍 Estrangeiro", false);
        grupTipo = new ButtonGroup();
        grupTipo.add(rbNacional);
        grupTipo.add(rbEstrangeiro);

        // Label do documento
        lblDocumento = createModernLabel("📄 CPF:*");

        // Botões modernos
        btnSalvar = createModernButton("💾 Salvar", SUCCESS_COLOR, "Salvar o cliente no sistema");
        btnLimpar = createModernButton("🧹 Limpar", WARNING_COLOR, "Limpar todos os campos");
        btnCancelar = createModernButton("❌ Cancelar", DANGER_COLOR, "Cancelar e fechar a janela");

        // Tamanho padrão dos botões
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
        field.setPreferredSize(new Dimension(field.getPreferredSize().width, 36));
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

    private JRadioButton createModernRadioButton(String text, boolean selected) {
        JRadioButton rb = new JRadioButton(text, selected);
        rb.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        rb.setForeground(DARK_GRAY);
        rb.setBackground(Color.WHITE);
        rb.setFocusPainted(false);
        rb.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Personalizar cor de seleção
        rb.setOpaque(false);
        
        return rb;
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
        
        // Painel principal com fundo moderno
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(LIGHT_GRAY);
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Painel do formulário com design moderno
        JPanel painelFormulario = createModernFormPanel();
        
        // Painel de botões moderno
        JPanel painelBotoes = createModernButtonPanel();

        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private JPanel createModernFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        
        // Border moderna com título estilizado
        TitledBorder border = new TitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ),
            "👤 Dados do Cliente",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            PRIMARY_COLOR
        );
        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.WEST;

        // Nome
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createModernLabel("👤 Nome:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtNome, gbc);

        // Telefone
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(createModernLabel("📞 Telefone:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtTelefone, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(createModernLabel("📧 Email:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtEmail, gbc);

        // Tipo de cliente
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(createModernLabel("🏠 Tipo:*"), gbc);

        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelTipo.setBackground(Color.WHITE);
        painelTipo.add(rbNacional);
        painelTipo.add(rbEstrangeiro);
        gbc.gridx = 1; gbc.gridwidth = 2;
        panel.add(painelTipo, gbc);

        // Documento (CPF/Passaporte)
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1;
        panel.add(lblDocumento, gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(txtDocumento, gbc);

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
        rbNacional.addActionListener(e -> {
            lblDocumento.setText("📄 CPF:*");
            txtDocumento.setToolTipText("Digite o CPF no formato 000.000.000-00");
            animateFieldChange(txtDocumento);
        });

        rbEstrangeiro.addActionListener(e -> {
            lblDocumento.setText("🛂 Passaporte:*");
            txtDocumento.setToolTipText("Digite o número do passaporte (6-9 caracteres alfanuméricos)");
            animateFieldChange(txtDocumento);
        });

        btnSalvar.addActionListener(e -> {
            animateButtonClick(btnSalvar);
            salvarCliente();
        });
        
        btnLimpar.addActionListener(e -> {
            animateButtonClick(btnLimpar);
            limparCampos();
        });
        
        btnCancelar.addActionListener(e -> {
            animateButtonClick(btnCancelar);
            dispose();
        });

        // ESC fecha a janela
        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void animateButtonClick(JButton button) {
        Color originalColor = button.getBackground();
        button.setBackground(originalColor.darker());
        
        Timer timer = new Timer(150, evt -> button.setBackground(originalColor));
        timer.setRepeats(false);
        timer.start();
    }

    private void animateFieldChange(JTextField field) {
        Color originalBorder = PRIMARY_COLOR;
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2, true),
            BorderFactory.createEmptyBorder(7, 12, 7, 12)
        ));
        
        Timer timer = new Timer(300, evt -> {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void salvarCliente() {
        String nome = txtNome.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();
        String documento = txtDocumento.getText().trim();
        boolean isNacional = rbNacional.isSelected();

        if (!validarCampos()) {
            return;
        }

        if (!controller.validarCliente(nome, telefone, email, documento, isNacional)) {
            showModernMessage("⚠️ Dados inválidos! Verifique os campos preenchidos.", 
                            "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente cliente = controller.criarCliente(nome, telefone, email, documento, isNacional);
        controller.salvarCliente(cliente);

        if (telaCliente != null) {
            telaCliente.reloadData();
        }
        
        limparCampos();
        setVisible(false);
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty()) {
            showValidation("❌ O campo Nome é obrigatório!", txtNome);
            return false;
        }
        if (txtTelefone.getText().trim().isEmpty()) {
            showValidation("❌ O campo Telefone é obrigatório!", txtTelefone);
            return false;
        }
        String email = txtEmail.getText().trim();
        if (email.isEmpty()) {
            showValidation("❌ O campo Email é obrigatório!", txtEmail);
            return false;
        }
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) {
            showValidation("❌ Por favor, digite um email válido!", txtEmail);
            return false;
        }
        String documento = txtDocumento.getText().trim();
        if (documento.isEmpty()) {
            String tipoDoc = rbNacional.isSelected() ? "CPF" : "Passaporte";
            showValidation("❌ O campo " + tipoDoc + " é obrigatório!", txtDocumento);
            return false;
        }
        if (rbNacional.isSelected()) {
            if (!documento.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$")) {
                showValidation("❌ CPF deve estar no formato 000.000.000-00!", txtDocumento);
                return false;
            }
        } else {
            if (!documento.matches("^[A-Za-z0-9]{6,9}$")) {
                showValidation("❌ Passaporte deve conter de 6 a 9 caracteres alfanuméricos!", txtDocumento);
                return false;
            }
        }
        return true;
    }

    private void showValidation(String msg, JTextField field) {
        showModernMessage(msg, "⚠️ Validação", JOptionPane.WARNING_MESSAGE);
        SwingUtilities.invokeLater(() -> {
            field.requestFocusInWindow();
            // Destacar campo com erro
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
        txtNome.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtDocumento.setText("");
        rbNacional.setSelected(true);
        lblDocumento.setText("📄 CPF:*");
        txtNome.requestFocus();
        
        // Resetar bordas dos campos
        JTextField[] campos = {txtNome, txtTelefone, txtEmail, txtDocumento};
        for (JTextField campo : campos) {
            campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
            ));
        }
    }
}