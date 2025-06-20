package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Menu extends JPanel {
    private MainFrame mainFrame;

    public Menu(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
    }

    private void initComponents() {
        

        // ==== BOTÕES DO MENU ====
        setLayout(new GridLayout(5, 1, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        JButton btnClientes = new JButton("Gerenciar Clientes");
        JButton btnPacotes = new JButton("Gerenciar Pacotes de Viagem");
        JButton btnServicos = new JButton("Gerenciar Serviços Adicionais");
        JButton btnPedidos = new JButton("Gerenciar Pedidos");

        // Ações dos botões
        btnClientes.addActionListener(e -> {
            // Exemplo de navegação para outra tela
        	mainFrame.mostrarTela("telaCliente");
        	
        });

        btnPacotes.addActionListener(e -> {
            // Exemplo de navegação para outra tela
            // mainFrame.mostrarTela("telaPacotes");
            JOptionPane.showMessageDialog(this, "Abrir tela de Pacotes (placeholder)");
        });

        btnServicos.addActionListener(e -> {
            // Exemplo de navegação para outra tela
            // mainFrame.mostrarTela("telaServicos");
            JOptionPane.showMessageDialog(this, "Abrir tela de Serviços (placeholder)");
        });

        btnPedidos.addActionListener(e -> {
            // Exemplo de navegação para outra tela
            // mainFrame.mostrarTela("telaPedidos");
            JOptionPane.showMessageDialog(this, "Abrir tela de Pedidos (placeholder)");
        });
        
        JLabel lblNewLabel = new JLabel("Sistema de Gerenciamento");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblNewLabel);

        add(btnClientes);
        add(btnPacotes);
        add(btnServicos);
        add(btnPedidos);
    }
}