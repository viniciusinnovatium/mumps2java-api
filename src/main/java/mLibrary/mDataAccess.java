package mLibrary;

public abstract class mDataAccess {

	boolean subsChanged;
	boolean firstExecutionOrder = true;
	Object[] currentSubs;
	mVariables mVariables;

	public mDataAccess(){
		this(new mVariables());
	}
	
	public mDataAccess(mVariables mVariables) {
		this.mVariables = mVariables;
	}

	public abstract Object get(Object... subs);

	public abstract void set(Object value);

	public abstract void merge(Object[] dest, Object[] orig);

	public abstract void stacking(Object... variables);

	public abstract void stackingExcept(Object... variables);

	public abstract int data(Object... subs);

	public abstract Object order(Object[] subs, int direction);

	public abstract Object order(Object... subs);

	public abstract mDataAccess subs(Object... subs);

	public abstract void unstacking();

	public abstract void kill(Object[] subs);
}
