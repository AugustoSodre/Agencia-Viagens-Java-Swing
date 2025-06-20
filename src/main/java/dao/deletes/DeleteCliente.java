package dao.deletes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import dao.Conexao;

public class DeleteCliente {

    public static void delete(int id) {
        boolean temPedido = false;
	
        String sqlVerifica = "SELECT * FROM pedido WHERE id_cliente = ?";

        // Verifica se o cliente possui pedidos vinculados
        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sqlVerifica)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                temPedido = true;
                JOptionPane.showMessageDialog(null,
                    "Este cliente possui pedidos vinculados e não pode ser excluído.",
                    "Cliente em uso",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao verificar vínculos do cliente:\n" + e.getMessage(),
                "Erro de consulta",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Se não possui pedidos, deleta o cliente
        String sqlDelete = "DELETE FROM cliente WHERE id_cliente = ?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sqlDelete)
        ) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null,
                    "Cliente deletado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(null,
                    "Nenhum cliente foi deletado. Verifique o ID informado.",
                    "Nenhuma alteração",
                    JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao deletar cliente:\n" + e.getMessage(),
                "Erro de exclusão",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
