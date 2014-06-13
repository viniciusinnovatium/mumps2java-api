package br.com.innovatium.mumps2java.datastructure;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyOfParent;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKey;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class QueryCache {
	final Set<String> cache = new HashSet<String>();

	public QueryCache() {
	}

	public boolean isCached(Object[] subs) {
		int last = subs.length;
		Object[] subscripts = null;
		if (subs.length == 1) {
			return cache.contains(subs[0].toString());
		}
		if (cache.contains(generateKey(subs))) {
			return true;
		}
		for (int i = last; i > 1; i--) {
			subscripts = Arrays.copyOf(subs, i);
			if (cache.contains(generateKeyOfParent(subscripts))) {
				return true;
			}
		}
		return false;
	}

	public void add(Object[] subs) {
		this.add(subs,false);
	}

	public void add(Object[] subs, boolean isOrder) {
		/*
		 *if (subs.length == 1) {
			cache.add(subs[0].toString());
		} else {
			cache.add(generateKeyOfParent(subs));
		} 
		 */
		if (isOrder) {
			cache.add(generateKeyOfParent(subs));
		}
		else {
			//cache.add(generateKeyOfParent(subs));
			cache.add(generateKey(subs));
		}
	}
}
