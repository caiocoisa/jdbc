package aula1.persistence;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
	public void createTable () throws SQLException;
	public void insert (final T t) throws SQLException;
	public T findById (final Long id) throws SQLException;
	public List<T> findAll () throws SQLException;
	public void update (Long id, T t) throws SQLException;
	public void delete (Long id) throws SQLException;
}
