package br.com.innovatium.mumps2java.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import br.com.innovatium.mumps2java.todo.TODO;

public class DAO {
	private ConnectionType type;

	public DAO() {
		this(ConnectionType.JDBC);
	}

	public DAO(ConnectionType connectionType) {
		this.type = connectionType;
	}

	// Check another return type different from the map.
	@TODO
	public Map<String, String> like(String tableName, String key) {
		if (key == null) {
			return null;
		}

		Connection con = null;
		Map<String, String> map = null;
		try {
			con = ConnectionFactory.getConnection(type);
			String like = "select key_, value_ from \"" + tableName
					+ "\" where key_ like ? order by key_ asc;";

			PreparedStatement select = con.prepareStatement(like);
			select.setString(1, key + "%");
			ResultSet result = select.executeQuery();
			map = new HashMap<String, String>();
			while (result.next()) {
				map.put(result.getString(1), result.getString(2));
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
		return map;
	}

	public void remove(String tableName, String key) {
		Connection con = null;
		try {
			con = ConnectionFactory.getConnection(type);

			String string = "delete from \"" + tableName
					+ "\" where key_ like ?;";
			PreparedStatement delete = con.prepareStatement(string);
			delete.setString(1, key + "%");
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

	// Remove table name treatment.
	@TODO
	public void insert(String tableName, Object key, Object value) {
		Connection con = null;
		try {
			con = ConnectionFactory.getConnection(type);
			String selectOne = "select key_, value_ from \"" + tableName
					+ "\" where key_ = ?;";
			PreparedStatement select = con.prepareStatement(selectOne);
			select.setObject(1, key);
			ResultSet result = select.executeQuery();
			PreparedStatement insert = null;
			if (!result.next()) {
				String insertQuery = "insert into " + tableName
						+ " values (?, ?) ;";
				insert = con.prepareStatement(insertQuery);
				insert.setObject(1, key);
				insert.setObject(2, value);
			} else {
				String updateQuery = "update \"" + tableName
						+ "\" set value_ = ? where key_ = ?;";
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

	// Remove table name treatment.
	@TODO
	public Object find(String tableName, String key) {
		Connection con = null;
		try {
			con = ConnectionFactory.getConnection(type);
			String selectOne = "select key_, value_ from \"" + tableName
					+ "\" where key_ = ?;";
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
}
