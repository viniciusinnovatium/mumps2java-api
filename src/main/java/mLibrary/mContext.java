package mLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletResponse;

import mSystem.mSystem;
import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;
import br.com.innovatium.mumps2java.todo.REMOVE;
import br.com.innovatium.mumps2java.todo.TODO;

public class mContext {
	// TODO remover
	public int xecuteCount;
	private mDataAccess mDataPublic;
	private mDataAccess mDataGlobal;
	private mDataAccess mDataLocal;

	private int countNewOperator;
	private Map<String, Method> methodMap;

	private mRequest mReq;
	private mSession mSes;
	public mFnc Fnc;
	public mCmd Cmd;
	private mSystem system;
	private Writer writer;
	private BufferedReader reader;
	private Map<String, Object> ioMap = new TreeMap<String, Object>();
	private String ioDefault = "response";
	private String ioActual;
	private final mVariables mVariables;
	private Map<String, Class> stackedClasses = new HashMap<String, Class>(30);

	public mContext(Object io) {
		this(true, io);
	}

	public mContext() {
		this(false, null);
	}

	public mContext(boolean hasDatabaseAcces, Object io) {
		this.mVariables = new mVariables();
		this.mDataPublic = new mDataAccessPublic(mVariables);
		this.mDataLocal = new mDataAccessLocal(mVariables);
		if (hasDatabaseAcces) {
			this.mDataGlobal = new mDataGlobalAccess(mVariables);
		} else {
			this.mDataGlobal = new mDataAccessMemory(mVariables,
					DataStructureUtil.GLOBAL);
		}

		this.Fnc = new mFnc(this);
		this.Cmd = new mCmd(this);
		this.system = new mSystem(this);

		ioMap.put(this.ioDefault, io);
		useIO(this.ioDefault);
	}

	public Writer getWriter() {
		return writer;
	}

	public BufferedReader getReader() {
		return reader;
	}

	public Object getIO() {
		return this.ioActual;
	}

	public void putIO(String deviceName, Object io) {
		ioMap.put(deviceName, io);
	}

	public void removeIO(String deviceName) {
		ioMap.remove(deviceName);
		useIO(this.ioDefault);
	}

