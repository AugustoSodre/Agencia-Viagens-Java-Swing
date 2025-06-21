package view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
        	mainFrame.mostrarTela("telaCliente");
        });

        btnPacotes.addActionListener(e -> {
            mainFrame.mostrarTela("telaPacote");
        });

        btnServicos.addActionListener(e -> {
            mainFrame.mostrarTela("telaServico");
        });

        btnPedidos.addActionListener(e -> {
            mainFrame.mostrarTela("telaPedido");
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