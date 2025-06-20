package control;

import javax.swing.table.DefaultTableModel;

import dao.Insert;
import model.Cliente;
import model.ClienteEstrangeiro;
import model.ClienteNacional;

public class ClienteController {

    public boolean validarCliente(String nome, String telefone, String email, String documento, boolean isNacional) {
        if (nome == null || nome.trim().isEmpty()) return false;
        if (telefone == null || telefone.trim().isEmpty()) return false;
        if (email == null || email.trim().isEmpty()) return false;
        if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.[a-zA-Z]{2,}$")) return false;
        if (documento == null || documento.trim().isEmpty()) return false;
        if (isNacional) {
            return documento.matches("^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$");
        } else {
            return documento.matches("^[A-Za-z0-9]{6,9}$");
        }
    }

    public Cliente criarCliente(String nome, String telefone, String email, String documento, boolean isNacional) {
        if (isNacional) {
            return new ClienteNacional(nome, telefone, email, documento);
        } else {
            return new ClienteEstrangeiro(nome, telefone, email, documento);
        }
    }

    public void salvarCliente(Cliente cliente) {
        Insert.inserir(cliente);
    }
    
    public DefaultTableModel buscarClientePorId(int id) {
        return dao.Consulta.consulta(id, "cliente");
    }

}
