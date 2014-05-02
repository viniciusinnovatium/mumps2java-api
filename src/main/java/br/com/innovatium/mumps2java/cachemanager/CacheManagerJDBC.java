package br.com.innovatium.mumps2java.cachemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.innovatium.mumps2java.todo.TODO;

public class CacheManagerJDBC extends CacheManager {

	public CacheManagerJDBC() {
		super(new HashMap<Object, Object>());
	}

	@Override
	public List<Object[]> like(String path) {
		final List<Object[]> l = new ArrayList<Object[]>();
		if (path == null) {
			return l;
		}
		Connection con = null;
		try {
			con = openConnection();

			String like = "select key_, value_ from \""
					+ path.split("~")[0].replace("^", "")
					+ "\" where key_ like ? order by key_ asc;";

			PreparedStatement select = con.prepareStatement(like);
			select.setString(1, path + "%");
			ResultSet result = select.executeQuery();
			while (result.next()) {
				l.add(new Object[] { result.getString(1), result.getString(2) });
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

	// Remove hardcoded delimiter
	@TODO
	public void kill(String path) {
		Connection con = null;
		try {
			con = openConnection();

			String string = "delete from \""
					+ path.split("~")[0].replace("^", "")
					+ "\" where key like ?;";
			PreparedStatement delete = con.prepareStatement(string);
			delete.setString(1, path + "%");
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

	/*
	 * We have to study a better way to do inserts and create string to make
	 * queries.
	 */
	@TODO
	@Override
	public void put(String key, Object value) {
		Connection con = null;
		try {
			con = openConnection();
			final String variableName = key.split("~")[0];
			String selectOne = "select key_, value_ from \"" + variableName
					+ "\" where key_ =  ?;";
			PreparedStatement select = con.prepareStatement(selectOne);
			select.setObject(1, key);
			ResultSet result = select.executeQuery();
			PreparedStatement insert = null;
			if (!result.next()) {
				String insertQuery = "insert into " + variableName
						+ " values (?, ?) ;";
				insert = con.prepareStatement(insertQuery);
				insert.setObject(1, key);
				insert.setObject(2, value);
			} else {
				String updateQuery = "update \"" + variableName
						+ "\" set value_ = ? where key_ =  ?;";
				insert = con.prepareStatement(updateQuery);
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
			String selectOne = "select key_, value_ from \""
					+ key.split("~")[0].replace("^", "") + "\" where key_ =  ?;";
			PreparedStatement ps = con.prepareStatement(selectOne);
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

	// Remove split method to recover variable name
	@TODO
	@Override
	public void remove(String key) {
		Connection con = null;
		try {
			con = openConnection();
			String delete = "delete from \""
					+ key.split("~")[0].replace("^", "") + "\" where key_ =  ?;";
			PreparedStatement ps = con.prepareStatement(delete);
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

	// Replace the database connection parameter to properties file.
	@TODO
	private Connection openConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/metadata", "postgres",
				"@postgresql15");

	}

}
