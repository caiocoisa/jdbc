package aula1.model;

public class Curso {

	private Integer id;
	private String nome;
	private Integer professorId;
	
	public Curso(Integer id, String nome, Integer professorId) {
		super();
		this.id = id;
		this.nome = nome;
		this.professorId = professorId;
	}
	
	public Curso(String nome, Integer professorId) {
		super();
		this.nome = nome;
		this.professorId = professorId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getProfessorId() {
		return professorId;
	}

	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
	}
	
}
