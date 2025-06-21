package control;

import dao.Insert;
import dao.deletes.DeleteServico;
import model.Servico;

import javax.swing.table.DefaultTableModel;
import dao.Consulta;

public class ServicoController {

    public boolean validarServico(String nome, String descricao, double preco) {
        if (nome == null || nome.trim().isEmpty()) return false;
        if (descricao == null || descricao.trim().isEmpty()) return false;
        if (preco <= 0) return false;
        return true;
    }

    public Servico criarServico(String nome, String descricao, double preco) {
        return new Servico(nome, descricao, preco);
    }

    public void salvarServico(Servico servico) {
        Insert.inserir(servico);
    }

    public void deletarServico(int id) {
        DeleteServico.delete(id);
    }

    public DefaultTableModel buscarServicoPorId(int id) {
        return Consulta.consulta(id, "servico");
    }
}