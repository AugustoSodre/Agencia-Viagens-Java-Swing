package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import control.PacoteController;
import model.PacoteViagem;

public class CadastrarPacote extends JFrame {

    private JTextField txtNome, txtDestino, txtDuracao, txtPreco, txtTipo;
    private JTextArea txtDescricao;
    private JButton btnSalvar, btnLimpar, btnCancelar;
    private JLabel lblTipo;
    private TelaPacote telaPacote;
    private PacoteController controller = new PacoteController();

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(248, 249, 250);
    private static final Color DARK_GRAY = new Color(52, 58, 64);

    public CadastrarPacote(TelaPacote telaPacote) {
        this.telaPacote = telaPacote;
        setTitle("Cadastro de Pacote - Agência de Viagens");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        setupModernUI();
        initComponents();
        setupLayout();
        setupEvents();

        setResizable(false); 
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
        txtNome = createModernTextField(20, "Digite o nome do pacote");
        txtDestino = createModernTextField(20, "Digite o destino");
        txtDuracao = createModernTextField(5, "Duração em dias");
        txtPreco = createModernTextField(10, "Preço do pacote");
        txtTipo = createModernTextField(10, "Tipo (aventura, cultural, luxo, etc.)");
        txtDescricao = new JTextArea(4, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescricao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        lblTipo = createModernLabel("Tipo:*");

        btnSalvar = createModernButton("Salvar", SUCCESS_COLOR, "Salvar o pacote");
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
            "Dados do Pacote",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            PRIMARY_COLOR
        );
        panel.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 15, 12, 15);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createModernLabel("Nome:*"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(createModernLabel("Destino:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtDestino, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(createModernLabel("Duração (dias):*"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtDuracao, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(createModernLabel("Preço:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtPreco, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(lblTipo, gbc);
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(txtTipo, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 1; gbc.fill = GridBagConstraints.NONE;
        panel.add(createModernLabel("Descrição:*"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        JScrollPane scroll = new JScrollPane(txtDescricao);
        scroll.setPreferredSize(new Dimension(300, 80));
        panel.add(scroll, gbc);

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
        btnSalvar.addActionListener(e -> salvarPacote());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());

        getRootPane().registerKeyboardAction(e -> dispose(),
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void salvarPacote() {
        String nome = txtNome.getText().trim();
        String destino = txtDestino.getText().trim();
        String duracaoStr = txtDuracao.getText().trim();
        String precoStr = txtPreco.getText().trim();
        String tipo = txtTipo.getText().trim().toLowerCase();
        String descricao = txtDescricao.getText().trim();

        int duracao = 0;
        double preco = 0.0;

        try {
            duracao = Integer.parseInt(duracaoStr);
        } catch (NumberFormatException e) {
            showModernMessage("Duração deve ser um número inteiro!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtDuracao.requestFocus();
            return;
        }

        try {
            preco = Double.parseDouble(precoStr.replace(",", "."));
        } catch (NumberFormatException e) {
            showModernMessage("Preço deve ser um número!", "Validação", JOptionPane.WARNING_MESSAGE);
            txtPreco.requestFocus();
            return;
        }

        if (!controller.validarPacote(nome, destino, duracao, preco, descricao, tipo)) {
            showModernMessage("Preencha todos os campos corretamente!", "Validação", JOptionPane.WARNING_MESSAGE);
            return;
        }

        PacoteViagem pacote = controller.criarPacote(nome, destino, duracao, preco, descricao, tipo);
        controller.salvarPacote(pacote);

        if (telaPacote != null) {
            telaPacote.reloadData();
        }
        limparCampos();
        setVisible(false);
    }

    private void showModernMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDestino.setText("");
        txtDuracao.setText("");
        txtPreco.setText("");
        txtTipo.setText("");
        txtDescricao.setText("");
        txtNome.setFocusable(true);
        txtNome.requestFocus();
        
        txtNome.setEnabled(true);
        txtNome.setEditable(true);

        txtDestino.setEnabled(true);
        txtDestino.setEditable(true);

        txtDuracao.setEnabled(true);
        txtDuracao.setEditable(true);

        txtPreco.setEnabled(true);
        txtPreco.setEditable(true);

        txtTipo.setEnabled(true);
        txtTipo.setEditable(true);

        txtDescricao.setEnabled(true);
        txtDescricao.setEditable(true);

    }
}