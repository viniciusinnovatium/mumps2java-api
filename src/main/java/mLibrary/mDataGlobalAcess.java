package mLibrary;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKey;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyOfParent;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyToLikeQuery;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyWithoutVarName;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateSubs;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateTableName;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.innovatium.mumps2java.dataaccess.DAO;
import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;

public class mDataGlobalAcess extends mDataAccess {
	private DAO dao = new DAO();
	final Set<String> cacheOrderFunction = new HashSet<String>(50);

	public mDataGlobalAcess(mVariables mVariables) {
		super(mVariables, DataStructureUtil.GLOBAL);
	}

	public Object get(Object... subs) {
		return dao.find(generateTableName(subs),
				generateKeyWithoutVarName(subs));
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
		final String tableName = generateTableName(currentSubs);
		// Here we have calling toString method because ListObject
		// should be persisted as string
		dao.insert(tableName, generateKeyWithoutVarName(currentSubs),
				value != null ? value.toString() : null);
	}

	public void stacking(Object... variables) {
		throw new UnsupportedOperationException(
				"Stacking variable is not supported to access data on disk");
	}

	public void stackingExcept(Object... variables) {
		throw new UnsupportedOperationException(
				"Stacking variable is not supported to access data on disk");
	}

	public void unstacking() {
		throw new UnsupportedOperationException(
				"Stacking variable is not supported to access data on disk");
	}

	public String dump() {
		return tree.dump();
	}

	public void kill(Object[] subs) {
		currentSubs = null;
		dao.remove(generateTableName(subs), generateKey(subs));
		tree.kill(subs);
	}

	public int data(Object... subs) {
		currentSubs = subs;
		populateTree();
		return tree.data(subs);
	}

	public Object order(Object[] subs, int direction) {
		this.currentSubs = subs;
		populateTree();
		return tree.order(subs, direction);
	}

	public Object order(Object... subs) {
		return order(subs, 1);
	}

	private void populateTree() {
		String key = null;
		if (!cacheOrderFunction
				.contains(key = generateKeyOfParent(currentSubs))) {

			cacheOrderFunction.add(key);
			findDataOnDisk();
		}
	}

	private void findDataOnDisk() {

		final String tableName = generateTableName(currentSubs);

		Map<String, String> map = dao.like(tableName,
				generateKeyToLikeQuery(currentSubs));
		if (map != null) {
			Set<Entry<String, String>> result = map.entrySet();
			for (Entry<String, String> entry : result) {
				// Here we have to include variable or table name into the key
				// because this is part of the subscripts.
				tree.set(generateSubs(tableName, entry.getKey()),
						entry.getValue());
			}
		}
	}
}