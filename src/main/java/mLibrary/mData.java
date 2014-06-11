package mLibrary;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyToLikeQuery;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyWithoutVarName;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateSubs;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateTableName;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.innovatium.mumps2java.dataaccess.DAO;
import br.com.innovatium.mumps2java.dataaccess.ServiceLocator;
import br.com.innovatium.mumps2java.dataaccess.ServiceLocatorException;
import br.com.innovatium.mumps2java.datastructure.MetadataCache;
import br.com.innovatium.mumps2java.datastructure.Tree;

public class mData {
	Object[] currentSubs;

	DAO dao;
	final Tree tree = new Tree();

	final MetadataCache metadataCache = MetadataCache.getCache();

	public mData() {
		try {
			this.dao = ServiceLocator.locate(DAO.class);
		} catch (ServiceLocatorException e) {
			throw new IllegalArgumentException(
					"Fail to create data access object", e);
		}
	}

	public Object get(Object... subs) {
		if (isDiskAccess(subs)) {
			Object value = metadataCache.get(subs);
			if (value != null) {
				return value;
			}
			final String tableName = generateTableName(subs);
			value = dao.find(tableName, generateKeyWithoutVarName(subs));
			if (value != null) {
				metadataCache.set(subs, value);
			}

			return value;
		}
		return tree.get(subs);
	}

	/*
	 * This method was create to support lastVar function and should not remove.
	 */
	public Object[] getCurrentSubs() {
		return currentSubs;
	}

	public boolean isEmpty() {
		return tree.isEmpty();
	}

	public void set(Object value) {
		if (isDiskAccess(currentSubs)) {
			if (currentSubs != null && value != null) {
				metadataCache.set(currentSubs, value.toString());
			}
		} else {
			if (value != null) {
				tree.set(currentSubs, value);
			}

		}
	}

	public void merge(Object[] dest, Object[] orig) {
		if (isDiskAccess(dest)) {
			metadataCache.merge(dest, orig);
		} else {
			tree.merge(dest, orig);
		}
	}

	public void stacking(Object... variables) {
		if (!isDiskAccess(variables)) {
			tree.stacking(variables);
		} else {
			throw new UnsupportedOperationException(
					"Stacking variable is not supported to access data on disk");
		}
	}

	public void stackingBlock(int indexBlock, Object... variables) {
		if (!isDiskAccess(variables)) {
			tree.stackingBlock(indexBlock, variables);
		} else {
			throw new UnsupportedOperationException(
					"Stacking variable in block is not supported to access data on disk");
		}
	}

	public void stackingExceptBlock(int indexBlock, Object... variables) {
		if (!isDiskAccess(variables)) {
			tree.stackingExceptBlock(indexBlock, variables);
		} else {
			throw new UnsupportedOperationException(
					"Stacking variable in block is not supported to access data on disk");
		}
	}

	public void stackingExcept(Object... variables) {
		tree.stackingExcept(variables);
	}

	public void unstacking() {
		tree.unstacking();
	}

	public void unstackingBlock(int indexBlock) {
		tree.unstackingBlock(indexBlock);
	}

	public String dump() {
		return tree.dump();
	}

	public void kill(Object... subs) {
		currentSubs = null;
		if (isDiskAccess(subs)) {
			metadataCache.kill(subs);
		} else {
			tree.kill(subs);
		}
	}

	public int data(Object... subs) {
		currentSubs = subs;

		if (isDiskAccess(subs)) {
			populateTree(false);
			return metadataCache.data(subs);
		}
		return tree.data(subs);
	}

	public Object order(Object[] subs, int direction) {
		this.currentSubs = subs;

		if (isDiskAccess(subs)) {
			populateTree(true);
			return metadataCache.order(subs);
		}
		return tree.order(subs, direction);
	}

	public Object order(Object[] subs) {
		return order(subs, 1);
	}

	public mData subs(Object... subs) {
		currentSubs = subs;
		return this;
	}

	private void populateTree(boolean isOrder) {
		if (isDiskAccess(currentSubs) && !metadataCache.isQueried(currentSubs)) {
			findDataOnDisk(isOrder);
			metadataCache.addQueried(currentSubs);
		}
	}

	private boolean isDiskAccess(Object... subs) {
		boolean bool = false;
		if (subs != null && subs.length > 0 && subs[0] != null
				&& !subs[0].toString().isEmpty()) {
			bool = subs[0].toString().charAt(0) == '^';
		}
		return bool;
	}

	private void findDataOnDisk(boolean isOrder) {

		String tableName = generateTableName(currentSubs);
		Map<String, String> map = null;
		if (isOrder) {
			map = dao.like(tableName, generateKeyToLikeQuery(currentSubs));
		} else {
			map = dao.like(tableName, generateKeyWithoutVarName(currentSubs));
		}

		if (map != null) {
			Set<Entry<String, String>> result = map.entrySet();
			for (Entry<String, String> entry : result) {
				// Here we have to include variable or table name into the key
				// because this is part of the subscripts.
				metadataCache.set(generateSubs(tableName, entry.getKey()),
						entry.getValue());
			}
		}

		Object value = get(currentSubs);
		if (value != null) {
			metadataCache.set(currentSubs, value);
		}
	}
}
