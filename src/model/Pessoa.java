package model;

public class Pessoa {
	
	private int id;
	private String nome;
	private String email;
	private String instituicao;
	private String interesse;
	
	public Pessoa() {
		
	}
	
	public Pessoa(String nome, String email, String instituicao, String interesse) {
		this.nome = nome;
		this.email = email;
		this.instituicao = instituicao;
		this.interesse = interesse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getInteresse() {
		return interesse;
	}

	public void setInteresse(String interesse) {
		this.interesse = interesse;
	}
	
	public String toString() {
		return "id: " + id + "\n"
			 + "nome: " + nome + "\n"
			 + "email: " + email + "\n"
			 + "instituicao: " + instituicao + "\n"
			 + "interesse: " + interesse;
	}

}
