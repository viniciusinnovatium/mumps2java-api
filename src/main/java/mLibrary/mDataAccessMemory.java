package mLibrary;

import br.com.innovatium.mumps2java.datastructure.Tree;

class mDataAccessMemory extends mDataAccess {

	final Tree tree;

	public mDataAccessMemory(mVariables mVariables, Character type) {
		super(mVariables);
		tree = mVariables.getVariables(type);
	}

	public Object get(Object... subs) {
		return tree.get(subs);
	}

	public void set(Object value) {
		tree.set(currentSubs, value);
	}

	public void merge(Object[] dest, Object[] orig) {
		tree.merge(dest, orig);
	}

	public void stacking(Object... variables) {
		tree.stacking(variables);
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
		tree.kill(subs);
	}

	public int data(Object... subs) {
		return tree.data(subs);
	}

	public Object order(Object[] subs, int direction) {
		return tree.order(subs, direction);
	}

	public Object order(Object... subs) {
		return order(subs, 1);
	}

	public mDataAccessMemory subs(Object... subs) {
		currentSubs = subs;
		return this;
	}
}
