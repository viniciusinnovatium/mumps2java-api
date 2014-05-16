package mLibrary;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKey;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyOfParent;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyToLikeQuery;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKeyWithoutVarName;
import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateTableName;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.innovatium.mumps2java.dataaccess.DAO;

public class mDataGlobalAcess extends mDataAccess {
	private DAO dao;
	final Set<String> cacheOrderFunction = new HashSet<String>(50);
	
	public mDataGlobalAcess(mVariables mVariables){
		super(mVariables, mDataAccess.GLOBAL);
	}

	public Object get(Object... subs) {
		if (isDiskAccess(subs)) {
			initDAO();
			final String tableName = generateTableName(subs);
			return dao.find(tableName, generateKeyWithoutVarName(subs));
		} else {
			return tree.get(subs);
		}

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
			if (currentSubs != null) {
				initDAO();
				final String tableName = generateTableName(currentSubs);
				// Here we have calling toString method because ListObject
				// should be persisted as string
				dao.insert(tableName, generateKeyWithoutVarName(currentSubs),
						value != null ? value.toString() : null);
			}
		} else {
			tree.set(currentSubs, value);
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

	public void stackingExcept(Object... variables) {
		tree.stackingExcept(variables);
	}

	public void unstacking() {
		tree.unstacking();
	}

	public String dump() {
		return tree.dump();
	}

	public void kill(Object[] subs) {
		currentSubs = null;
		if (isDiskAccess(subs)) {
			initDAO();
			dao.remove(generateTableName(subs), generateKey(subs));
		} else {
			tree.kill(subs);
		}
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
		if (isDiskAccess(currentSubs)
				&& !cacheOrderFunction
						.contains(key = generateKeyOfParent(currentSubs))) {

			cacheOrderFunction.add(key);
			initDAO();
			findDataOnDisk();
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

	private void initDAO() {
		if (dao == null) {
			this.dao = new DAO();
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
				tree.set(tree.generateSubs(tableName, entry.getKey()),
						entry.getValue());
			}
		}
	}
}
