package aula1.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.postgresql.util.PSQLException;

import aula1.model.Curso;
import aula1.model.Professor;
import aula1.model.Professor;
import aula1.model.Usuario;

public class ProfessorDao implements Dao<Professor>{

	private final Connection conexao;
	
	public ProfessorDao ( Connection conexao ) {
		this.conexao = conexao;
	}
	
	@Override
	public void createTable() throws SQLException {
		try {
			Statement statement = this.conexao.createStatement();
			statement.execute("CREATE TABLE professores"
					+ "(id BIGSERIAL,"
					+ "nome VARCHAR(255),"
					+ "idade BIGINT,"
					+ "PRIMARY KEY (id) );");
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao criar a tabela.");
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void insert(Professor professor) throws SQLException {
		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement( ""
					+ "INSERT INTO professores (nome, idade) "
					+ "VALUES (?, ?);");
			preparedStatement.setString(1, professor.getNome());
			preparedStatement.setLong(2, professor.getIdade());
			preparedStatement.execute();
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao inserir.");
			System.err.println(e.getMessage());
		}
	}

	@Override
	public Professor findById(Long id) throws SQLException {
		Professor professor = null;
		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(""
					+ "SELECT * FROM professores "
					+ "WHERE id = ?;");
			preparedStatement.setLong(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				Long idProfessor = resultSet.getLong("id");
				String nome = resultSet.getString( "nome" );
				Integer idade = resultSet.getInt( "idade" );
				
				professor = new Professor(idProfessor, nome, idade);
			}
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao consultar.");
			System.err.println(e.getMessage());
		}
		
		return professor;
	}

	@Override
	public List<Professor> findAll() throws SQLException {
		ArrayList<Professor> professores = new ArrayList<>();
		try {
			PreparedStatement  preparedStatement = this.conexao.prepareStatement(""
					+ "SELECT * FROM professores;");
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while ( resultSet.next() ) {
				Long id = resultSet.getLong("id");
				String nome = resultSet.getString("nome");
				Integer idade = resultSet.getInt("idade");
				
				Professor professor = new Professor(id, nome, idade);
				professores.add(professor);
			}
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao consultar.");
			System.err.println(e.getMessage());
		}			
		return professores;
	}

	@Override
	public void update(Long id, Professor professor) throws SQLException {
		String nome = professor.getNome();
		Integer idade = professor.getIdade();
		try {
			PreparedStatement preparedStatement = this.conexao.prepareStatement(""
					+ "UPDATE professores "
					+ "SET nome = ? ,"
					+ " idade = ? "
					+ "WHERE id = ? ;");
			preparedStatement.setString(1, nome);
			preparedStatement.setInt(2, idade);
			preparedStatement.setLong(3, id);
			preparedStatement.execute();
		
		} catch (PSQLException e) {
			System.err.println("Um erro ocorreu ao atualizar");
			System.err.println(e.getMessage());
		}
		
	}

	@Override
	public void delete(Long id) throws SQLException {
		Professor professor = findById(id);
		try {
			if ( !professor.equals(null) ) {		
				PreparedStatement preparedStatement = this.conexao.prepareStatement(
						"DELETE FROM professores WHERE id = ? ;");
				preparedStatement.setLong(1, professor.getId());
				preparedStatement.execute();
			}
		}catch (PSQLException e) {
			System.err.println("Ocorreu um erro ao excluir.");
			System.err.println(e.getMessage());
		}			
	}

}
