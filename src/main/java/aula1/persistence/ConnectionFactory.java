package aula1.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.postgresql.util.PSQLException;

public class ConnectionFactory {
	
	private static ConnectionFactory instancia;
	
	private ConnectionFactory() {
		
	}
	
	public static ConnectionFactory getInstance() {
		if ( instancia == null ) {
			instancia = new ConnectionFactory();	
		}
		return instancia;
	}

	public Connection getConnection() {
		
		try {
			Connection con =  DriverManager.getConnection(""
					+ "jdbc:postgresql://localhost:5434/", "postgres", "admin");
			
			return con;
		} catch ( SQLException e ) {
			System.out.println( e.getSQLState() );
			System.out.println( e.getErrorCode() );
			System.out.println( e.getMessage() );
			System.out.println( e.getCause() );
		}
		
		return null;
		
	}

}
