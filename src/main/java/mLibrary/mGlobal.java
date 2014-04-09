package mLibrary;

import java.util.Arrays;
import java.util.List;

import br.com.innovatium.mumps2java.cachemanager.CacheManager;
import br.com.innovatium.mumps2java.cachemanager.CacheManagerFactory;
import br.com.innovatium.mumps2java.cachemanager.CacheType;
import br.com.innovatium.mumps2java.datastructure.Node;
import br.com.innovatium.mumps2java.datastructure.Tree;

public class mGlobal extends mData {

	private final CacheManager cacheManager;

	public mGlobal(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public mGlobal(CacheType cacheType) {
		this(CacheManagerFactory.create(cacheType));
	}

	public mGlobal() {
		this(CacheType.REMOTE);
	}

	public void set(Object value) {
		if (tempSubs != null) {
			cacheManager.put(Tree.generateKey(tempSubs), value);
		}
	}

	public Object get(Object... subs) {
		return cacheManager.get(Tree.generateKey(subs));
	}

	void onOrder() {
		addNodes();
	}

	public void onKill(Object... subs) {
		cacheManager.kill(Tree.generateKey(subs));
	}

	void onPopulateTree() {
		addNodes();
	}

	private void addNodes() {
		Object[] brothers = Arrays
				.copyOfRange(tempSubs, 0, tempSubs.length - 1);
		List<Node> nodes = cacheManager.like(Tree.generateKey(brothers));

		for (Node node : nodes) {
			tree.add(node);
		}
	}
}
