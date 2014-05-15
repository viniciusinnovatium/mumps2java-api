package mLibrary;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.innovatium.mumps2java.dataaccess.ConnectionType;
import br.com.innovatium.mumps2java.dataaccess.DAO;
import br.com.innovatium.mumps2java.datastructure.Tree;

public class mData {
	Object[] currentSubs;

	boolean subsChanged;
	boolean firstExecutionOrder = true;
	DAO dao;
	final Tree tree = new Tree();
	final Set<String> cacheOrderFunction = new HashSet<String>(50);

	public Object get(Object... subs) {
		if (isDiskAccess(subs)) {
			initDAO();
			final String tableName = generateTableName(subs);
			subs = Arrays.copyOfRange(subs, 1, subs.length);
			return dao.find(tableName, Tree.generateKey(subs));
		} else {
			return tree.get(subs);
		}

	}

	public boolean isEmpty() {
		return tree.isEmpty();
	}

	public void set(Object value) {

		if (isDiskAccess(currentSubs)) {
			if (currentSubs != null) {
				initDAO();
				final String tableName = generateTableName(currentSubs);
				currentSubs = Arrays.copyOfRange(currentSubs, 1,
						currentSubs.length);
				// Here we have calling toString method because ListObject
				// should be persisted as string
				dao.insert(tableName, Tree.generateKey(currentSubs),
						value != null ? value.toString() : null);
			}
		} else {
			tree.set(currentSubs, value);
		}
	}

	public void merge(Object[] dest, Object[] orig) {
		tree.merge(dest, orig);
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

	public void kill(Object... subs) {
		currentSubs = null;
		if (isDiskAccess(subs)) {
			initDAO();
			dao.remove(generateTableName(subs), Tree.generateKey(subs));
		} else {
			tree.kill(subs);
		}
	}

	public int data(Object... subs) {
		verifySubsChanges(subs);
		currentSubs = subs;
		populateTree();
		return tree.data(subs);
	}

	/*
	 * public Object order(int direction) { if (subsChanged) { if
	 * (isDiskAccess(currentSubs)) { initDAO(); findDataOnDisk(); }
	 * firstExecutionOrder = true; }
	 * 
	 * if (firstExecutionOrder) { firstExecutionOrder = false; orderSubs =
	 * Arrays.copyOfRange(currentSubs, 0, currentSubs.length); return
	 * orderSubs[orderSubs.length - 1] = tree.order(currentSubs, direction); }
	 * else { return orderSubs[orderSubs.length - 1] = tree.order(orderSubs,
	 * direction); } }
	 */
	public Object order(Object[] subs, int direction) {
		if (subsChanged) {
			if (isDiskAccess(subs)) {
				initDAO();
				String key = Tree.generateKey(currentSubs);
				if (!cacheOrderFunction.contains(key)) {
					findDataOnDisk();
				}
				this.currentSubs = subs;
			}
			firstExecutionOrder = true;
		}
		return tree.order(subs, direction);
	}

	public Object order(Object[] subs) {
		return order(subs, 1);
	}

	public mData subs(Object... subs) {
		verifySubsChanges(subs);
		currentSubs = subs;
		return this;
	}

	private void populateTree() {
		if (subsChanged && isDiskAccess(currentSubs)) {
			initDAO();
			findDataOnDisk();
		}
	}

	private void verifySubsChanges(Object... subs) {
		subsChanged = false;
		if (currentSubs != null && subs != null) {
			if (currentSubs.length == subs.length) {
				for (int i = 0; i < subs.length; i++) {
					if (currentSubs[i] != null
							&& !currentSubs[i].equals(subs[i])) {
						subsChanged = true;
						break;
					}
				}
			} else {
				subsChanged = true;
			}
		} else {
			subsChanged = true;
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
			this.dao = new DAO(ConnectionType.DATASOURCE);
		}
	}

	private void findDataOnDisk() {

		Object[] brothers = Arrays.copyOfRange(currentSubs, 0,
				currentSubs.length - 1);

		final String tableName = generateTableName(currentSubs);

		Map<String, String> map = dao.like(tableName,
				Tree.generateKey(true, brothers));
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

	private String generateTableName(Object... subs) {
		return subs[0].toString().replace("^", "");
	}
}
