package view;

import java.awt.*;
import javax.swing.*;
    
public class MainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel container;

    // Nova paleta de cores elegante
    private static final Color PRIMARY = new Color(150, 60, 70);      // #963C46
    private static final Color SECONDARY = new Color(225, 188, 190);  // #E1BCBE
    private static final Color BACKGROUND = new Color(255, 245, 238); // #FFF5EE
    private static final Color TEXT = new Color(45, 45, 45);          // #2D2D2D
    private static final Color SUCCESS = new Color(71, 191, 145);     // #47BF91
    private static final Color WARNING = new Color(255, 127, 80);     // #FF7F50
    private static final Color ERROR = new Color(192, 57, 43);        // #C0392B
    private static final Color BORDER = new Color(217, 217, 217);         // #D9D9D9

    public MainFrame() {
        // Custom font for menu
        Font menuFont = new Font("Segoe UI", Font.BOLD, 16);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(PRIMARY); // Marsala
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JMenu menuOpcoes = new JMenu("Opções");
        menuOpcoes.setFont(menuFont);
        menuOpcoes.setForeground(Color.WHITE);

        JMenuItem retornarMenu = new JMenuItem("Retornar ao Menu", UIManager.getIcon("FileView.homeFolderIcon"));
        retornarMenu.setFont(menuFont);
        retornarMenu.setToolTipText("Voltar para o menu principal");
        retornarMenu.setBackground(BACKGROUND);

        JMenuItem sairItem = new JMenuItem("Sair", UIManager.getIcon("OptionPane.errorIcon"));
        sairItem.setFont(menuFont);
        sairItem.setToolTipText("Fechar o sistema");
        sairItem.setBackground(BACKGROUND);

        JMenuItem sobreItem = new JMenuItem("Sobre", UIManager.getIcon("FileView.computerIcon"));
        sobreItem.setFont(menuFont);
        sobreItem.setToolTipText("Informações sobre o sistema");
        sobreItem.setBackground(BACKGROUND);

        retornarMenu.setAccelerator(KeyStroke.getKeyStroke("F1"));
        sairItem.setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
        sobreItem.setAccelerator(KeyStroke.getKeyStroke("F2"));

        retornarMenu.addActionListener(e -> mostrarTela("menu"));
        sairItem.addActionListener(e -> {
            Object[] options = {"Sim", "Não"};
            int confirm = JOptionPane.showOptionDialog(
                this, "Deseja realmente sair?", "Sair",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]
            );
            if (confirm == 0) System.exit(0);
        });
        sobreItem.addActionListener(e -> JOptionPane.showMessageDialog(
            this,
            "Agência de Viagens\nVersão 1.0\nDesenvolvido por Augusto Sodré e Guilherme Ilhas",
            "Sobre", JOptionPane.INFORMATION_MESSAGE
        ));

        menuOpcoes.add(retornarMenu);
        menuOpcoes.addSeparator();
        menuOpcoes.add(sobreItem);
        menuOpcoes.addSeparator();
        menuOpcoes.add(sairItem);

        // Set menu foreground color for all items
        for (Component c : menuOpcoes.getMenuComponents()) {
            if (c instanceof JMenuItem) {
                c.setForeground(new Color(30, 144, 255));
            }
        }

        menuBar.add(menuOpcoes);
        setJMenuBar(menuBar);

        setTitle("Sistema de Gerenciamento da Agência de Viagens");
        setSize(1324, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        Menu menu = new Menu(this);
        TelaCliente telaCliente = new TelaCliente(this);
        TelaPacote telaPacote = new TelaPacote(this);
        TelaServico telaServico = new TelaServico(this);
        TelaPedido telaPedido = new TelaPedido(this);
        

        container.add(menu, "menu");
        container.add(telaCliente, "telaCliente");
        container.add(telaPacote, "telaPacote");
        container.add(telaServico, "telaServico");
        container.add(telaPedido, "telaPedido");

        add(container);
        setVisible(true);
    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(container, nomeTela);
    }
}
