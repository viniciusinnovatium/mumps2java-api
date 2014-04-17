package mLibrary;

public class mContext {
	private mData mData;
	private String[] newVarName;

	public mContext() {
	}

	public mContext(mLibrary.mData mData) {
		this.mData = mData;
	}

	public mVar indirect(Object string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar indirectVar(Object val) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar lastvar(int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar lastVar(Object... subs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void newcontext() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar newref(Object object, String string, Object $$$no) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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

	public mVar newVarRef(String name, Object variable) {
		if (variable instanceof mVar) {
			return (mVar) variable;
		} else {
			mVar var = var(name);
			if (variable != null) {
				var.set(variable);
			}
			return var;
		}
	}

	public mVar newVarRef(String name, Object variable, Object valueDefault) {
		if (variable instanceof mVar) {
			return (mVar) variable;
		} else {
			mVar var = var(name);
			if (variable != null) {
				var.set(variable);
			} else {
				var.set(valueDefault);
			}
			return var;
		}
	}

	public void oldvar(int totalLevel) {
		while (totalLevel-- > 0) {
			mData.unstacking();
		}
	}

	public mVar pieceVar(mVar var, Object del, Object ipos) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar pieceVar(mVar var, Object del, Object ipos, Object epos) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar var(Object... subs) {
		return new mVar(subs, this.mData);
	}

	public mVar varRef(String name, Object ref) {
		throw new UnsupportedOperationException();
	}
}
