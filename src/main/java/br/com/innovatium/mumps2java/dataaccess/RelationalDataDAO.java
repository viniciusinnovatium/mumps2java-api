package br.com.innovatium.mumps2java.dataaccess;

import java.sql.ResultSet;

import javax.ejb.Local;

@Local
public interface RelationalDataDAO {
	boolean existsRecord(String tableName, String id);

	ResultSet loadRecord(String tableName, String id, String columnString);

	String insertRecord(String tableName, String columns, Object[] values);

	String updateRecord(String tableName, String id, String columns,
			Object[] values);

	String deleteRecord(String tableName, String id);

}
