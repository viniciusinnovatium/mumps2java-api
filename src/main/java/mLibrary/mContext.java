package mLibrary;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import mSystem.mSystem;
import br.com.innovatium.mumps2java.todo.REMOVE;
import br.com.innovatium.mumps2java.todo.TODO;

public class mContext {
	private mData mDataPublic;
	private mData mDataGlobal;
	private mData mDataLocal;
	private Object[] newVarName;
	private int countNewOperator;
	private Map<String, Method> methodMap;

	private mRequest mReq;
	private mSession mSes;
	public mFnc Fnc;
	public mCmd Cmd;
	private mSystem system;

	public mContext() {
		this.mDataPublic = new mData();
		this.mDataGlobal = new mData();
		this.mDataLocal = new mData();
		this.Fnc = new mFnc(this);
		this.Cmd = new mCmd(this);
		this.system = new mSystem(this);
	}

	public mSystem getSystem() {
		// TODO Auto-generated method stub
		return system;
	}

	public String dump() {
		return mDataPublic.dump();
	}

	public Object dispatch(mClass objClass, String methodName,
			Object... parameters) {
		Method m = getMethod(methodName);
		Object result = null;
		Object obj = null;
		try {
			if (objClass != null) {
				obj = objClass;
			} else if (!Modifier.isStatic(m.getModifiers())) {
				obj = m.getDeclaringClass().newInstance();
				if (obj instanceof mClass) {
					((mClass) obj).setContext(this);
				}
			}
			if (m.getParameterTypes() != null
					&& m.getParameterTypes().length > 0
					&& m.getParameterTypes()[0].isArray()) {
				parameters = new Object[] { parameters };
			}
			if (m.getReturnType().equals(Void.TYPE)) {
				m.invoke(obj, parameters);
			} else {
				result = m.invoke(obj, parameters);
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
				// Method[] methods = Macros.class.getMethods();
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

		if (m == null) {
			throw new IllegalArgumentException("The method " + methodName
					+ " does not exist");
		}
		return m;
	}

	public Object fnc$(Object... args) {
		Object[] parameters = null;
		String methodName = "";
		mClass objClassArg = null;
		if (args != null && args.length > 0) {
			int initParam = 1;
			if (args[0] instanceof String) {
				methodName = (String) args[0];
			} else if (args[0] instanceof mClass) {
				objClassArg = (mClass) args[0];
				methodName = (String) args[1];
				initParam = 2;
			}
			if (args.length > 1) {
				parameters = Arrays.copyOfRange(args, initParam, args.length);
			}
		}

		methodName = defineMethodName(objClassArg, methodName);
		return dispatch(objClassArg, methodName, parameters);
	}

	/*
	 * Estudar uma estrategia para executar o metodo quando nao temos declarado
	 * o nome da classe a qual ele pertence, por exmplo: 1) Com nome definido:
	 * WWWConsys.main 2) Sem nome definido: calcular. Nesse caso estamos supondo
	 * que esse metodo pertence ao ultimo mClass em execucao na pilha.
	 */
	@TODO
	public String defineMethodName(mClass objClass, String methodName) {
		if (methodName != null && !methodName.contains(".")) {
			if (objClass != null) {
				methodName = objClass.getClass().getName().concat(".")
						.concat(methodName);
			} else {
				Throwable thr = new Throwable();
				thr.fillInStackTrace();
				StackTraceElement[] ste = thr.getStackTrace();
				String className = null;
				for (int i = 0; i < ste.length; i++) {
					className = ste[i].getClassName();
					try {
						if (mClass.class.isAssignableFrom(Class
								.forName(className))) {
							methodName = className.concat(".").concat(
									methodName);
							break;
						}
					} catch (ClassNotFoundException e) {
						throw new IllegalArgumentException(
								"Can not execute the method "
										+ methodName
										+ " because there is no one classe implementing it.",
								e);
					}
				}

			}
		}
		return methodName;
	}

	public void populateParameter(Map<String, String[]> map) {
		Set<Entry<String, String[]>> results = map.entrySet();
		for (Entry<String, String[]> result : results) {
			mDataPublic.subs("%request.Data", result.getKey()).set(
					result.getValue()[0]);
		}
	}

	public mSession getSession() {
		return mSes;
	}

	public mRequest getRequest() {
		return mReq;
	}

	public void setSession(mSession mSes) {
		this.mSes = mSes;
	}

	public void setRequest(mRequest mReq) {
		this.mReq = mReq;
	}

	public mVar indirectVar(Object val) {
		return var(parseVarSubs(val.toString()));
	}

	@REMOVE
	public mVar lastvar(int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar lastVar(Object... subs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void merge(mVar dest, mVar orig) {
		mDataPublic.merge(dest.getSubs(), orig.getSubs());
	}

	@REMOVE
	public void newcontext() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@REMOVE
	public mVar newref(Object object, String string, Object $$$no) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void newVar(mVar... vars) {
		newVarName = new String[vars.length];
		for (int i = 0; i < vars.length; i++) {
			newVarName[i] = vars[i].getName();
		}
		mDataPublic.stacking(newVarName);
		newVarName = null;
		countNewOperator++;
	}

	public void newVarExcept(mVar... vars) {
		newVarName = new String[vars.length];
		for (int i = 0; i < vars.length; i++) {
			newVarName[i] = vars[i].getName();
		}
		mDataPublic.stackingExcept(newVarName);
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
			mDataPublic.unstacking();
		}
	}

	@REMOVE
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
		final boolean isEmpty = subs.length >= 1 && "".equals(subs[0]);
		if(isEmpty){
			return new mVar(subs, mDataLocal);
		}
		
		final String varName = mFncUtil.castString(subs[0]);
		final char type = varName.charAt(0);
		if (type == '%') {
			return new mVar(subs, mDataPublic);
		} else if (type == '^') {
			return new mVar(subs, mDataGlobal);
		} else {
			return new mVar(subs, mDataLocal);
		}
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

	public mVar prop(Object object, String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	private Object[] parseVarSubs(String _content) {
		final List<Object> _result = new ArrayList<Object>();
		int _level = 0, y = 0;
		boolean _isstring = false;
		for (int x = 0; x < _content.length(); x++) {
			if (_content.charAt(x) == '"') {
				_isstring = (_isstring) ? false : true;
			}

			if (_isstring) {
				continue;
			}

			if (_content.charAt(x) == '(') {
				if (_level == 0) {
					_result.add(_content.substring(y, x));
					y = x + 1;
				}
				_level++;
			}

			else if (_content.charAt(x) == ')') {
				_level--;
				if (_level == 0) {
					_result.add(parseVarValue(_content.substring(y, x)));
					y = x + 1;
				}
			} else if (_content.charAt(x) == ',') {
				if (_level == 1) {
					_result.add(parseVarValue(_content.substring(y, x)));
					y = x + 1;
				}
			}
		}
		return _result.toArray();
	}

	private Object parseVarValue(String _content) {
		Object _result;
		System.out.println(_content);
		if (_content == null) {
			_result = "";
		} else if (_content.length() == 0) {
			_result = _content;
		} else if (_content.charAt(0) == '"') {
			_result = _content.replaceAll("\"(.*)\"", "$1").replaceAll("\"\"",
					"\"");
		} else if (_content.matches("[\\+\\-]?[\\d\\.]+(.*)")) {
			_result = _content.replaceAll("([\\+\\-]?[\\d\\.]+)(.*)", "$1");
		} else {
			_result = var(parseVarSubs(_content)).get();
		}
		return _result;
	}
}
