package com.disclinc.cachemanager.test;
import static org.junit.Assert.*;

import org.junit.Test;

import br.com.innovatium.mumps2java.cachemanager.CacheManager;
import br.com.innovatium.mumps2java.cachemanager.CacheManagerFactory;
import br.com.innovatium.mumps2java.cachemanager.CacheType;
import br.com.innovatium.mumps2java.datastructure.Node;


public class MultiCacheAccessDataTest {

	private final CacheManager cache1 = CacheManagerFactory.create(CacheType.JDBC);
	private final CacheManager cache2 = CacheManagerFactory.create(CacheType.JDBC);;
	private final String path = "pedido";
	
	public MultiCacheAccessDataTest() {
		Node n = new Node(path, path, "77");
		cache1.put(n);
	}
	
	@Test
	public void testRecoveringValueIntoCache() {
		assertEquals("Fail to recover existing value into cache", "77", cache1.get(path));
	}
	
	@Test
	public void testRecoveringValueIntoCacheByAnotherCache() {
		assertEquals("Fail to recover existing value into cache", "77", cache2.get(path));
	}
}
