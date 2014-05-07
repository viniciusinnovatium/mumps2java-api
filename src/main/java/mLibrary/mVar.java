package mLibrary;

import java.util.Arrays;

import br.com.innovatium.mumps2java.todo.TODO;

public class mVar {
	/*
	 * The subscripts should be converted to String soon.
	 */
	@TODO
	private Object[] subs;
	private mData mData;

	public mVar() {
	}

	public mVar(Object[] subs, mLibrary.mData mData) {
		this.subs = subs;
		this.mData = mData;
	}

	public Object order(int direction) {
		mData.subs(subs);
		return mData.order(direction);
	}

	public Object order() {
		return order(1);
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

	public mVar var(Object... subs) {
		return new mVar(mFncUtil.concat(this.subs, subs), mData);
	}

	public String getName() {
		return subs[0].toString();
	}

	public Object[] getParameters() {
		return Arrays.copyOfRange(subs, 1, subs.length);
	}
	
	public Object[] getSubs() {
		return subs;
	}

	public mData getmData() {
		return mData;
	}

}
