package mLibrary;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.generateKey;

import java.util.Arrays;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;
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

	public Object order(int direction) {
		return mData.order(subs, direction);
	}

	public Object order() {
		return order(1);
	}

	public void set(Object value) {
		if (generateKey(subs).contains("YSATZ")) {
			System.out.print("");
		}
		mData.subs(subs).set(value);
	}

	public Object get() {
		Object val = mData.get(subs);
		if(String.valueOf(val).contains("Sexo")){
			System.out.print("");
		}
		if(generateKey(subs).contains("1.0")){
			System.out.print("");
		}		
		return val;
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
		if(!DataStructureUtil.isGlobal(this.subs)){
			throw new UnsupportedOperationException();
		}
		return new mVar(mFncUtil.concatSinceLastSubscript(this.subs, subs), mData);  
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
	
	public void merge(mVar orig) {
		this.mData.merge(this.subs, orig.subs);
	}

}
