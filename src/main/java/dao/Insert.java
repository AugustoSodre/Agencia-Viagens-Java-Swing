package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import model.Cliente;
import model.PacoteViagem;
import model.Pedido;
import model.Servico;

public class Insert {

    // Método para inserir um Cliente
    public static void inserir(Cliente cliente) {
        String sql;

        if ("estrangeiro".equals(cliente.getTipo())) {
            sql = "INSERT INTO cliente (nome, telefone, email, tipo, passaporte) VALUES (?, ?, ?, ?, ?)";
            try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
            ) {
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getTelefone());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getTipo());
                stmt.setString(5, cliente.getPassaporte());

                int linhasAfetadas = stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente estrangeiro inserido com sucesso!\nLinhas Afetadas: " + linhasAfetadas, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao inserir cliente estrangeiro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }

        } else if ("nacional".equals(cliente.getTipo())) {
            sql = "INSERT INTO cliente (nome, telefone, email, tipo, cpf) VALUES (?, ?, ?, ?, ?)";
            try (
                Connection conn = Conexao.conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
            ) {
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getTelefone());
                stmt.setString(3, cliente.getEmail());
                stmt.setString(4, cliente.getTipo());
                stmt.setString(5, cliente.getCPF());

                int linhasAfetadas = stmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Cliente nacional inserido com sucesso!\nLinhas Afetadas: " + linhasAfetadas, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) {
                System.err.println("Erro ao inserir cliente nacional: " + e.getMessage());
            }

        } else {            
            System.err.println("Tipo de cliente inválido: " + cliente.getTipo());
        }
    }

    // Método para inserir um Pacote
    public static void inserir(PacoteViagem pacote) {
        String sql = "INSERT INTO pacote_viagem (nome, destino, duracao_dias, preco, tipo, descricao) VALUES (?, ?, ?, ?, ?, ?)";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, pacote.getNome());
            stmt.setString(2, pacote.getDestino());
            stmt.setInt(3, pacote.getDuracao());
            stmt.setDouble(4, pacote.getPreco());
            stmt.setString(5, pacote.getTipo());
            stmt.setString(6, pacote.getDescricao());

            int linhasAfetadas = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pacote inserido com sucesso!\nLinhas afetadas: " + linhasAfetadas, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir pacote: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para inserir um Pedido
    public static void inserir(Pedido pedido) {
        String sql = "INSERT INTO pedido (id_cliente, id_pacote, data_contratacao) VALUES (?, ?, ?)";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, pedido.getId_cliente());
            stmt.setInt(2, pedido.getId_pacote());

            Date dataContratacao = Date.valueOf(pedido.getData());
            stmt.setDate(3, dataContratacao);

            int linhasAfetadas = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Pedido inserido com sucesso!\nLinhas afetadas: " + linhasAfetadas, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para inserir um Servico
    public static void inserir(Servico servico) {
        String sql = "INSERT INTO servico_adicional (nome, descricao, preco) VALUES (?, ?, ?)";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, servico.getNome());
            stmt.setString(2, servico.getDescricao());
            stmt.setDouble(3, servico.getPreco());

            int linhasAfetadas = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Serviço inserido com sucesso!\nLinhas afetadas: " + linhasAfetadas, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao inserir serviço: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para inserir relação Pedido-Serviço
    public static void inserirPedidoServico(int id_pedido, int id_servico) {
        String sql = "INSERT INTO pedido_servico (id_pedido, id_servico) VALUES (?, ?)";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id_pedido);
            stmt.setInt(2, id_servico);

            int linhasAfetadas = stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Serviço vinculado ao pedido com sucesso!\nLinhas afetadas: " + linhasAfetadas, "Sucesso", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao vincular serviço ao pedido: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
