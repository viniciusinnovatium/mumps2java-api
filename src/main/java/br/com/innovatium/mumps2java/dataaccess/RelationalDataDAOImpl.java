package br.com.innovatium.mumps2java.dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.Stateless;

@Stateless
public class RelationalDataDAOImpl extends AbstractDAO implements
		RelationalDataDAO {

	public RelationalDataDAOImpl() {
		super(ConnectionType.DATASOURCE_RELATIONAL);
	}

	public boolean existsRecord(String tableName, String id) {
		boolean exists = false;
		PreparedStatement cmd = null;
		ResultSet result = null;
		try {
			String strcmd = "select 1 from " + tableName + " where ID = ?";
			cmd = con.prepareStatement(strcmd);
			cmd.setString(1, id);
			result = cmd.executeQuery();
			while (result.next()) {
				exists = true;
				break;
			}
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to select from table "
					+ tableName + " (ID " + id + ")", e);
		} finally {
			releaseResouce(cmd);
		}
		return exists;
	}

	public ResultSet loadRecord(String tableName, String id, String columnString) {
		PreparedStatement cmd = null;
		ResultSet result = null;
		StringBuilder strcmd = new StringBuilder();
		try {
			strcmd.append("select ").append(columnString).append(" from ")
					.append(tableName).append(" where ID = ?");
			cmd = con.prepareStatement(strcmd.toString());
			cmd.setString(1, id);
			result = cmd.executeQuery();
			if (!result.next()) {
				return null;
			}

		} catch (SQLException e) {
			throw new IllegalStateException("Fail to select from table "
					+ tableName + " (ID " + id + ")", e);
		}
		return result;
	}

	public String insertRecord(String tableName, String columns, Object[] values) {
		PreparedStatement cmd = null;
		String[] columnsMap = columns.split(",");

		try {
			/*
			 * String columnsVal = ""; for (int i = 0; i < columnsMap.length;
			 * i++) { columnsVal = columnsVal + (columnsVal.isEmpty() ? "" :
			 * ",") + "?"; }
			 * 
			 * String strcmd = "insert into " + tableName + " (" + columns + ")"
			 * + " values (" + columnsVal + ")";
			 * 
			 * cmd = populateStatement(tableName, values, columnsMap, strcmd);
			 */
			cmd = con.prepareStatement(generateInsert(tableName, values,
					columnsMap));
			cmd.execute();
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to insert into table "
					+ tableName, e);
		} finally {
			releaseResouce(cmd);
		}

		return "";
	}

	private PreparedStatement populateStatement(String tableName,
			Object[] values, Object[] columnsMap, String strcmd)
			throws SQLException {

		StringBuilder insert = new StringBuilder();
		insert.append("insert into ").append(tableName);

		PreparedStatement cmd = con.prepareStatement(strcmd);
		for (int i = 0; i < columnsMap.length; i++) {
			if (values[i] instanceof Timestamp) {
				cmd.setTimestamp(i + 1, (Timestamp) values[i]);
			} else if (values[i] instanceof Date) {
				cmd.setDate(i + 1,
						(new java.sql.Date(((Date) values[i]).getTime())));
			} else if (values[i] instanceof Integer) {
				cmd.setInt(i + 1, (int) values[i]);
			} else if (values[i] instanceof Double) {
				cmd.setDouble(i + 1, (double) values[i]);
			} else {
				cmd.setObject(i + 1, values[i] == null ? "''" : values[i]);
			}

			System.out.println("coluna " + (i + 1) + ": " + columnsMap[i]
					+ " valor: " + values[i]);

		}
		return cmd;
	}

	public String generateUpdate(String idRecord, String tableName,
			Object[] values, Object[] columnsMap) {

		StringBuilder updateValues = new StringBuilder();
		for (int i = 0; i < columnsMap.length; i++) {
			updateValues.append(columnsMap[i]).append("=");
			if (values[i] != null) {
				if (values[i] instanceof Date) {
					updateValues.append(resolver.toDate((Date) values[i]));
				} else {
					updateValues.append("'").append(values[i]).append("'");
				}
			} else {
				updateValues.append("null");
			}

			if (i + 1 < columnsMap.length) {
				updateValues.append(", ");
			}
		}

		StringBuilder update = new StringBuilder("update ").append(tableName)
				.append(" set ").append(updateValues);

		update.append(" where ID = '").append(idRecord).append("'");
		System.out.println(update);
		return update.toString();
	}

	public String generateInsert(String tableName, Object[] values,
			Object[] columnsMap) {

		StringBuilder columnsInsert = new StringBuilder();
		StringBuilder insertValues = new StringBuilder();
		for (int i = 0; i < columnsMap.length; i++) {

			columnsInsert.append(columnsMap[i]);
			if (values[i] != null) {
				if (values[i] instanceof Date) {
					insertValues.append(resolver.toDate((Date) values[i]));
				} else {
					insertValues.append("'").append(values[i]).append("'");
				}

			} else {
				insertValues.append("null");
			}

			if (i + 1 < columnsMap.length) {
				columnsInsert.append(", ");
				insertValues.append(", ");

			}
		}

		StringBuilder insert = new StringBuilder("insert into ")
				.append(tableName).append("(").append(columnsInsert)
				.append(")").append(" values (").append(insertValues)
				.append(")");

		System.out.println(insert);
		return insert.toString();
	}

	public String updateRecord(String tableName, String id, String columns,
			Object[] values) {

		PreparedStatement cmd = null;

		try {
			String[] columnsMap = columns.split(",");
			/*
			 * 
			 * 
			 * String strcmd = "update " + tableName + " set "; for (int i = 0;
			 * i < columnsMap.length; i++) { strcmd = strcmd + columnsMap[i] +
			 * " = ?" + ((i + 1) < columnsMap.length ? "," : ""); } strcmd =
			 * strcmd + " where ID = ?";
			 * 
			 * // cmd = populateStatement(tableName, values, columnsMap,
			 * strcmd); cmd.setString(columnsMap.length + 1, id);
			 */
			cmd = con.prepareStatement(generateUpdate(id, tableName, values,
					columnsMap));

			//
			cmd.execute();
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to update table " + tableName
					+ " (ID:" + id + ")", e);
		} finally {
			releaseResouce(cmd);
		}

		return "";
	}

	public String deleteRecord(String tableName, String id) {
		PreparedStatement cmd = null;
		try {
			String strcmd = "delete from " + tableName + " where ID = ?";
			cmd = con.prepareStatement(strcmd);
			cmd.setString(1, id);
			cmd.execute();
		} catch (SQLException e) {
			throw new IllegalStateException("Fail to delete table " + tableName
					+ " (ID:" + id + ")", e);
		} finally {
			releaseResouce(cmd);
		}
		return "";
	}

	private void releaseResouce(PreparedStatement ps) {
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
