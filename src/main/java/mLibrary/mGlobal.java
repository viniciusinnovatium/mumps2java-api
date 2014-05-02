package mLibrary;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.innovatium.mumps2java.cachemanager.CacheManager;
import br.com.innovatium.mumps2java.cachemanager.CacheManagerFactory;
import br.com.innovatium.mumps2java.cachemanager.CacheType;
import br.com.innovatium.mumps2java.dataaccess.ConnectionType;
import br.com.innovatium.mumps2java.dataaccess.DAO;
import br.com.innovatium.mumps2java.datastructure.Tree;
import br.com.innovatium.mumps2java.todo.TODO;

public class mGlobal extends mData {
	private final CacheManager cacheManager;
	private DAO dao = new DAO(ConnectionType.DATASOURCE);
	private String tableName;

	public mGlobal(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	public mGlobal(CacheType cacheType) {
		this(CacheManagerFactory.create(cacheType));
	}

	public mGlobal() {
		this(CacheType.REMOTE);
	}

	public mData subs(Object... subs) {
		tableName = subs[0].toString().replace("^", "");
		return super.subs(Arrays.copyOfRange(subs, 1,
				subs.length));
	}

	// Implements the caching data srategy here
	@TODO
	public void set(Object value) {
		if (tempSubs != null) {
			// cacheManager.put(Tree.generateKey(tempSubs), value);
			dao.insert(tableName, Tree.generateKey(tempSubs), value);
		}
	}

	// Remove table name treatment
	@TODO
	public Object get(Object... subs) {
		if (subs == null) {
			return null;
		}
		String tableName = subs[0].toString().replace("^", "");
		subs = Arrays.copyOfRange(subs, 1, subs.length);
		return dao.find(tableName, Tree.generateKey(subs));
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
		Map<String, String> map = dao.like(tableName,
				Tree.generateKey(brothers));
		if (map != null) {
			Set<Entry<String, String>> result = map.entrySet();
			for (Entry<String, String> entry : result) {
				tree.set(tree.generateSubs(entry.getKey()), entry.getValue());
			}
		}
	}

	@Override
	void unstacking() {
		throw new UnsupportedOperationException();
	}

	@Override
	String dump() {
		throw new UnsupportedOperationException();
	}

	@Override
	void stacking(String... subs) {
		throw new UnsupportedOperationException();
	}

	@Override
	void stackingExcept(String... subs) {
		throw new UnsupportedOperationException();
	}
}
