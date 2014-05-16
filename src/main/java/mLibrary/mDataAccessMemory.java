package mLibrary;


class mDataAccessMemory extends mDataAccess {

	public mDataAccessMemory(mVariables mVariables, int type) {
		super(mVariables, type);
	}

	public Object get(Object... subs) {
		return tree.get(subs);
	}

	public void set(Object value) {
		tree.set(currentSubs, value);
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

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}
}
