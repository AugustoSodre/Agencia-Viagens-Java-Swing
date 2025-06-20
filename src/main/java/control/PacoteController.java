package control;

import javax.swing.table.DefaultTableModel;

import dao.Insert;
import dao.Consulta;
import model.PacoteViagem;
import model.PacoteAventura;
import model.PacoteCultural;
import model.PacoteLuxo;
import model.PacoteOutro;

public class PacoteController {

    public boolean validarPacote(String nome, String destino, int duracao, double preco, String descricao, String tipo) {
        if (nome == null || nome.trim().isEmpty()) return false;
        if (destino == null || destino.trim().isEmpty()) return false;
        if (duracao <= 0) return false;
        if (preco <= 0) return false;
        if (descricao == null || descricao.trim().isEmpty()) return false;
        if (tipo == null || tipo.trim().isEmpty()) return false;
        return true;
    }

    public PacoteViagem criarPacote(String nome, String destino, int duracao, double preco, String descricao, String tipo) {
        switch (tipo.toLowerCase()) {
            case "aventura":
                return new PacoteAventura(nome, destino, duracao, preco, descricao);
            case "cultural":
                return new PacoteCultural(nome, destino, duracao, preco, descricao);
            case "luxo":
                return new PacoteLuxo(nome, destino, duracao, preco, descricao);
            default:
                return new PacoteOutro(nome, destino, duracao, preco, descricao, tipo);
        }
    }

    public void salvarPacote(PacoteViagem pacote) {
        Insert.inserir(pacote);
    }

    public DefaultTableModel buscarPacotePorId(int id) {
        return Consulta.consulta(id, "pacote");
    }
}