package com.disclinc.netmanager.metadatacache.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import br.com.innovatium.mumps2java.metadatacache.MetadataCache;

public class MetadataCacheTest {

	@Test
	public void testMetadataConcurrencyAccess() {
		generateThreads();
	}

	private void generateThreads() {

		List<Thread> threads = new ArrayList<Thread>();
		int total = 10;
		MetadataUpdate update = null;
		String global = null;
		for (int i = 0; i < total; i++) {
			global = "www00" + (i < total / 2 ? 1 : 2);
			update = new MetadataUpdate(new Object[] { global }, 1);

			// Criando threads para acesso a global www001 e www002
			threads.add(new Thread(update, global + ":" + i));
		}

		for (Thread thread : threads) {
			thread.start();
		}
	}

}

class MetadataUpdate implements Runnable {

	private final MetadataCache cache = MetadataCache.getCache();
	private final Object[] subs;
	private final Object value;

	public MetadataUpdate(Object[] subs, Object value) {
		this.subs = subs;
		this.value = value;
	}

	@Override
	public void run() {
		cache.set(subs, value);
	}

}