package model;

public class Pedido {
	private int id_cliente;
	private int id_pacote;
	private String data;
	
	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getId_pacote() {
		return id_pacote;
	}

	public void setId_pacote(int id_pacote) {
		this.id_pacote = id_pacote;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Pedido(int id_cliente, int id_pacote, String data) {
		this.setId_cliente(id_cliente);
		this.setId_pacote(id_pacote);
		this.setData(data);
	}

}
