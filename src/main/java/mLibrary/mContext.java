package mLibrary;

public class mContext {
	private mData mData;

	public mVar var(Object... subs) {
		return new mVar(subs, this.mData);
	}

	public mVar newref(Object obj, String name) {
		throw new UnsupportedOperationException();
	}

	public mVar incref(Object obj, String name) {
		throw new UnsupportedOperationException();
	}
}
