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

	public mVar newref(Object obj, String name) {
		throw new UnsupportedOperationException();
	}

	public mVar incref(Object obj, String name) {
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

	public mVar piece(Object $get, Object object, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar piece(mVar p$1, Object $$$comma, Object subtract) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar newref(Object object, String string, String string2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar newref(Object object, String string, Object $$$no) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar indirect(Object string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar lastvar(int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void newcontext() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void oldvar(int totalLevel) {
		while (totalLevel-- > 0) {
			mData.unstacking();
		}
	}

}
