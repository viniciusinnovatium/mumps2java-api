package br.com.innovatium.mumps2java.dataaccess;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

final class SQLResolver {
	private Map<SQLType, String> sql = new HashMap<SQLType, String>();
	private final SQLResolverType type;
	private static final DateFormat dateFormat = new SimpleDateFormat(
			"dd/MM/yyyy HH:mm:ss");

	private SQLResolver(SQLResolverType type) {
		if (SQLResolverType.POSTGRE.equals(type)) {
			initPostgreSQL();
		} else if (SQLResolverType.ORACLE.equals(type)) {
			initOracleSQL();
		} else if (SQLResolverType.MYSQL.equals(type)) {
			initMySQL();
		} else {
			throw new IllegalArgumentException(
					"This database instruction was not implemented.");
		}
		this.type = type;
	}

	private void initPostgreSQL() {
		sql = new HashMap<SQLType, String>();
		sql.put(SQLType.STRING, "varchar(5000)");
		sql.put(SQLType.DROP_TABLE, "drop table");
		sql.put(SQLType.CREATE_TABLE, "create table");
		sql.put(SQLType.SELECT_TABLE_NAME,
				"SELECT tablename FROM pg_tables where schemaname = 'public' ");
	}

	private void initMySQL() {
		sql = new HashMap<SQLType, String>();
		sql.put(SQLType.STRING, "varchar(4000)");
		sql.put(SQLType.DROP_TABLE, "drop table");
		sql.put(SQLType.CREATE_TABLE, "create table");
		sql.put(SQLType.SELECT_TABLE_NAME, "SHOW TABLES from metauser");
	}

	private void initOracleSQL() {
		sql = new HashMap<SQLType, String>();
		sql.put(SQLType.STRING, "varchar2(4000)");
		sql.put(SQLType.DROP_TABLE, "drop table");
		sql.put(SQLType.CREATE_TABLE, "create table");
		sql.put(SQLType.SELECT_TABLE_NAME, "select table_name from ALL_TABLES");
		sql.put(SQLType.TO_DATE, "TO_DATE('X', 'DD/MM/YYYY HH24:MI:SS')");

	}

	public static SQLResolver getResolver(SQLResolverType type) {
		return new SQLResolver(type);
	}

	public static SQLResolver getResolver(String description) {
		return getResolver(SQLResolverType.getType(description));
	}

	public String resolve(SQLType type) {
		return sql.get(type);
	}

	public String toDate(Date date) {
		if (date == null) {
			return null;
		}

		if (SQLResolverType.ORACLE.equals(type)) {
			return sql.get(SQLType.TO_DATE).replaceAll("X",
					dateFormat.format(date));
		}
		throw new IllegalArgumentException();
	}

	public static void main(String[] asd) {
		long x = 343440000000L;
		System.out.println(new Date(x));

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		System.out.println(dateFormat.format(new Date(x)));
	}
}