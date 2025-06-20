package dao.deletes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import dao.Conexao;

public class DeletePacote {

    public static void delete(int id) {
        boolean temClientes = false;

        String sqlVerifica = "SELECT * FROM pedido WHERE id_pacote = ?";

        // Verifica se o pacote está vinculado a algum pedido
        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sqlVerifica)
        ) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                temClientes = true;
                JOptionPane.showMessageDialog(null,
                    "Este pacote está vinculado a um ou mais pedidos e não pode ser excluído.",
                    "Pacote em uso",
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao verificar vínculos do pacote:\n" + e.getMessage(),
                "Erro de consulta",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Se não estiver vinculado, tenta deletar
        String sqlDelete = "DELETE FROM pacote_viagem WHERE id_pacote = ?";

        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(sqlDelete)
        ) {
            stmt.setInt(1, id);
            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null,
                    "Pacote deletado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(null,
                    "Nenhum pacote foi deletado. Verifique o ID informado.",
                    "Nenhuma alteração",
                    JOptionPane.WARNING_MESSAGE
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao deletar pacote:\n" + e.getMessage(),
                "Erro de exclusão",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
