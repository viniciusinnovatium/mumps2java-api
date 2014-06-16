package br.com.innovatium.mumps2java.dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import br.com.innovatium.mumps2java.todo.TODO;

@Stateless
public class MetadataDAOImpl extends AbstractDAO implements MetadataDAO {
	private Set<String> tableCache = new HashSet<String>(30);

	public MetadataDAOImpl() {
		this(ConnectionType.DATASOURCE_METADATA);
	}

	public MetadataDAOImpl(ConnectionType connectionType) {
		super(connectionType);

		PreparedStatement ps;
		try {
			ps = con.prepareStatement(resolver
					.resolve(SQLType.SELECT_TABLE_NAME));
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				tableCache.add(rs.getString(1).toLowerCase());
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Table cache name was not loaded",
					e);
		}

	}

	// Check another return type different from the map.
	@TODO
	public Map<String, String> like(String tableName, String key) {
		if (!hasTable(tableName)) {
			return null;
		}

		if (key == null) {
			return null;
		}
		key = (key==" ")?"":key;
		Map<String, String> map = null;
		PreparedStatement select = null;
		ResultSet result = null;
		try {

			String like = "select key_, value_ from " + tableName
					+ " where key_ like ? order by key_ asc";

			select = con.prepareStatement(like);
			select.setString(1, key + "%");
			result = select.executeQuery();
			map = new HashMap<String, String>();
			while (result.next()) {
				map.put(result.getString(1),
						result.getString(2) != null ? result.getString(2) : "");
			}

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to find data thought like clause from table "
							+ tableName + " and key " + key, e);
		} finally {
			closeResouce(select);
		}
		return map;
	}

	public void remove(String tableName, String key) {
		if (!hasTable(tableName)) {
			return;
		}

		PreparedStatement delete = null;
		try {

			String string = "delete from " + tableName + " where key_ like ?";
			delete = con.prepareStatement(string);
			delete.setString(1, key + "%");
			delete.execute();

		} catch (SQLException e) {
			throw new IllegalStateException("Fail to remove data from table "
					+ tableName + " and key " + key, e);
		} finally {
			closeResouce(delete);
		}
	}

	// Remove table name treatment.
	@TODO
	public void insert(String tableName, Object key, Object value) {
		if (!hasTable(tableName)) {
			createTable(tableName);
		}

		PreparedStatement ps = null;
		ResultSet result = null;

		try {

			String selectOne = "select key_, value_ from " + tableName
					+ " where key_ = ?";
			ps = con.prepareStatement(selectOne);
			ps.setObject(1, key);
			result = ps.executeQuery();

			if (!result.next()) {
				closeResouce(ps);
				String insertQuery = "insert into " + tableName
						+ " values (?, ?)";
				ps = con.prepareStatement(insertQuery);
				ps.setString(1, String.valueOf(key));
				ps.setObject(2, value);
			} else {
				closeResouce(ps);
				String updateQuery = "update " + tableName
						+ " set value_ = ? where key_ = ?";
				ps = con.prepareStatement(updateQuery);
				ps.setObject(1, value);
				ps.setString(2, String.valueOf(key));
			}

			ps.execute();

		} catch (SQLException e) {

			throw new IllegalStateException("Fail to insert data into table "
					+ tableName + " and key " + key, e);
		} finally {
			closeResouce(ps);
		}
	}

	public boolean hasTable(String tableName) {
		return tableCache.contains(tableName.toLowerCase());
	}

	// Remove table name treatment.
	@TODO
	public Object find(String tableName, String key) {
		if (!hasTable(tableName)) {
			return null;
		}
		Object objResult = null;
		PreparedStatement ps = null;
		ResultSet result = null;

		try {

			final StringBuilder selectOne = new StringBuilder(
					"select key_, value_ from ").append(tableName).append(
					" where key_ = ?");

			ps = con.prepareStatement(selectOne.toString());
			ps.setString(1, key);
			result = ps.executeQuery();
			objResult = result.next() ? (result.getString(2) != null ? result
					.getString(2) : "") : null;
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to select data from the table " + tableName
							+ " and key " + key, e);
		} finally {
			closeResouce(ps);
		}
		return objResult;
	}

	public void createTable(String tableName) {
		if (hasTable(tableName)) {
			return;
		}

		PreparedStatement ps = null;
		try {
			final StringBuilder selectOne = new StringBuilder("CREATE TABLE ");
			selectOne.append(tableName);
			selectOne.append(" ( KEY_ ")
					.append(resolver.resolve(SQLType.STRING))
					.append(" NOT NULL ,");
			selectOne.append(" VALUE_ ")
					.append(resolver.resolve(SQLType.STRING)).append(")");

			ps = con.prepareStatement(selectOne.toString());
			ps.execute();

		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to create table " + tableName, e);
		} finally {
			closeResouce(ps);
		}
	}

	private void closeResouce(PreparedStatement ps) {
		try {
			if (ps != null && !ps.isClosed()) {
				ps.close();
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to close prepare statement",
					e);
		}
	}

}
