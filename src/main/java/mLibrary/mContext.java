package mLibrary;

public class mContext {
	private mData mData;
	private String[] newVarName;

	public mContext() {
	}

	public mContext(mLibrary.mData mData) {
		this.mData = mData;
	}

	public mVar var(Object... subs) {
		return new mVar(subs, this.mData);
	}

	public void newVar(mVar... vars) {
		newVarName = new String[vars.length];
		for (int i = 0; i < vars.length; i++) {
			newVarName[i] = vars[i].getName();
		}
		mData.stacking(newVarName);
		newVarName = null;
	}
	
	public void newVarExcept(mVar... vars) {
		newVarName = new String[vars.length];
		for (int i = 0; i < vars.length; i++) {
			newVarName[i] = vars[i].getName();
		}
		mData.stackingExcept(newVarName);
		newVarName = null;
	}

	public mVar piece(String $extract, String string, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar piece(mVar p$1, String string, int i, Object subtract) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar piece(mVar p$1, String string, Object subtract) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar piece(Object $zdate, String string, int i, int subtract) {
		// TODO Auto-generated method stub
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

	public mVar pieceVar(mVar var, Object del, Object ipos) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar pieceVar(mVar var, Object del, Object ipos, Object epos) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar indirectVar(Object val) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar lastVar(Object... subs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void oldvar(int totalLevel) {
		while (totalLevel-- > 0) {
			mData.unstacking();
		}
	}
}
