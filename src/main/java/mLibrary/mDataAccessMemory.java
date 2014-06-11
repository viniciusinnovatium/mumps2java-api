package mLibrary;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;

class mDataAccessMemory extends mDataAccess {

	public mDataAccessMemory(mVariables mVariables, int type) {
		super(mVariables, type);
	}
	
	public mDataAccessMemory() {
		super(new mVariables(), DataStructureUtil.LOCAL);
	}

	public Object get(Object... subs) {
		return tree.get(subs);
	}

	public void stacking(Object... variables) {
		tree.stacking(variables);
	}

	public void stackingExcept(Object... variables) {
		tree.stackingExcept(variables);
	}

	public void stackingBlock(int indexBlock, Object... variables) {
		tree.stackingBlock(indexBlock, variables);
	}

	public void stackingExceptBlock(int indexBlock, Object... variables) {
		tree.stackingExceptBlock(indexBlock, variables);
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
