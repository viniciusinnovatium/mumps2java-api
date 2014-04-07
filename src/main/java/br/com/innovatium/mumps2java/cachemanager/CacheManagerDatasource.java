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

public final class CacheManagerDatasource extends CacheManager {
	private static DataSource ds;
	private final String INSERT = "insert into key_value values (KEY, VALUE) ";

	CacheManagerDatasource() {
		super(new HashMap<Object, Object>());

		Context ctx;
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:jboss/datasources/infinispan");
		} catch (NamingException e) {
			throw new IllegalStateException(
					"Fail on look up datasource to open a new conection.", e);
		}
	}


	@Override
	public void put(Node node) {
		try {
			Connection con = ds.getConnection();
			PreparedStatement st = con.prepareStatement(INSERT);
			st.setObject(0, node.getValue());
			st.executeUpdate();
		} catch (SQLException e) {
			throw new IllegalStateException("Fail on starting conection", e);
		}
	}

	@Override
	public Node get(String key) {
		return null;
	}


	@Override
	public List<Node> like(String path) {
		// TODO Auto-generated method stub
		return null;
	}

}
