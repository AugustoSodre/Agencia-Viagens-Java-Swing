package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.table.DefaultTableModel;

import dao.utils.ResultSetUtils;

public class Consulta {

    public static DefaultTableModel consulta(int id, String tipo) {
        DefaultTableModel model = null;
        String query = "";

        // Seleciona qual query será utilizada
        switch (tipo.toLowerCase()) {
            case "cliente":
                query = "SELECT * FROM cliente WHERE id_cliente = ?";
                break;

            case "pacote":
                query = "SELECT * FROM pacote_viagem WHERE id_pacote = ?";
                break;

            case "pedido":
                query = "SELECT * FROM pedido WHERE id_pedido = ?";
                break;

            case "servico":
                query = "SELECT * FROM servico_adicional WHERE id_servico = ?";
                break;

            default:
                System.err.println("Tipo de entidade inválido: " + tipo);
                return null;
        }

        // Conexão e execução da consulta
        try (
            Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {

                // Verifica se o ResultSet está vazio
                if (!rs.isBeforeFirst()) {
                  
                    System.err.println("Nenhum resultado encontrado para o ID: " + id);
                    return null;
                }

                // Converte os dados para DefaultTableModel
                model = ResultSetUtils.buildTableModel(rs);
            }

        } catch (Exception e) {
            System.err.println("Erro de SQL: " + e.getMessage());
        }

        return model;
    }
}
