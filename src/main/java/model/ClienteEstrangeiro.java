package model;

public class ClienteEstrangeiro extends Cliente{
	
	private String passaporte;
	
	@Override
	public String getPassaporte() {
		return passaporte;
	}


	public void setPassaporte(String passaporte) {
		this.passaporte = passaporte;
	}


	public ClienteEstrangeiro(String nome, String telefone, String email, String passaporte) {
		this.setNome(nome);
		this.setTelefone(telefone);
		this.setEmail(email);
		this.setTipo("estrangeiro");
		this.setPassaporte(passaporte);
	}
	
}
