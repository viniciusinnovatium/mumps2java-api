package mLibrary;

import java.util.Arrays;

import br.com.innovatium.mumps2java.datastructure.Tree;

public abstract class mData {
	Object[] tempSubs;
	Object[] orderSubs;

	boolean subsChanged;
	boolean firstExecutionOrder = true;

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
			firstExecutionOrder = true;
		}

		if (firstExecutionOrder) {
			firstExecutionOrder = false;
			orderSubs = Arrays.copyOfRange(tempSubs, 0, tempSubs.length);
			return orderSubs[orderSubs.length - 1] = tree.order(direction, tempSubs);
		} else {
			return orderSubs[orderSubs.length - 1] = tree.order(direction, orderSubs);
		}
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
