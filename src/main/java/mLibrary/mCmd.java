package mLibrary;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import br.com.innovatium.mumps2java.todo.TODO;

public class mCmd extends mParent {

	public mCmd(mContext m$) {
		super(m$);
	}

	public void Close(Object io) {
		m$.removeIO(String.valueOf(io));
	}

	public String defineMethodName(String methodName) {
		if (methodName == null) {
			return null;
		}
		final String[] method = methodName.split("\\^");
		if (method.length > 1) {
			return method[1] + "." + (method[0].isEmpty() ? "main" : method[0]);
		} else if (method.length == 1) {
			return method[0] + ".main";
		} else {
			return null;
		}
	}

	public void Do(mClass objClass, String methodName, Object... parameters) {
		DoJob(false, objClass, methodName, parameters);
	}

	public void Do(String object, String methodName, String object2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Do(Object methodName) {
		Do(mFncUtil.castString(methodName));
	}

	public void Do(String methodName) {
		DoJob(false, methodName);
	}

	public void Do(String methodName, Object... parameters) {
		DoJob(false, null, methodName, parameters);
	}

	private void DoJob(boolean isJobExec, mClass objClass, String methodName,
			Object... parameters) {
		methodName = m$.defineMethodName(objClass, methodName);
		m$.dispatch(isJobExec, objClass, methodName, parameters);
	}

	private void DoJob(boolean isJobExec, String methodName) {
		if (isIndirectionExecution(methodName)) {
			mVar var = m$.indirectVar(methodName);
			methodName = var.getName();

			if (isMethodExecution(methodName)) {
				DoJob(isJobExec, defineMethodName(methodName),
						var.getParameters());
			} else {
				DoJob(isJobExec, methodName, var.getParameters());
			}
		} else {
			if (isMethodExecution(methodName)) {
				DoJob(isJobExec, defineMethodName(methodName), (Object[]) null);
			} else {
				m$.fnc$(methodName);
			}
		}
	}

	private void DoJob(boolean isJobExec, String methodName,
			Object... parameters) {
		DoJob(isJobExec, null, methodName, parameters);
	}

	public Object Goto(Object label) {
		return m$.fnc$(label);
	}

	/*
	 * Pausa o processamento por um determinado número de milisegundos
	 */
	public void Hang(Object obj) {
		Double time = mFncUtil.numberConverter(obj);
		time = time * 1000;

		try {
			Thread.sleep(time.longValue());
		} catch (NumberFormatException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		} catch (InterruptedException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	public boolean isIndirectionExecution(String content) {
		if (content == null) {
			return false;
		}
		return content.indexOf("(") != -1;
	}

	public boolean isMethodExecution(String content) {
		if (content == null) {
			return false;
		}
		return content.indexOf("^") != -1;
	}

	public void Job(String string, String string2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Lock(String string, String string2, String string3) {
		// TODO REVISAR IMPLEMENTAÇÃO
		throw new UnsupportedOperationException();
	}

	public void LockInc(mVar var, int i) {

	}

	public void Merge(mVar target, mVar source) {
		m$.merge(target, source);
	}

	public void Open(Object $$$OprLog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Open(Object object, String string, int i) {
		m$.putIO(String.valueOf(object), new File(String.valueOf(object)));
	}

	public void Open(Object object, String string, Object concat, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * Exibe mensagem no console solicitando entrada de dados.
	 */
	public void Read(Object... parameters) {
		try {
			Object variable = null;
			Object scan = null;
			if (parameters != null && parameters.length > 0) {
				variable = parameters[0];
			}
			;
			scan = m$.getReader().readLine();
			if (variable instanceof mVar) {
				mVar var = (mVar) variable;
				var.set(scan);
			} else {

			}
		} catch (Exception e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		} finally {
		}
	}

	public void SQL() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Unlock(mVar var) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Unlock(mVar var, String str) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Unlock(String string) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Use(Object io) {
		m$.useIO(String.valueOf(io));
	}

	/*
	 * Remove toString to wirte faster
	 */
	@TODO
	public void Write(Object... string) {
		try {
			Writer writer = m$.getWriter();
			for (Object str : string) {
				try {
					if (m$.getIO() instanceof File) {
						throw new IllegalArgumentException(
								"Fail to write the string in FILE "
										+ str.toString());
					}
					String strWr = mFncUtil.toString(str);
					writer.write(strWr);
				} catch (IOException e) {
					throw new IllegalArgumentException(
							"Fail to write the string HTML " + str.toString());
				}

			}
		} catch (NullPointerException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void WriteHtml(Object... string) {
		Write(string);
	}

	public void WriteJS(Object... string) {
		for (int i = 0; i < string.length; i++) {
			string[i] = String.valueOf(string[i]).replaceAll(
					Pattern.quote("&lt;"), "<");
			string[i] = String.valueOf(string[i]).replaceAll(
					Pattern.quote("&gt;"), ">");
		}
		Write(string);
	}

	public void Xecute(Object command) {
		m$.var("^MXecute", "cmd", ++m$.xecuteCount).set(command.toString());
		String cmdStr = String.valueOf(command);
		if (cmdStr
				.equals("do AfterDataFields^COMViewFilter(\"MEDPatient,,\",1)")) {
			Do("COMViewFilter.AfterDataFields", "MEDPatient,,", 1);
		} else if (cmdStr
				.equals("do ##class(SourceControl.Exporter).TagNMArtifactByNameKey(YDATEI,YKEY)")) {
			// Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get()
		} else if (cmdStr
				.equals("SET %TXT(1)=$$BeforeSave^WWWFORMValidation(YINHALT,YVAR)")) {
			m$.var("%TXT", 1).set(
					m$.fnc$("WWWFORMValidation.BeforeSave", m$.var("YINHALT")
							.get(), m$.var("YVAR").get()));
			// m$.var("%TXT",1).set("#FUNCTION~DefaultSubmit(0)");
		} else if (cmdStr
				.equals("SET %TXT(1)=$$CallBack^COMViewUtils(YINHALT,YVAR)")) {
			m$.var("%TXT", 1).set(
					m$.fnc$("COMViewUtils.CallBack", m$.var("YINHALT").get(),
							m$.var("YVAR").get()));
			// m$.var("%TXT",1).set("#FUNCTION~DefaultSubmit(0)");
		} else if (cmdStr.startsWith("SET %TXT(1)=$$")) {
			throw new UnsupportedOperationException();
		} else if (cmdStr.startsWith("SET ") || cmdStr.startsWith("set ")) {

		} else if (cmdStr.startsWith("DO ")) {
			Do(String.valueOf(command).replaceAll(Pattern.quote("DO "), ""));
		} else if (cmdStr.startsWith("D ")) {
			Do(String.valueOf(command).replaceAll(Pattern.quote("D "), ""));
		} else if (cmdStr.startsWith("do ")) {
			Do(String.valueOf(command).replaceAll(Pattern.quote("do "), ""));
		} else if (cmdStr.startsWith("d ")) {
			Do(String.valueOf(command).replaceAll(Pattern.quote("d "), ""));
		} else if (cmdStr.startsWith("U ") || cmdStr.startsWith("USER ")) {
		} else {
			throw new UnsupportedOperationException();
		}

	}

	/*
	 * Revisar implementacao
	 */
	@TODO
	public void Lock(mVar var) {
		// TODO REVISAR IMPLEMENTAÇÃO
		throw new UnsupportedOperationException();
	}

	/*
	 * Revisar implementacao
	 */
	@TODO
	public void Lock(mVar var, int index) {
		// TODO REVISAR IMPLEMENTAÇÃO
		throw new UnsupportedOperationException();
	}

	public void Job(String methodName) {
		// m$.Cmd.Do(methodName);
		new Thread(new JobCmd(methodName)).start();
	}

	private class JobCmd implements Runnable {
		private String methodName;

		public JobCmd(final String methodName) {
			this.methodName = methodName;
		}

		public void run() {
			m$.Cmd.DoJob(true, methodName);
			// m$.Cmd.Do(methodName);
		}

	}

	public void Job(String string, Object object, Object object2, Object object3) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