	public void useIO(String deviceName) {
		Object io = ioMap.get(deviceName);
		try {
			if (io instanceof HttpServletResponse) {
				HttpServletResponse res = (HttpServletResponse) io;
				this.writer = res.getWriter();
				this.reader = null;
			} else if (io instanceof File) {
				File res = (File) io;
				this.writer = new BufferedWriter(new FileWriter(res, true));
				this.reader = new BufferedReader(new FileReader(res));
			}
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"The writer strategy must not be empty.");
		}
		this.ioActual = deviceName;
	}

	public mDataAccess getmDataPublic() {
		return mDataPublic;
	}

	public boolean hasPublicVariables() {
		return !mDataPublic.isEmpty();
	}

	public mDataAccess getmDataGlobal() {
		return mDataGlobal;
	}

	public mDataAccess getmDataLocal() {
		return mDataLocal;
	}

	public mSystem getSystem() {
		return system;
	}

	public Object dispatch(boolean isJobExec, mClass objClass,
			String methodName, Object... parameters) {
		Method m = getMethod(methodName);
		Object result = null;
		Object obj = null;
		int countOld = countNewOperator;
		countNewOperator = 0;
		try {
			if (objClass != null) {
				obj = objClass;
			} else if (!Modifier.isStatic(m.getModifiers())) {
				obj = Class.forName(
						methodName.substring(0, methodName.lastIndexOf(".")))
						.newInstance();

				if (obj instanceof mClass) {
					// This was done because in the job threads we have sharing
					// memory mContext, so, to get isolation we must create a
					// new context and into this the thread can create variables
					// in such way that will not conflict with mContext of the
					// request.
					if (isJobExec) {
						((mClass) obj).setContext(new mContext());
					} else {
						((mClass) obj).setContext(this);
					}

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

		} catch (Throwable e) {
			throw new IllegalStateException("Fail to execute method: "
					+ methodName + " and its parameters: "
					+ Arrays.deepToString(parameters), e);
		}

		oldvar();
		countNewOperator = countOld;
		return result;
	}

	public Object dispatch(mClass objClass, String methodName,
			Object... parameters) {
		return dispatch(false, objClass, methodName, parameters);
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
				Class clazz = null;
				for (int i = 0; i < ste.length; i++) {
					className = ste[i].getClassName();
					clazz = stackedClasses.get(className);
					if (clazz == null) {
						try {
							clazz = Class.forName(className);
						} catch (ClassNotFoundException e) {
							throw new IllegalArgumentException(
									"Can not execute the method "
											+ methodName
											+ " because there is no one classe implementing it.",
									e);
						}
						stackedClasses.put(className, clazz);
					}
					if (mClass.class.isAssignableFrom(clazz)) {
						methodName = className.concat(".").concat(methodName);
						break;
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

	public mVar lastVar(Object... subs) {
		Object[] concat = mFncUtil.concatSinceLastSubscript(
				mDataGlobal.getCurrentSubs(), subs);
		return var(concat);
	}

	@TODO
	public void merge(mVar dest, mVar orig) {
		Object obj = String.valueOf("");
		for (;;) {
			ArrayList<Object> subL = new ArrayList<Object>(Arrays.asList(orig
					.getSubs()));
			subL.add(obj);

			obj = mFnc.$order(var(subL.toArray()));
			if (String.valueOf(obj).isEmpty()) {
				break;
			}
			ArrayList<Object> subDest = new ArrayList<Object>(
					Arrays.asList(dest.getSubs()));
			subDest.add(obj);

			ArrayList<Object> subOrig = new ArrayList<Object>(
					Arrays.asList(orig.getSubs()));
			subOrig.add(obj);
			merge(var(subDest.toArray()), var(subOrig.toArray()));
		}
		Object valOrig = orig.get();
		if (valOrig != null) {
			dest.set(valOrig);
		}		
		//dest.merge(orig);
	}

	@TODO
	public void newVar(mVar... vars) {
		Map<mDataAccess, Object[]> maps = filteringVariableTypes(vars);
		Set<Entry<mDataAccess, Object[]>> set = maps.entrySet();
		for (Entry<mDataAccess, Object[]> entry : set) {
			entry.getKey().stacking(entry.getValue());
		}

		countNewOperator++;
	}

	@TODO
	public void newVarExcept(mVar... vars) {
		Map<mDataAccess, Object[]> maps = filteringVariableTypes(vars);
		Set<Entry<mDataAccess, Object[]>> set = maps.entrySet();
		for (Entry<mDataAccess, Object[]> entry : set) {
			entry.getKey().stackingExcept(entry.getValue());
		}
		countNewOperator++;
	}

	public void newVarBlock(int indexBlock, mVar... vars) {
		Map<mDataAccess, Object[]> maps = filteringVariableTypes(vars);
		Set<Entry<mDataAccess, Object[]>> set = maps.entrySet();
		for (Entry<mDataAccess, Object[]> entry : set) {
			entry.getKey().stackingBlock(indexBlock, entry.getValue());
		}
	}

	public void newVarExceptBlock(int indexBlock, mVar... vars) {
		Map<mDataAccess, Object[]> maps = filteringVariableTypes(vars);
		Set<Entry<mDataAccess, Object[]>> set = maps.entrySet();
		for (Entry<mDataAccess, Object[]> entry : set) {
			entry.getKey().stackingExceptBlock(indexBlock, entry.getValue());
		}
	}

	public void restoreVarBlock(int indexBlock) {
		mDataLocal.unstackingBlock(indexBlock);
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
			mDataLocal.unstacking();
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
		if (isEmpty) {
			return new mVar(subs, mDataLocal);
		}

		final String varName = mFncUtil.castString(subs[0]);
		if (Character.isDigit(varName.charAt(0))) {
			return null;
		}

		return new mVar(subs, getMDataAccess(subs));
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
		int _level = 0, y = 0, x = 0;
		boolean _isstring = false;
		for (x = 0; x < _content.length(); x++) {
			if (_content.charAt(x) == '"') {
				_isstring = (_isstring) ? false : true;
			}

			if (_isstring) {
				continue;
			}

			if (_content.charAt(x) == '(') {
				if (_level == 0 && x > y) {
					_result.add(_content.substring(y, x));
					y = x + 1;
				}
				_level++;
			}

			else if (_content.charAt(x) == ')') {
				_level--;
				if (_level == 0) {
					if(x > y){
						_result.add(parseVarValue(_content.substring(y, x)));
					}
					y = x + 1;
				}
			}

			else if (_content.charAt(x) == ',') {
				if (_level == 1) {
					if(x > y){
						_result.add(parseVarValue(_content.substring(y, x)));
					}
					y = x + 1;
				}
			}
		}
		if (x > y) {
			_result.add(_content.substring(y, x));
		}
		return _result.toArray();
	}

	private Object parseVarValue(String _content) {
		Object _result;
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
		return ((_result == null) ? "" : _result);
	}

	private mDataAccess getMDataAccess(Object[] subs) {
		if (DataStructureUtil.isPublic(subs)) {
			return mDataPublic;
		} else if (DataStructureUtil.isGlobal(subs)) {
			return mDataGlobal;
		} else {
			return mDataLocal;
		}
	}

	private Map<mDataAccess, Object[]> filteringVariableTypes(mVar... variables) {
		Map<mDataAccess, Object[]> map = new HashMap<mDataAccess, Object[]>();
		List<String> locals = new ArrayList<String>();
		List<String> publics = new ArrayList<String>();
		List<String> globals = new ArrayList<String>();

		String name = null;
		for (mVar var : variables) {
			name = var.getSubs()[0] == null ? null : var.getSubs()[0]
					.toString();
			if (DataStructureUtil.isPublic(name)) {
				publics.add(name);
			} else if (DataStructureUtil.isGlobal(name)) {
				globals.add(name);
			} else {
				locals.add(name);
			}
		}

		map.put(this.mDataLocal, locals.toArray());
		return map;
	}

	public mParameter param(mClass instanceMClas, String parameterName) {
		mParameter parameter;
		try {
			parameter = new mParameter(instanceMClas.getClass().getField(
					parameterName));
		} catch (NoSuchFieldException e) {
			parameter = null;
		} catch (SecurityException e) {
			parameter = null;
		}
		return parameter;
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) this.ioMap.get("response");
	}
}
