package aula1.persistence;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.util.PSQLException;

import aula1.model.Curso;

public class CursoDao implements Dao<Curso>{

	private final Connection conexao;
	
	public CursoDao ( Connection conexao ) {
		this.conexao = conexao;
	}
	
	@Override
	public void createTable() throws SQLException {
		try {
			Statement statement = this.conexao.createStatement();
			statement.execute("CREATE TABLE cursos"
					+ "(id BIGSERIAL,"
					+ "nome VARCHAR(255),"
					+ "id_professor BIGINT,"
					+ "PRIMARY KEY (id),"
					+ "CONSTRAINT fk_professor FOREIGN KEY (id_professor)"
					+ "REFERENCES professores(id) );");
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao criar a tabela.");
			System.err.println(e.getMessage());
		}			
	}

	@Override
	public void insert(Curso curso) throws SQLException {
		// ADD NOVO CURSO APENAS SE NÃO HOUVER UM OUTRO CURSO COM O MESMO NOME
		try {
			if ( findByNome(curso.getNome()) == null ) {
				PreparedStatement preparedStatement = this.conexao.prepareStatement(""
						+ "INSERT INTO cursos (nome, id_professor) "
						+ "VALUES (?, ?);");
				preparedStatement.setString(1, curso.getNome());
				preparedStatement.setInt(2, curso.getProfessorId());
				preparedStatement.execute();
			}
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao inserir.");
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Curso findById(Long id) throws SQLException {
		Curso curso = null;
		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(""
					+ "SELECT * FROM cursos "
					+ "WHERE id = ? ;");
			preparedStatement.setLong(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				int idCurso = resultSet.getInt("id");
				String nomeCurso = resultSet.getString("nome");
				int idProfessor = resultSet.getInt("id_professor");
				
				curso = new Curso(idCurso, nomeCurso, idProfessor);
			}
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao consultar.");
			System.err.println(e.getMessage());
		}
		return curso;
	}
	
	public Curso findByNome(String nome) throws SQLException {
		Curso curso = null;
		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(""
					+ "SELECT * FROM cursos "
					+ "WHERE nome = ? ;");
			preparedStatement.setString(1, nome);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				int idCurso = resultSet.getInt("id");
				String nomeCurso = resultSet.getString("nome");
				int idProfessor = resultSet.getInt("id_professor");
				
				curso = new Curso(idCurso, nomeCurso, idProfessor);
			}
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao consultar.");
			System.err.println(e.getMessage());
		}			
		return curso;
	}


	@Override
	public List<Curso> findAll() throws SQLException {
		ArrayList<Curso> cursos = new ArrayList<>();
		
		try {
			PreparedStatement  preparedStatement = this.conexao.prepareStatement(""
					+ "SELECT * FROM cursos;");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				int idCurso = resultSet.getInt("id");
				String nomeCurso = resultSet.getString("nome");
				int idProfessor = resultSet.getInt("id_professor");
				
				Curso curso = new Curso(idCurso, nomeCurso, idProfessor);
				cursos.add(curso);
			}
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao consultar.");
			System.err.println(e.getMessage());
		}			
		return cursos;
	}

	@Override
	public void update(Long id, Curso curso) throws SQLException {
		String nomeCurso = curso.getNome();
		int idProfessor = curso.getProfessorId();
		try {
			if ( findByNome( nomeCurso ) == null ) {
				PreparedStatement preparedStatement = this.conexao.prepareStatement(""
						+ "UPDATE cursos "
						+ "SET nome = ? , "
						+ "id_professor = ? "
						+ "WHERE id = ? ;");
				preparedStatement.setString(1, nomeCurso);
				preparedStatement.setInt(2, idProfessor);
				preparedStatement.setLong(3, id);
				preparedStatement.execute();
			}
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao atualizar.");
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		Curso curso = findById(id);
		try {
			if ( !curso.equals(null) ) {		
				PreparedStatement preparedStatement = this.conexao.prepareStatement(
						"DELETE FROM cursos "
						+ "WHERE id = ? ;");
				preparedStatement.setInt(1, curso.getId());
				preparedStatement.execute();
			}
		} catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao excluir.");
			System.err.println(e.getMessage());
		}
	}
}
