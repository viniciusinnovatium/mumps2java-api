package mLibrary;

public class mContext {
	private mData mData;
	private String[] newVarName;

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

	public mVar oldvar(String subs) {
		mVar var = this.var(subs);
		//var.unstacking();
		return var;
	}

}
