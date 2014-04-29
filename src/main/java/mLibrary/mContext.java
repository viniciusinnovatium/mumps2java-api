package mLibrary;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class mContext {
	private mData mData;
	private String[] newVarName;
	private int countNewOperator;
	private Map<String, Method> methodMap;

	private mRequest mReq;
	private mSession mSes;
	
	public mContext() {
	}

	public mContext(mLibrary.mData mData) {
		this.mData = mData;
	}

	public Object dispatch(String methodName, Object... parameters) {
		Method m = getMethod(methodName);
		Object result = null;

		try {
			if (m.getReturnType().equals(Void.TYPE)) {
				m.invoke(null, parameters);
			} else {
				result = m.invoke(null, parameters);	
			}
			
		} catch (Exception e) {
			throw new IllegalStateException("Fail to execute method: "
					+ methodName + " and its parameters: "
					+ Arrays.deepToString(parameters), e);
		}

		oldvar();
		return result;
	}

	private Method getMethod(String methodName) {
		if (methodMap == null) {
			methodMap = new HashMap<String, Method>(20);
		}

		Method m = methodMap.get(methodName);		
		if (m == null) {
			int lastIndex = methodName.lastIndexOf(".");
			final String clazz = methodName.substring(0, lastIndex);
			final String method = methodName.substring(lastIndex + 1);

			try {
				Method[] methods = Class.forName(clazz).getMethods();
				//Method[] methods = Macros.class.getMethods();
				for (Method met : methods) {
					if (method.equals(met.getName())) {
						m = met;
						break;
					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Fail to find method name: "
						+ methodName, e);
			}
			methodMap.put(methodName, m);
		}
		return m;
	}

	public void populateParameter(Map<String, String[]> map) {
		Set<Entry<String, String[]>> results = map.entrySet();
		for (Entry<String, String[]> result : results) {
			mData.subs("%request.Data", result.getKey()).set(
					result.getValue()[0]);
		}
	}

	
	public mSession getSession(){
		return mSes;
	}
	
	public mRequest getRequest(){
		return mReq;
	}
	
	public void setSession(mSession mSes){
		this.mSes = mSes;
	}
	
	public void setRequest(mRequest mReq){
		this.mReq = mReq;
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
		countNewOperator++;
	}

	public void newVarExcept(mVar... vars) {
		newVarName = new String[vars.length];
		for (int i = 0; i < vars.length; i++) {
			newVarName[i] = vars[i].getName();
		}
		mData.stackingExcept(newVarName);
		newVarName = null;
		countNewOperator++;
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
	 * @param valueDefault
	 *            default value to be attributed
	 * @return
	 */
	public mVar newVarRef(String name, Object variable, Object valueDefault) {
		return simulatingVariableThroughReference(name, variable, valueDefault,
				true);
	}

	public void oldvar(int totalLevel) {
		if (totalLevel <= 0) {
			return;
		}
		while (totalLevel-- > 0) {
			mData.unstacking();
		}
	}

	public void oldvar() {
		oldvar(countNewOperator);
	}

	public mVar pieceVar(mVar var, Object del) {
		return new mPieceVar(var, del, 1);
	}
	
	public mVar pieceVar(mVar var, Object del, Object ipos) {
		return new mPieceVar(var, del, ipos);
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
		return simulatingVariableThroughReference(name, ref, valueDefault,
				false);
	}

	/**
	 * This method was created to play a role of mumps usage variable through
	 * reference or value scheme
	 * 
	 * @param name
	 * @param variable
	 *            variable used to be employed through reference or value
	 * @param valueDefault
	 *            default value to be attributed
	 * @param stackingNeeded
	 *            parameter indicating stacking variable condition
	 * @return
	 */
	private mVar simulatingVariableThroughReference(String name,
			Object variable, Object valueDefault, boolean stackingNeeded) {
		if (variable instanceof mVar) {
			return (mVar) variable;
		} else {
			mVar var = var(name);

			if (stackingNeeded) {
				newVar(var);
			}

			if (variable != null) {
				var.set(variable);
			} else if (valueDefault != null) {
				var.set(valueDefault);
			}
			return var;
		}
	}
}
