package view;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import control.ServicoController;
import model.Servico;

public class CadastrarServico extends JFrame {

    private JTextField txtNome, txtPreco;
    private JTextArea txtDescricao;
    private JButton btnSalvar, btnLimpar, btnCancelar;
    private ServicoController controller = new ServicoController();
    private TelaServico telaServico;

    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SUCCESS_COLOR = new Color(39, 174, 96);
    private static final Color WARNING_COLOR = new Color(241, 196, 15);
    private static final Color DANGER_COLOR = new Color(231, 76, 60);
    private static final Color LIGHT_GRAY = new Color(248, 249, 250);
    private static final Color DARK_GRAY = new Color(52, 58, 64);

    public CadastrarServico(TelaServico telaServico) {
        this.telaServico = telaServico;
        setTitle("Cadastro de Serviço Adicional");
        setSize(500, 400);
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
        txtNome = createModernTextField(20, "Digite o nome do serviço");
        txtPreco = createModernTextField(10, "Preço do serviço");
        txtDescricao = new JTextArea(4, 20);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        txtDescricao.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescricao.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        btnSalvar = createModernButton("Salvar", SUCCESS_COLOR, "Salvar o serviço");
        btnLimpar = createModernButton("Limpar", WARNING_COLOR, "Limpar todos os campos");
        btnCancelar = createModernButton("Cancelar", DANGER_COLOR, "Cancelar e fechar a janela");

        Dimension btnSize = new Dimension(120, 36);
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

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(Color.WHITE);
        TitledBorder border = new TitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ),
            "Dados do Serviço",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            PRIMARY_COLOR
        );
        painelFormulario.setBorder(border);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        painelFormulario.add(new JLabel("Nome:*"), gbc);
        gbc.gridx = 1;
        painelFormulario.add(txtNome, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        painelFormulario.add(new JLabel("Preço:*"), gbc);
        gbc.gridx = 1;
        painelFormulario.add(txtPreco, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelFormulario.add(new JLabel("Descrição:*"), gbc);
        gbc.gridx = 1;
        painelFormulario.add(new JScrollPane(txtDescricao), gbc);

        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(LIGHT_GRAY);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnCancelar);

        painelPrincipal.add(painelFormulario, BorderLayout.CENTER);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        add(painelPrincipal);
    }

    private void setupEvents() {
        btnSalvar.addActionListener(e -> salvarServico());
        btnLimpar.addActionListener(e -> limparCampos());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvarServico() {
        String nome = txtNome.getText();
        String descricao = txtDescricao.getText();
        double preco;
        try {
            preco = Double.parseDouble(txtPreco.getText());
        } catch (NumberFormatException ex) {
            showModernMessage("Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!controller.validarServico(nome, descricao, preco)) {
            showModernMessage("Preencha todos os campos obrigatórios corretamente!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Servico servico = controller.criarServico(nome, descricao, preco);
        controller.salvarServico(servico);

        if (telaServico != null) {
            telaServico.reloadData();
        }
        limparCampos();
        setVisible(false);
    }

    private void showModernMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void limparCampos() {
        txtNome.setText("");
        txtDescricao.setText("");
        txtPreco.setText("");
    }
}