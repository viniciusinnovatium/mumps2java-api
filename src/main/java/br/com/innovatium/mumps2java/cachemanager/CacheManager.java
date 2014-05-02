package br.com.innovatium.mumps2java.cachemanager;

import java.util.List;
import java.util.Map;

import br.com.innovatium.mumps2java.datastructure.Node;


public abstract class CacheManager {

	private final Map<Object, Object> cache;

	public CacheManager(Map<Object, Object> cache) {
		this.cache = cache;
	}

	public int size() {
		return cache.size();
	}

	public void remove(String key) {
		cache.remove(key);
	}
	
	public void kill(String key) {
		cache.remove(key);
	}

	public void put(String key, Object value) {
		cache.put(key, value);
	}

	public Object get(String key) {
		return cache.get(key);
	}

	public abstract List<Object[]> like(String path);
	
	public void put(Map<Object, Node> mapa) {
		cache.putAll(mapa);
	}

	public String dump() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		int lastIndex = cache.size() - 1;

		for (Object x : cache.keySet()) {
			builder.append("\"").append(x.toString()).append("\":")
					.append(cache.get(x.toString()));
			if (lastIndex-- > 0) {
				builder.append(", \n");
			}
		}
		builder.append("}");
		return builder.toString();
	}	
}