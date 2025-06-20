package dao.deletes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import dao.Conexao;

public class DeleteServico {

    public static void delete(int id) {
        boolean temPedido = false;

        String sqlVerifica = "SELECT * FROM pedido_servico WHERE id_servico = ?";

        // Checando se o Serviço possui Pedidos vinculados
        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sqlVerifica)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                temPedido = true;
                JOptionPane.showMessageDialog(null,
                    "Este serviço está vinculado a um pedido e não pode ser excluído.",
                    "Serviço em uso",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao verificar vínculos do serviço:\n" + e.getMessage(),
                "Erro de consulta",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Se não possui pedidos vinculados, pode ser apagado
        String sqlDelete = "DELETE FROM servico_adicional WHERE id_servico = ?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sqlDelete)
        ) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null,
                    "Serviço deletado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(null,
                    "Nenhum serviço foi deletado. Verifique o ID informado.",
                    "Nenhuma alteração",
                    JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao deletar serviço:\n" + e.getMessage(),
                "Erro de exclusão",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
