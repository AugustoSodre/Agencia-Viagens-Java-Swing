package dao.deletes;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

import dao.Conexao;

public class DeletePedido {

    public static void delete(int id) {
        try (Connection conn = Conexao.conectar()) {

            // Primeiro: deletar na tabela dependente (pedido_servico)
            String sql1 = "DELETE FROM pedido_servico WHERE id_pedido = ?";
            try (PreparedStatement stmt1 = conn.prepareStatement(sql1)) {
                stmt1.setInt(1, id);
                stmt1.executeUpdate();
            }

            // Segundo: deletar o próprio pedido
            String sql2 = "DELETE FROM pedido WHERE id_pedido = ?";
            try (PreparedStatement stmt2 = conn.prepareStatement(sql2)) {
                stmt2.setInt(1, id);
                int linhasAfetadas = stmt2.executeUpdate();

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(null,
                        "Pedido deletado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(null,
                        "Nenhum pedido foi deletado. Verifique o ID informado.",
                        "Nenhuma alteração",
                        JOptionPane.WARNING_MESSAGE
                    );
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Erro ao deletar pedido:\n" + e.getMessage(),
                "Erro de exclusão",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
