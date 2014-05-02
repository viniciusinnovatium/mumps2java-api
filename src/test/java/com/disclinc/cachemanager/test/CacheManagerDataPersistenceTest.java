package com.disclinc.cachemanager.test;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKey;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import br.com.innovatium.mumps2java.cachemanager.CacheManager;
import br.com.innovatium.mumps2java.cachemanager.CacheManagerFactory;
import br.com.innovatium.mumps2java.cachemanager.CacheType;

public class CacheManagerDataPersistenceTest {
	private CacheManager cacheManager = CacheManagerFactory.create(CacheType.JDBC);

	@Test
	public void testJDBCDataPersistence() {
		String path = "vinicius";
		cacheManager.put(generateKey("www001", path), 98);
		
		assertEquals("Fail on search key/value pair", "98", cacheManager.get(path));
	}
	
	@Test
	public void testJDBCDataUpdating() {
		String path = "vinicius";
		cacheManager.put(generateKey("www001", path), "xxx");
		assertEquals("Fail on search key/value pair", "xxx", cacheManager.get(path));
		
		cacheManager.put(generateKey("www001", path), "aaa");
		assertEquals("Fail on search key/value pair", "aaa", cacheManager.get(path));
	}
	
	@Test
	public void testJDBCDataDeleting() {
		String path = "maria";
		cacheManager.put(generateKey("www001", path), "mae de deus");
		assertEquals("Fail on search key/value pair", "mae de deus", cacheManager.get(path));
		
		cacheManager.remove(path);
		assertEquals("Fail on search key/value pair", null, cacheManager.get(path));
	}
	
	@Test
	public void testJDBCDataSearching() {
		cacheManager.put(generateKey("www001", "qwer1"), "mae de deus");
		cacheManager.put(generateKey("www001", "qwer2"), "mae de deus");
		cacheManager.put(generateKey("www001", "qwer3"), "mae de deus");
		
		List<Object[]> nodes = cacheManager.like("qwer");
		assertEquals("Fail on search key/value pair searching data. The result number does not macth.", 3, nodes.size());
	}
	
	@Test
	public void testJDBCDataOrdering() {
		cacheManager.put(generateKey("www001", "maria10"), "mae de deus");
		cacheManager.put(generateKey("www001", "maria3"), "mae de deus");
		cacheManager.put(generateKey("www001", "maria2"), "mae de deus");
		cacheManager.put(generateKey("www001", "maria1"), "mae de deus");
		
		List<Object[]> nodes = cacheManager.like("maria");
		for (Object[] node : nodes) {
			System.out.println(node[0]);
		}
	}
	
	@Test
	public void testJDBCKillData() {
		cacheManager.put(generateKey("www001", "a/w/3/3/4/5"), "mae de deus");
		cacheManager.put(generateKey("www001", "a/w/3/3"), "mae de deus");
		cacheManager.put(generateKey("www001", "a/w/3"), "mae de deus");
		cacheManager.put(generateKey("www001", "a/w/2/1"), "mae de deus");
		cacheManager.put(generateKey("www001", "a/w/2"), "mae de deus");
		
		List<Object[]> nodes = cacheManager.like("a/w");
		assertEquals("Fail on search key/value pair searching data. The result number does not macth.", 5, nodes.size());
		
		cacheManager.kill("a/w/3");
		
		nodes = cacheManager.like("a/w/3");
		assertEquals("Fail on search key/value pair searching data. The result number does not macth.", 0, nodes.size());
		
		nodes = cacheManager.like("a/w");
		assertEquals("Fail on search key/value pair searching data. The result number does not macth.", 2, nodes.size());
		
	}
}
