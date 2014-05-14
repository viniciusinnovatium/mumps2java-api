package mLibrary;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.innovatium.mumps2java.datastructure.Tree;

public class mDatabaseAcess extends mData {

	public Object get(Object... subs) {
		final String tableName = generateTableName(subs);
		subs = Arrays.copyOfRange(subs, 1, subs.length);
		return dao.find(tableName, Tree.generateKey(subs));
	}

	public void set(Object value) {
		if (currentSubs != null) {
			final String tableName = generateTableName(currentSubs);
			currentSubs = Arrays
					.copyOfRange(currentSubs, 1, currentSubs.length);
			// Here we have calling toString method because ListObject
			// should be persisted as string
			dao.insert(tableName, Tree.generateKey(currentSubs),
					value != null ? value.toString() : null);
		}
	}

	public void merge(Object[] dest, Object[] orig) {
		tree.merge(dest, orig);
	}

	public void stacking(Object... subs) {
		throw new UnsupportedOperationException(
				"Stacking variable is not supported to access data on disk");
	}

	public void stackingExcept(Object... subs) {
		throw new UnsupportedOperationException(
				"Stacking variable is not supported to access data on disk");
	}

	public void unstacking() {
		throw new UnsupportedOperationException(
				"Unstacking variable is not supported to access data on disk");
	}

	public String dump() {
		return tree.dump();
	}

	public void kill(Object... subs) {
		currentSubs = null;
		dao.remove(generateTableName(subs), Tree.generateKey(subs));
	}

	public int data(Object... subs) {
		verifySubsChanges(subs);
		currentSubs = subs;
		populateTree();
		return tree.data(subs);
	}

	public Object order(Object[] subs, int direction) {
		if (subsChanged) {
			this.currentSubs = subs;
			findDataOnDisk();
			firstExecutionOrder = true;
		}
		return tree.order(subs, direction);
	}

	public Object order(Object... subs) {
		return tree.order(subs, 1);
	}

	public mDatabaseAcess subs(Object... subs) {
		verifySubsChanges(subs);
		currentSubs = subs;
		return this;
	}

	private void populateTree() {
		if (subsChanged) {
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
