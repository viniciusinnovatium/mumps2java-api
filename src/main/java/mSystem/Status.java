package mSystem;

import mLibrary.mContext;
import mLibrary.mParent;
import mLibrary.mVar;

public class Status extends mParent{	

	public Status(mContext m$) {
		super(m$);
		// TODO Auto-generated constructor stub
	}

	public static void DecomposeStatus(Object object, mVar arrErr) {
		// TODO REVISAR IMPLEMENTAÇÃO
		arrErr.set("Status Decompose of "+object);
	}

	public Object Error(int i, Object object, String string, String string2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	public Object Error(Object string) {
		// TODO REVISAR IMPLEMENTAÇÃO
		return "Error message of "+string;
	}

	public Object IsOK(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
