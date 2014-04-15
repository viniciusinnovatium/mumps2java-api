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
	
	public void stacking() {
		mData.stacking(subs);
	}
	
	public void unstacking() {
		mData.unstacking();
	}

	public void set(Object value) {
		mData.subs(subs).set(value);
	}

	public Object get() {
		return mData.get(subs);
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

	public mVar var(mVar childKey) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar var() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar var(int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar var(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public Object var(Object object, String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public Object var(Object object, String string, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
