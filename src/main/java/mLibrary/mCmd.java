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

		if(cmdStr.equals("do AfterDataFields^COMViewFilter(\"MEDPatient,,\",1)")){
			Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);
		}else if(cmdStr.equals("do ##class(SourceControl.Exporter).TagNMArtifactByNameKey(YDATEI,YKEY)")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get()
		}else if(cmdStr.equals("set strResult=##class(alSOH.iStockHistory).ItemHasTransactions(YKEY)")){
			//set strResult=##class(alSOH.iStockHistory).ItemHasTransactions(YKEY) TODO REVISAR IMPLEMENTAÇÃO
		}else if(cmdStr.equals("W:$P($G(YFELD),Y,40)=6 \"²\"  W:$P($G(YFELD),Y,40)=12 \"³\" ")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get() TODO REVISAR IMPLEMENTAÇÃO
		}else if(cmdStr.equals(";I YINHALT=\"\" S YINHALT=\"nopicture.gif\"  ;Customizing !")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get() TODO REVISAR IMPLEMENTAÇÃO
		}else if(cmdStr.equals("SET %TXT(1)=$$BeforeSave^WWWFORMValidation(YINHALT,YVAR)")){		
			m$.var("%TXT",1).set(m$.fnc$("WWWFORMValidation.BeforeSave",m$.var("YINHALT").get(),m$.var("YVAR").get()));
		}else if(cmdStr.equals("SET %TXT(1)=$$CallBack^COMViewUtils(YINHALT,YVAR)")){
			m$.var("%TXT",1).set(m$.fnc$("COMViewUtils.CallBack",m$.var("YINHALT").get(),m$.var("YVAR").get()));		
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess^WWW101(YFORM,YKEY,YFELD)")){
			m$.var("strStatus").set(m$.fnc$("WWW101.OnBeforeDataAccess",m$.var("YFORM").get(),m$.var("YKEY").get(),m$.var("YFELD").get()));
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess1^VARWWW101(YFORM,YKEY,YFELD)")){
			m$.var("strStatus").set(m$.fnc$("VARWWW101.OnBeforeDataAccess1",m$.var("YFORM").get(),m$.var("YKEY").get(),m$.var("YFELD").get()));
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess^VARMEDPrescription(.YKEY,YFELD,YFORM)")){
			m$.var("strStatus").set(m$.fnc$("VARMEDPrescription.OnBeforeDataAccess",m$.var("YKEY"),m$.var("YFELD").get(),m$.var("YFORM").get()));
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess^WWW100()")){
			m$.var("strStatus").set(m$.fnc$("WWW100.OnBeforeDataAccess"));
		}else if(cmdStr.equals("set strStatus=$$IsUsable^INLIEF(YKEY,YFORM)")){
			m$.var("strStatus").set(m$.fnc$("INLIEF.IsUsable",m$.var("YKEY").get(),m$.var("YFORM").get()));
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess^INReqSourceSupplier(YKEY,YUSER)")){
			m$.var("strStatus").set(m$.fnc$("INReqSourceSupplier.OnBeforeDataAccess",m$.var("YKEY").get(),m$.var("YUSER").get()));
		}else if(cmdStr.equals("set strResult=$$HideCustoms^INVORG()")){
			m$.var("strResult").set(m$.fnc$("INVORG.HideCustoms"));
		}else if(cmdStr.equals("set strResult='$$ValidItem^INART(YKEY)")){
			m$.var("strResult").set(m$.fnc$("INART.ValidItem",m$.var("YKEY").get()));
		}else if(mFncUtil.isMatcher(cmdStr, "set objStoredData=$get(",")")){
			String var = mFncUtil.matcher(cmdStr, "set objStoredData=$get(",")");
			m$.var("objStoredData").set(m$.Fnc.$get(m$.indirectVar(var)));
		}else if(mFncUtil.isMatcher(cmdStr, "set strCode=$$GetRemovalCode^MEDPatient(\"MEDPatient\",",")")){
			String var = mFncUtil.matcher(cmdStr, "set strCode=$$GetRemovalCode^MEDPatient(\"MEDPatient\",",")");
			m$.var("strCode").set(m$.fnc$("MEDPatient.GetRemovalCode","MEDPatient",var));
		}else if(mFncUtil.isMatcher(cmdStr, "SET YOPTION=")){
			String var = mFncUtil.matcher(cmdStr, "SET YOPTION=");
			m$.var("YOPTION").set(var);
		}else if(mFncUtil.isMatcher(cmdStr, "s YQ=",  "  //SR16842")){
			String var = mFncUtil.matcher(cmdStr, "s YQ=",  "  //SR16842");
			m$.var("YQ").set(var);
		}else if(mFncUtil.isMatcher(cmdStr, "IF $G(YSEITE)=0 SET YSEITE=")){
			String var = mFncUtil.matcher(cmdStr, "IF $G(YSEITE)=0 SET YSEITE=");
			if(mOp.Equal(m$.Fnc.$get(m$.var("YSEITE")),0)){
				m$.var("YSEITE").set(var);
			}
		}else if(mFncUtil.isMatcher(cmdStr, "if $$IsInUse^INART(YKEY) set YHID = ")){
			String var = mFncUtil.matcher(cmdStr, "if $$IsInUse^INART(YKEY) set YHID = ");
			if(mOp.Logical(m$.fnc$("INART.IsInUse",m$.var("YKEY").get()))){
				m$.var("YHID").set(var);
			}
		}else if(cmdStr.startsWith("SET %TXT(1)=$$")){
			throw new UnsupportedOperationException();
		}else if (cmdStr.startsWith("SET ") || cmdStr.startsWith("set ")) {
			throw new UnsupportedOperationException("Xecute with command: "+cmdStr);
		} else if (cmdStr.startsWith("DO ")){
			Do(String.valueOf(command).replaceAll(Pattern.quote("DO "), ""));
		} else if (cmdStr.startsWith("D ")) {
			Do(String.valueOf(command).replaceAll(Pattern.quote("D "), ""));
		} else if (cmdStr.startsWith("do ")) {
			Do(String.valueOf(command).replaceAll(Pattern.quote("do "), ""));
		} else if (cmdStr.startsWith("d ")) {
			Do(String.valueOf(command).replaceAll(Pattern.quote("d "), ""));
		} else if (cmdStr.startsWith("U ") || cmdStr.startsWith("USER ")) {
		} else {
			throw new UnsupportedOperationException("Xecute with command: "+cmdStr);
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

	public void LockInc(mVar var) {
		// TODO REVISAR IMPLEMENTAÇÃO
	}

	public void Job(String string, Object object) {
		throw new UnsupportedOperationException();		
	}
}
