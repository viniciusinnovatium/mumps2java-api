package br.com.innovatium.mumps2java.cachemanager;

public final class CacheManagerFactory {

	
	private CacheManagerFactory() {
	}

	public static CacheManager create(CacheType cacheType) {
		if (CacheType.HOT_ROD.equals(cacheType)) {
			return new CacheManagerHotRod();
		}
		
		if (CacheType.DATASOURCE.equals(cacheType)) {
			return new CacheManagerDatasource();
		}

		if (CacheType.JDBC.equals(cacheType)) {
			return new CacheManagerJDBC();
		}
		
		if (CacheType.DEFAULT.equals(cacheType)) {
			//return new CacheManagerDefault();
			return null;
		}

		throw new IllegalArgumentException(
				"There is not cache to chosen type: " + cacheType);
		/*
		 * c.persistence()
		 * .addStore(JdbcStringBasedStoreConfigurationBuilder.class)
		 * .fetchPersistentState(false).ignoreModifications(false)
		 * .purgeOnStartup(false).table().dropOnExit(true)
		 * .createOnStart(true).tableNamePrefix("ISPN_STRING_TABLE")
		 * .idColumnName("ID_COLUMN").idColumnType("VARCHAR(255)")
		 * .dataColumnName("DATA_COLUMN").dataColumnType("VARCHAR(255)")
		 * .timestampColumnName("TIMESTAMP_COLUMN")
		 * .timestampColumnType("BIGINT").connectionPool()
		 * .connectionUrl("jdbc:postgresql://localhost:5432/infinispan")
		 * .username("postgres").password("@postgresql15")
		 * .driverClass("org.postgresql.Driver").build();
		 */

	}

}