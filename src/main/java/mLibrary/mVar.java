package mLibrary;

public class mVar {
	private Object[] subs;
	private mData mData;

	public mVar() {
	}

	public mVar(Object[] subs, mLibrary.mData mData) {
		this.subs = subs;
		this.mData = mData;
	}

	public void set(Object value) {
		mData.subs(subs).set(value);
	}

	public Object get() {
		throw new UnsupportedOperationException();
	}

	public void kill() {
		mData.kill(subs);
	}

	public int data() {
		return data(1);
	}

	public int data(int direction) {
		return mData.data(subs);
	}
}
