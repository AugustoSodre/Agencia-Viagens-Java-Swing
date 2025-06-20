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
                query = "SELECT * FROM pedido";
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
