package mLibrary;

public class mContext {
	private mData mData;

	public mContext() {
	}

	public mContext(mLibrary.mData mData) {
		this.mData = mData;
	}

	public mVar var(Object... subs) {
		return new mVar(subs, this.mData);
	}

	public void newVar(mVar... vars) {
		throw new UnsupportedOperationException();
	}

	public void newVarExcept(mVar... vars) {
		throw new UnsupportedOperationException();
	}

	public mVar varRef(String name, Object ref, Object val) {
		throw new UnsupportedOperationException();
	}

	public mVar varRef(String name, Object ref) {
		throw new UnsupportedOperationException();
	}

	public mVar newVarRef(String name, Object ref, Object val) {
		throw new UnsupportedOperationException();
	}

	public mVar newVarRef(String name, Object ref) {
		throw new UnsupportedOperationException();
	}

	public mVar piece(mVar var, Object del, Object ipos) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar piece(mVar var, Object del, Object ipos, Object epos) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar indirect(Object val) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar lastVar(Object... subs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
