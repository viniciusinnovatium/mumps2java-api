package br.com.innovatium.mumps2java.cachemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.innovatium.mumps2java.datastructure.Node;
import br.com.innovatium.mumps2java.todo.TODO;

public final class CacheManagerDatasource extends CacheManager {
	private static DataSource ds;
	private final String INSERT = "insert into WWW001 values (?, ?) ";

	/*
	 * Devemos implementar configuracao dinamica para apontar para tabelas diferentes. 
	 */
	@TODO
	CacheManagerDatasource() {
		super(new HashMap<Object, Object>());

		Context ctx;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:jboss/datasources/oracle");
		} catch (NamingException e) {
			throw new IllegalStateException(
					"Fail on look up datasource to open a new conection.", e);
		}
	}

	@Override
	public void put(String key, Object value) {
		PreparedStatement st = null;
		try {
			Connection con = ds.getConnection();
			st = con.prepareStatement(INSERT);
			st.setObject(1, key);
			st.setObject(2, value);
			st.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException("Fail on starting conection", e);
		} finally{
			if(st != null){
				try {
					st.close();
				} catch (SQLException e) {
					throw new IllegalStateException("Fail to close statement", e);
				}
			}
		}
	}

	@Override
	public Node get(String key) {
		return null;
	}

	@Override
	public List<Object[]> like(String path) {
		throw new UnsupportedOperationException();
	}

}
