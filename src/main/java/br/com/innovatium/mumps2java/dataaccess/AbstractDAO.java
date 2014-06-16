package br.com.innovatium.mumps2java.dataaccess;

import java.sql.Connection;
import java.sql.SQLException;

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

}
