package model;

public class ClienteNacional extends Cliente {
	private String cpf;
	
	@Override
	public String getCPF() {
		return cpf;
	}


	public void setCPF(String cpf) {
		this.cpf = cpf;
	}


	public ClienteNacional(String nome, String telefone, String email, String cpf) {
		this.setNome(nome);
		this.setTelefone(telefone);
		this.setEmail(email);
		this.setTipo("nacional");
		this.setCPF(cpf);
	}
}
