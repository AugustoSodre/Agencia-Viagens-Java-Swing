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

    // Nova paleta de cores elegante
    private static final Color PRIMARY = new Color(150, 60, 70);      // #963C46
    private static final Color SECONDARY = new Color(225, 188, 190);  // #E1BCBE
    private static final Color BACKGROUND = new Color(255, 245, 238); // #FFF5EE
    private static final Color TEXT = new Color(45, 45, 45);          // #2D2D2D
    private static final Color SUCCESS = new Color(71, 191, 145);     // #47BF91
    private static final Color WARNING = new Color(255, 127, 80);     // #FF7F50
    private static final Color ERROR = new Color(192, 57, 43);        // #C0392B
    private static final Color BORDER = new Color(217, 217, 217);         // #D9D9D9

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
        setBackground(BACKGROUND);
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
            BorderFactory.createLineBorder(BORDER, 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        btnSalvar = createModernButton("Salvar", SUCCESS, "Salvar o serviço");
        btnLimpar = createModernButton("Limpar", WARNING, "Limpar todos os campos");
        btnCancelar = createModernButton("Cancelar", ERROR, "Cancelar e fechar a janela");

        Dimension btnSize = new Dimension(120, 36);
        btnSalvar.setPreferredSize(btnSize);
        btnLimpar.setPreferredSize(btnSize);
        btnCancelar.setPreferredSize(btnSize);
    }

    private JTextField createModernTextField(int columns, String tooltip) {
        JTextField field = new JTextField(columns);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setForeground(TEXT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER, 1, true),
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
        painelPrincipal.setBackground(BACKGROUND);
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelFormulario = new JPanel(new GridBagLayout());
        painelFormulario.setBackground(Color.WHITE);
        TitledBorder border = new TitledBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY, 2, true),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ),
            "Dados do Serviço",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 16),
            PRIMARY
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
        painelBotoes.setBackground(BACKGROUND);
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