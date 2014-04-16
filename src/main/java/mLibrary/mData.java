package mLibrary;

import br.com.innovatium.mumps2java.datastructure.Tree;

public abstract class mData {
	Object[] tempSubs;
	boolean subsChanged;
	final Tree tree = new Tree();

	public abstract Object get(Object... subs);

	public abstract void set(Object value);

	abstract void onPopulateTree();

	abstract void onOrder();

	abstract void onKill(Object... subs);

	abstract void stacking(String... subs);
	
	abstract void stackingExcept(String... subs);

	abstract void unstacking();
	
	abstract String dump();

	public void kill(Object... subs) {
		tempSubs = null;
		onKill(subs);
	}

	public int data(Object... subs) {
		tempSubs = subs;
		verifySubsChanges(subs);
		populateTree();
		return tree.data(subs);
	}

	public Object order(int direction) {
		if (subsChanged) {
			onOrder();
		}
		return tree.order(direction, tempSubs);
	}

	public mData subs(Object... subs) {
		verifySubsChanges(subs);
		tempSubs = subs;
		return this;
	}

	private void populateTree() {
		if (subsChanged) {
			onPopulateTree();
		}
	}

	private void verifySubsChanges(Object... subs) {
		subsChanged = false;
		if (tempSubs != null && subs != null) {
			if (tempSubs.length == subs.length) {
				for (int i = 0; i < subs.length; i++) {
					if (tempSubs[i] != null && !tempSubs[i].equals(subs[i])) {
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
}
