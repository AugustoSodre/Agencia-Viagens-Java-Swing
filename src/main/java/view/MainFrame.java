package view;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {
    CardLayout cardLayout;
    JPanel container;

    public MainFrame() {
        // Custom font for menu
        Font menuFont = new Font("Segoe UI", Font.BOLD, 16);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(30, 144, 255)); // DodgerBlue
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JMenu menuOpcoes = new JMenu("Opções");
        menuOpcoes.setFont(menuFont);
        menuOpcoes.setForeground(Color.WHITE);

        JMenuItem retornarMenu = new JMenuItem("Retornar ao Menu", UIManager.getIcon("FileView.homeFolderIcon"));
        retornarMenu.setFont(menuFont);
        retornarMenu.setToolTipText("Voltar para o menu principal");
        retornarMenu.setBackground(Color.WHITE);

        JMenuItem sairItem = new JMenuItem("Sair", UIManager.getIcon("OptionPane.errorIcon"));
        sairItem.setFont(menuFont);
        sairItem.setToolTipText("Fechar o sistema");
        sairItem.setBackground(Color.WHITE);

        JMenuItem sobreItem = new JMenuItem("Sobre", UIManager.getIcon("FileView.computerIcon"));
        sobreItem.setFont(menuFont);
        sobreItem.setToolTipText("Informações sobre o sistema");
        sobreItem.setBackground(Color.WHITE);

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
        setSize(1080, 720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        Menu menu = new Menu(this);
        TelaCliente telaCliente = new TelaCliente(this);
        TelaPacote telaPacote = new TelaPacote(this);
        

        container.add(menu, "menu");
        container.add(telaCliente, "telaCliente");
        container.add(telaPacote, "telaPacote");

        add(container);
        setVisible(true);
    }

    public void mostrarTela(String nomeTela) {
        cardLayout.show(container, nomeTela);
    }
}
