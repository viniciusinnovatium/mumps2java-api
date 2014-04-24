package mLibrary;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class mContext {
	private mData mData;
	private String[] newVarName;

	public mContext() {
	}

	public mContext(mLibrary.mData mData) {
		this.mData = mData;
	}

	public void populateParameter(Map<String, String[]> map){
		Set<Entry<String, String[]>> results = map.entrySet();
		for (Entry<String, String[]> result : results) {
			mData.subs("%request.Data", result.getKey()).set(result.getValue()[0]);
		}
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

	/**
	 * This method was created to play a role of mumps usage variable through
	 * reference or value scheme
	 * 
	 * @param name
	 * @param variable
	 *            variable used to be employed through reference or value
	 * @return
	 */
	public mVar newVarRef(String name, Object variable) {
		return newVarRef(name, variable, null);
	}

	/**
	 * This method was created to play a role of mumps usage variable through
	 * reference or value scheme
	 * 
	 * @param name
	 * @param variable
	 *            variable used to be employed through reference or value
	 * @param valueDefault default value to be attributed
	 * @return
	 */
	public mVar newVarRef(String name, Object variable, Object valueDefault) {
		return simulatingVariableThroughReference(name, variable, valueDefault, true);
	}

	public void oldvar(int totalLevel) {
		if (totalLevel <= 0) {
			return;
		}
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
		return varRef(name, ref, null);
	}
	
	public mVar varRef(String name, Object ref, Object valueDefault) {
		return simulatingVariableThroughReference(name, ref, valueDefault, false);
	}
	
		/**
	 * This method was created to play a role of mumps usage variable through
	 * reference or value scheme
	 * @param name
	 * @param variable
	 *            variable used to be employed through reference or value
	 * @param valueDefault default value to be attributed
	 * @param stackingNeeded parameter indicating stacking variable condition
	 * @return
	 */
	private mVar simulatingVariableThroughReference(String name, Object variable, Object valueDefault, boolean stackingNeeded) {
		if (variable instanceof mVar) {
			return (mVar) variable;
		} else {
			mVar var = var(name);
			
			if (stackingNeeded) {
				newVar(var);				
			}
			
			if (variable != null) {
				var.set(variable);
			} else if(valueDefault != null) {
				var.set(valueDefault);
			}
			return var;
		}
	}
}
