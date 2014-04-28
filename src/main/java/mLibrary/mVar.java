package mLibrary;

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

	public Object[] getSubs() {
		return subs;
	}

	public mData getmData() {
		return mData;
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
		return new mVar(concat(subs), mData);
	}

	/*
	 * This method should remove toString call from subscripts array element.
	 */
	@TODO
	public String getName() {
		return subs[0].toString();
	}

	private Object[] concat(Object[] subs) {
		Object[] copy = new Object[this.subs.length + subs.length];
		for (int i = 0; i < this.subs.length; i++) {
			copy[i] = this.subs[i];
		}

		for (int i = 0; i < subs.length; i++) {
			copy[i + this.subs.length] = subs[i];
		}
		return copy;
	}
}
