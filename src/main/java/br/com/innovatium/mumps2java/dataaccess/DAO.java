package br.com.innovatium.mumps2java.dataaccess;

import java.util.Map;

import javax.ejb.Local;

@Local
public interface DAO {
	Object find(String tableName, String key);

	Map<String, String> like(String tableName, String key);

	void insert(String tableName, Object key, Object value);

	void remove(String tableName, String key);
}
