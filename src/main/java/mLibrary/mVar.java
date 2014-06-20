package mLibrary;

import java.util.Arrays;

import br.com.innovatium.mumps2java.todo.TODO;

public class mVar {
	/*
	 * The subscripts should be converted to String soon.
	 */
	@TODO
	private Object[] subs;
	private mDataAccess mData;

	public mVar(Object[] subs, mDataAccess mData) {
		this.subs = subs;
		this.mData = mData;
	}

	public void merge(mVar orig) {
		this.mData.merge(this.subs, orig.subs);
	}

	public Object order(int direction) {
		return mData.order(subs, direction);
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

	public mVar lastVar(Object... subs) {
		return new mVar(mFncUtil.concatSinceLastSubscript(this.subs, subs),
				mData);
	}

	public String getName() {
		return subs[0].toString();
	}

	public Object[] getParameters() {
		return Arrays.asList(subs).subList(1, subs.length).toArray();
	}

	public Object[] getSubs() {
		return subs;
	}

	public mDataAccess getmData() {
		return mData;
	}

	public mClass getORef() {
		Object objRef = get();
		if (objRef instanceof mClass) {
			return (mClass) objRef;
		} else {
			return null;
		}
	}

}
