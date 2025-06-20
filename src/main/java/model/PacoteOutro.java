package model;

public class PacoteOutro extends PacoteViagem{
	public PacoteOutro(String nome, String destino, 
			int duracao, double preco, String descricao, String tipo) {
		this.setNome(nome);
		this.setDestino(destino);
		this.setDuracao(duracao);
		this.setPreco(preco);
		this.setDescricao(descricao);
		this.setTipo(tipo);
	}

}
