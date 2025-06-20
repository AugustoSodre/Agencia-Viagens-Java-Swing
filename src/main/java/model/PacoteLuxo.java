package model;

public class PacoteLuxo extends PacoteViagem {
	public PacoteLuxo(String nome, String destino, 
			int duracao, double preco, String descricao) {
		this.setNome(nome);
		this.setDestino(destino);
		this.setDuracao(duracao);
		this.setPreco(preco);
		this.setDescricao(descricao);
		this.setTipo("luxo");
	}
}
