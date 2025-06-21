package control;

import dao.Insert;
import dao.Consulta;
import model.Pedido;

import javax.swing.table.DefaultTableModel;

public class PedidoController {

    public boolean validarPedido(int idCliente, int idPacote, String data) {
        if (idCliente <= 0) return false;
        if (idPacote <= 0) return false;
        if (data == null || data.trim().isEmpty()) return false;
        return true;
    }

    public Pedido criarPedido(int idCliente, int idPacote, String data) {
        return new Pedido(idCliente, idPacote, data);
    }

    public void salvarPedido(Pedido pedido) {
        Insert.inserir(pedido);
    }

    public DefaultTableModel buscarPedidoPorId(int id) {
        return Consulta.consulta(id, "pedido");
    }
}
