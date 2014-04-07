package br.com.innovatium.mumps2java.cachemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.innovatium.mumps2java.datastructure.Node;

public class CacheManagerJDBC extends CacheManager {
	private final String INSERT = "insert into \"KEY_VALUE\" values (?, ?) ;";
	private final String UPDATE = "update \"KEY_VALUE\" set value = ? where key = ?;";
	private final String SELECT_ONE = "select key, value from \"KEY_VALUE\" where key = ?;";
	private final String SELECT_LIKE = "select key, value from \"KEY_VALUE\" where key like ? order by key asc;";
	private final String DELETE = "delete from \"KEY_VALUE\" where key = ?;";
	private final String DELETE_LIKE = "delete from \"KEY_VALUE\" where key like ?;";

	public CacheManagerJDBC() {
		super(new HashMap<Object, Object>());
	}

	@Override
	public List<Node> like(String path) {
		final List<Node> l = new ArrayList<Node>();
		if (path == null) {
			return l;
		}
		Connection con = null;
		try {
			con = openConnection();

			PreparedStatement select = con.prepareStatement(SELECT_LIKE);
			select.setString(1, path + "%");
			ResultSet result = select.executeQuery();
			while (result.next()) {
				l.add(new Node(null, result.getString(1), result.getString(2)));
			}

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail on opening a new connection to insert data", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new IllegalStateException(
							"Fail on close connection after insert data", e);
				}
			}
		}
		return l;
	}

	
	public void kill(String path) {
		Connection con = null;
		try {
			con = openConnection();
			
			PreparedStatement delete = con.prepareStatement(DELETE_LIKE);
			delete.setString(1, path+"%");
			delete.execute();

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail on opening a new connection to kill some data", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new IllegalStateException(
							"Fail on close connection after kill some data", e);
				}
			}
		}
	}
	
	@Override
	public void put(Node node) {
		put(node.getPath(), node.getValue());
	}

	@Override
	public void put(Object key, Object value) {
		Connection con = null;
		try {
			con = openConnection();
			
			PreparedStatement select = con.prepareStatement(SELECT_ONE);
			select.setObject(1, key);
			ResultSet result = select.executeQuery();
			PreparedStatement insert = null;
			if (!result.next()) {
				insert = con.prepareStatement(INSERT);
				insert.setObject(1, key);
				insert.setObject(2, value);
			} else {
				insert = con.prepareStatement(UPDATE);
				insert.setObject(1, value);
				insert.setObject(2, key);
			}

			insert.execute();	

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail on opening a new connection to insert data", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new IllegalStateException(
							"Fail on close connection after insert data", e);
				}
			}
		}
	}
	
	@Override
	public Object get(String key) {
		Connection con = null;
		try {
			con = openConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_ONE);
			ps.setString(1, key);
			ResultSet result = ps.executeQuery();

			return result.next() ? result.getString(2) : null;

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail on opening a new connection to select data", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new IllegalStateException(
							"Fail on close connection after select data", e);
				}
			}
		}
	}
	
	@Override
	public void remove(String key) {
		Connection con = null;
		try {
			con = openConnection();
			PreparedStatement ps = con.prepareStatement(DELETE);
			ps.setString(1, key);
			ps.execute();

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail on opening a new connection to delete data", e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new IllegalStateException(
							"Fail on close connection after delete data", e);
				}
			}
		}		
	}

	private Connection openConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:postgresql://localhost:5432/infinispan", "postgres",
				"@postgresql15");

	}
}
