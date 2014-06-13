package br.com.innovatium.mumps2java.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class NMDAO {
	private final Connection con;

	public NMDAO() {
		this(ConnectionType.DATASOURCE_RELATIONAL);
	}

	public NMDAO(ConnectionType connectionType) {
		try {
			con = ConnectionFactory.getConnection(connectionType);
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to database access throught "
							+ connectionType + " strategy", e);
		}
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
		String columnsVal = "";

		for (int i = 0; i < columnsMap.length; i++) {
			columnsVal = columnsVal + (columnsVal.isEmpty() ? "" : ",") + "?";
		}
		try {
			String strcmd = "insert into " + tableName + " (" + columns + ")"
					+ " values (" + columnsVal + ")";

			cmd = populateStatement(tableName, columnsVal, values, columnsMap,
					strcmd);

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
			String columns, Object[] values, Object[] columnsMap, String strcmd)
			throws SQLException {

		PreparedStatement cmd = con.prepareStatement(strcmd);
		for (int i = 0; i < columnsMap.length; i++) {
			if (values[i] instanceof Date) {
				cmd.setDate(i + 1,
						(new java.sql.Date(((Date) values[i]).getTime())));
			} else if (values[i] instanceof Integer) {
				cmd.setInt(i + 1, (int) values[i]);
			} else if (values[i] instanceof Double) {
				cmd.setDouble(i + 1, (double) values[i]);
			} else {
				cmd.setObject(i + 1, values[i] == null ? "''" : values[i]);
			}
			
			System.out.println("coluna "+(i+1)+": "+columnsMap[i]+" valor: "+values[i]);

		}
		return cmd;
	}

	public String updateRecord(String tableName, String id, String columns,
			Object[] values) {
		PreparedStatement cmd = null;
		String[] columnsMap = columns.split(",");
		try {
			String strcmd = "update " + tableName + " set ";
			for (int i = 0; i < columnsMap.length; i++) {
				strcmd = strcmd + columnsMap[i] + " = ?"
						+ ((i + 1) < columnsMap.length ? "," : "");
			}
			strcmd = strcmd + " where ID = ?";

			cmd = populateStatement(tableName, columns, values, columnsMap,
					strcmd);

			cmd.setString(columnsMap.length + 1, id);
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

	public void releaseResouce(PreparedStatement ps) {
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
