package mLibrary;

import java.util.ArrayList;

public class mList<T> extends mClass{
	ArrayList<T> list = new ArrayList<T>();
	Object modified = null;
	public Object GetAt(Object v){
		return list.get(mFncUtil.integerConverter(v));
	}
	public Object Count(){
		return list.size();
	}
	public void $SetModified(Object sts){
		modified = sts;
	}
}
