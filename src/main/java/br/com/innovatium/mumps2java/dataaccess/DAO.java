package br.com.innovatium.mumps2java.dataaccess;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
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
		try {
			con = ConnectionFactory.getConnection(type);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to insert data", e);
		}
		Map<String, String> map = null;
		try {

			String like = "select key_, value_ from \"" + tableName
					+ "\" where key_ like ? order by key_ asc";

			PreparedStatement select = con.prepareStatement(like);
			select.setString(1, key + "%");
			ResultSet result = select.executeQuery();
			map = new HashMap<String, String>();
			while (result.next()) {
				map.put(result.getString(1), result.getString(2));
			}

		} catch (java.sql.SQLSyntaxErrorException e) {
			if (!hasTable(tableName)) {
				return map;
			}
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to find data thought like clause from table "
							+ tableName + " and key " + key, e);
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
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to remove data", e);
		}
		try {
			String string = "delete from \"" + tableName
					+ "\" where key_ like ?";
			PreparedStatement delete = con.prepareStatement(string);
			delete.setString(1, key + "%");
			delete.execute();

		} catch (java.sql.SQLSyntaxErrorException e) {
			if (!hasTable(tableName)) {

			}
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to remove data from table "
					+ tableName + " and key " + key, e);
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
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to insert data", e);
		}

		try {

			String selectOne = "select key_, value_ from \"" + tableName.toUpperCase()
					+ "\" where key_ = ?";
			PreparedStatement select = con.prepareStatement(selectOne);
			select.setObject(1, key);
			ResultSet result = select.executeQuery();
			PreparedStatement insert = null;
			if (!result.next()) {
				String insertQuery = "insert into " + tableName.toUpperCase()
						+ " values (?, ?)";
				insert = con.prepareStatement(insertQuery);
				insert.setObject(1, key);
				insert.setObject(2, value);
			} else {
				String updateQuery = "update \"" + tableName.toUpperCase()
						+ "\" set value_ = ? where key_ = ?";
				insert = con.prepareStatement(updateQuery);
				insert.setObject(1, value);
				insert.setObject(2, key);
			}

			insert.execute();

		} catch (SQLException e) {
			if (!hasTable(tableName)) {
				createTable(tableName);
				insert(tableName, key, value);
			} else {
				throw new IllegalStateException("Fail to insert data into table "
						+ tableName + " and key " + key, e);	
			}
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

	public boolean hasTable(String tableName) {
		Connection con = null;
		boolean hasTable = false;
		try {
			con = ConnectionFactory.getConnection(type);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to hasTable data", e);
		}
		ResultSet rs = null;
		try {
			DatabaseMetaData metaData = con.getMetaData();
			rs = metaData.getTables(null, null, tableName.toUpperCase(), null);
			if (rs.next()) {
				hasTable = true;
				/*
				 * ResultSetMetaData rsMetaData = rs.getMetaData(); int
				 * columnCount = rsMetaData.getColumnCount(); for (int i = 1; i
				 * <= columnCount; i++) {
				 * if(rsMetaData.getTableName(i).equals(tableName)){ break; } }
				 */
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return hasTable;
	}

	// Remove table name treatment.
	@TODO
	public Object find(String tableName, String key) {
		Connection con = null;
		try {
			con = ConnectionFactory.getConnection(type);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to insert data", e);
		}

		try {
			final StringBuilder selectOne = new StringBuilder(
					"select key_, value_ from \"").append(tableName).append(
					"\" where key_ = ?");
			PreparedStatement ps = con.prepareStatement(selectOne.toString());
			ps.setString(1, key);
			ResultSet result = ps.executeQuery();

			return result.next() ? result.getString(2) : null;

		} catch (java.sql.SQLSyntaxErrorException e) {
			if (!hasTable(tableName)) {
				return null;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to select data from the table " + tableName
							+ " and key " + key, e);
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

	public boolean createTable(String tableName) {
		Connection con = null;
		try {
			con = ConnectionFactory.getConnection(type);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to insert data", e);
		}

		try {
			final StringBuilder selectOne = new StringBuilder("CREATE TABLE "
					+ tableName.toUpperCase()
					+ "(	\"KEY_\" VARCHAR(4000) NOT NULL,"
					+ "\"VALUE_\" VARCHAR(4000))");
			PreparedStatement ps = con.prepareStatement(selectOne.toString());
			return ps.execute();

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to create table " + tableName, e);
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
