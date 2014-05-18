package mLibrary;

import br.com.innovatium.mumps2java.datastructure.Tree;

public abstract class mDataAccess {

	boolean subsChanged;
	boolean firstExecutionOrder = true;
	Object[] currentSubs;
	final Tree tree;
	final mVariables mVariables;
	
	public mDataAccess(mVariables mVariables, int type) {
		this.mVariables = mVariables;
		this.tree = mVariables.getVariables(type);
	}

	Object[] getCurrentSubs() {
		return currentSubs;
	}

	public abstract Object get(Object... subs);

	public abstract void set(Object value);


	public abstract void stacking(Object... variables);

	public abstract void stackingExcept(Object... variables);

	public abstract int data(Object... subs);

	public abstract Object order(Object[] subs, int direction);

	public abstract Object order(Object... subs);

	final mDataAccess subs(Object... subs) {
		currentSubs = subs;
		return this;
	}

	public abstract void unstacking();

	public abstract void kill(Object[] subs);

	public abstract boolean isEmpty();
	
	public final void merge(Object[] dest, Object[] orig){
		mVariables.merge(dest, orig);
	}

}
