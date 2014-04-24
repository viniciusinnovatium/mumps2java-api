package br.com.innovatium.mumps2java.dataaccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {
	private ConnectionFactory() {
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(
				"jdbc:postgresql://localhost:5432/metadata", "postgres",
				"@postgresql15");
	}
}
