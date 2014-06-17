package br.com.innovatium.mumps2java.dataaccess;

import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.innovatium.mumps2java.dataaccess.exception.SQLExecutionException;

public class AbstractDAO {
	final Connection con;

	SQLResolver resolver = null;

	AbstractDAO() {
		this(ConnectionType.DATASOURCE_RELATIONAL);
	}

	AbstractDAO(ConnectionType connectionType) {
		try {
			con = ConnectionFactory.getConnection(connectionType);
			resolver = SQLResolver.getResolver(con.getMetaData()
					.getDatabaseProductName());
		} catch (SQLException e) {
			throw new IllegalStateException(
					"Fail to open connection to database access throught "
							+ connectionType + " strategy", e);
		}
	}

	String toString(Clob clob) throws SQLExecutionException {
		int length = -1;
		Reader reader = null;
		try {
			if (clob == null || (length = (int) clob.length()) <= 0) {
				return null;
			}
			reader = clob.getCharacterStream();
		} catch (SQLException e) {
			throw new SQLExecutionException("Fail to open clob object", e);
		}

		char[] buffer = new char[length];
		StringBuilder string = new StringBuilder();
		try {
			while (reader.read(buffer) != -1) {
				string.append(buffer);
			}
		} catch (IOException e) {
			throw new SQLExecutionException("Fail to read clob buffer", e);
		}
		return string.toString();
	}
}
