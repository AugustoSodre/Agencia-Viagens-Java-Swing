package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

import dao.utils.ResultSetUtils;

public class SelectAll {

    public DefaultTableModel listarTodosComoTableModel(String tipo) {
        DefaultTableModel model = null;
        String query = "";

        // Seleciona qual query será utilizada
        switch (tipo.toLowerCase()) {
            case "cliente":
                query = "SELECT * FROM cliente";
                break;
            case "pacote":
                query = "SELECT * FROM pacote_viagem";
                break;
            case "pedido":
                query = "SELECT " +
                        "p.id_pedido AS 'ID', " +
                        "DATE_FORMAT(p.data_contratacao, '%d/%m/%Y') AS 'Data', " +
                        "c.nome AS 'Cliente', " +
                        "c.email AS 'Email', " +
                        "c.telefone AS 'Telefone', " +
                        "pv.nome AS 'Pacote', " +
                        "pv.destino AS 'Destino', " +
                        "pv.preco AS 'Valor Pacote', " +
                        "GROUP_CONCAT(sa.nome SEPARATOR ', ') AS 'Serviços', " +
                        "(pv.preco + COALESCE(SUM(sa.preco),0)) AS 'Valor Total' " +
                        "FROM pedido p " +
                        "INNER JOIN cliente c ON p.id_cliente = c.id_cliente " +
                        "INNER JOIN pacote_viagem pv ON p.id_pacote = pv.id_pacote " +
                        "LEFT JOIN pedido_servico ps ON p.id_pedido = ps.id_pedido " +
                        "LEFT JOIN servico_adicional sa ON ps.id_servico = sa.id_servico " +
                        "GROUP BY p.id_pedido " +
                        "ORDER BY p.id_pedido ASC";
                break;
            case "servico":
                query = "SELECT * FROM servico_adicional";
                break;
            default:
                System.err.println("Tipo de entidade inválido: " + tipo);
                return null;
        }

        // Consulta no banco
        try (
            Connection conn = Conexao.conectar();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query)
        ) {
            // Verifica se o ResultSet está vazio
            if (!rs.isBeforeFirst()) {
                System.err.println("Nenhum resultado encontrado para o tipo: " + tipo);
                return null;
            }

            model = ResultSetUtils.buildTableModel(rs);

        } catch (Exception e) {
            System.err.println("Erro ao consultar " + tipo + ": " + e.getMessage());
        }

        return model;
    }
}
