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

	/*
	public void Do(String object, String methodName, String object2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	*/
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
		}else if(cmdStr.equals("d IsFirmable^INReq(YM,YFORM,$g(YKEY),$g(YFELD))")){
			Do("INReq.IsFirmable",m$.var("YM").get(),m$.var("YFORM").get(),m$.Fnc.$get(m$.var("YKEY")),m$.Fnc.$get(m$.var("YFELD")));
		}else if(cmdStr.equals("do ##class(SourceControl.Exporter).TagNMArtifactByNameKey(YDATEI,YKEY)")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get()
		}else if(cmdStr.equals("set strResult=##class(alSOH.iStockHistory).ItemHasTransactions(YKEY)")){
			//set strResult=##class(alSOH.iStockHistory).ItemHasTransactions(YKEY) TODO REVISAR IMPLEMENTAÇÃO
		}else if(cmdStr.equals("W:$P($G(YFELD),Y,40)=6 \"²\"  W:$P($G(YFELD),Y,40)=12 \"³\" ")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get() TODO REVISAR IMPLEMENTAÇÃO
		}/*else if(cmdStr.equals(";I YINHALT=\"\" S YINHALT=\"nopicture.gif\"  ;Customizing !")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get() TODO REVISAR IMPLEMENTAÇÃO
		}else if(cmdStr.equals(";D ^INARTD72")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get() TODO REVISAR IMPLEMENTAÇÃO
		}else if(cmdStr.equals(";D ^INARTD30")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get() TODO REVISAR IMPLEMENTAÇÃO
		}else if(cmdStr.equals(";do OnAfterButtonLine^INReq(YM,YFORM,YSEITE,YKEY,YFELD)")){
			//Do("COMViewFilter.AfterDataFields","MEDPatient,,",1);m$.var("YVAR").get()
		}*/else if(cmdStr.startsWith(";")){
			//Xecute commented: No action. 
		}else if(cmdStr.equals("S YINHALT=$$GetPackUOM^INART(YKEY)")){		
			m$.var("YINHALT").set(m$.fnc$("INART.GetPackUOM",m$.var("YKEY").get()));
		}else if(cmdStr.equals("S YINHALT=$$GetHomeLocation^INReq(YBED)")){		
			m$.var("YINHALT").set(m$.fnc$("INReq.GetHomeLocation",m$.var("YBED").get()));
		}else if(cmdStr.equals("SET %TXT(1)=$$BeforeSave^WWWFORMValidation(YINHALT,YVAR)")){		
			m$.var("%TXT",1).set(m$.fnc$("WWWFORMValidation.BeforeSave",m$.var("YINHALT").get(),m$.var("YVAR").get()));
		}else if(cmdStr.equals("SET %TXT(1)=$$CallBack^COMViewUtils(YINHALT,YVAR)")){
			m$.var("%TXT",1).set(m$.fnc$("COMViewUtils.CallBack",m$.var("YINHALT").get(),m$.var("YVAR").get()));		
		}else if(cmdStr.equals("SET %TXT(1)=$$End^WWWEND(YINHALT,YVAR)")){
			m$.var("%TXT",1).set(m$.fnc$("WWWEND.End",m$.var("YINHALT").get(),m$.var("YVAR").get()));		
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess^WWW101(YFORM,YKEY,YFELD)")){
			m$.var("strStatus").set(m$.fnc$("WWW101.OnBeforeDataAccess",m$.var("YFORM").get(),m$.var("YKEY").get(),m$.var("YFELD").get()));
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess1^VARWWW101(YFORM,YKEY,YFELD)")){
			m$.var("strStatus").set(m$.fnc$("VARWWW101.OnBeforeDataAccess1",m$.var("YFORM").get(),m$.var("YKEY").get(),m$.var("YFELD").get()));
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess^VARMEDPrescription(.YKEY,YFELD,YFORM)")){
			m$.var("strStatus").set(m$.fnc$("VARMEDPrescription.OnBeforeDataAccess",m$.var("YKEY"),m$.var("YFELD").get(),m$.var("YFORM").get()));
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess^WWW100()")){
			m$.var("strStatus").set(m$.fnc$("WWW100.OnBeforeDataAccess"));
		}else if(cmdStr.equals("set strStatus=$$OnAfterSave^VARMEDPatient(YKEY,YFELD)")){
			m$.var("strStatus").set(m$.fnc$("VARMEDPatient.OnAfterSave",m$.var("YKEY").get(),m$.var("YFELD").get()));
		}else if(cmdStr.equals("set strStatus=$$IsUsable^INLIEF(YKEY,YFORM)")){
			m$.var("strStatus").set(m$.fnc$("INLIEF.IsUsable",m$.var("YKEY").get(),m$.var("YFORM").get()));
		}else if(cmdStr.equals("set strStatus=$$OnBeforeDataAccess^INReqSourceSupplier(YKEY,YUSER)")){
			m$.var("strStatus").set(m$.fnc$("INReqSourceSupplier.OnBeforeDataAccess",m$.var("YKEY").get(),m$.var("YUSER").get()));
		}else if(cmdStr.equals("set strResult=$$HideCustoms^INVORG()")){
			m$.var("strResult").set(m$.fnc$("INVORG.HideCustoms"));
		}else if(cmdStr.equals("set strResult='$$ValidItem^INART(YKEY)")){
			m$.var("strResult").set(m$.fnc$("INART.ValidItem",m$.var("YKEY").get()));
		}else if(cmdStr.equals("set strResult=$$IsProgramOutOfDateRange^INReq(YFELD)")){
			m$.var("strResult").set(m$.fnc$("INReq.IsProgramOutOfDateRange",m$.var("YFELD").get()));
		}else if(cmdStr.equals("set strValue=$$GetDescription^WWWStatus(\"INReq\",\"1\",$g(SPRACHE))")){
			m$.var("strValue").set(m$.fnc$("WWWStatus.GetDescription","INReq","1",m$.Fnc.$get(m$.var("SPRACHE"))));
		}else if(cmdStr.equals("if (YPARA = \"\") set YPARA = YAUSWAHL")){
			if(mOp.Equal(m$.var("YPARA").get(),"")){
				m$.var("YPARA").set(m$.var("YAUSWAHL").get());
			}
		}else if(mFncUtil.isMatcher(cmdStr, "set objStoredData=$get(",")")){
			String var = mFncUtil.matcher(cmdStr, "set objStoredData=$get(",")")[0];
			m$.var("objStoredData").set(m$.Fnc.$get(m$.indirectVar(var)));
		}else if(mFncUtil.isMatcher(cmdStr, "set strCode=$$GetRemovalCode^MEDPatient(\"MEDPatient\",",")")){
			String var = mFncUtil.matcher(cmdStr, "set strCode=$$GetRemovalCode^MEDPatient(\"MEDPatient\",",")")[0];
			//m$.var("strCode").set(m$.fnc$("MEDPatient.GetRemovalCode","MEDPatient",var));//TODO REVISAR GetRemovalCode NÃO EXISTE
		}else if(mFncUtil.isMatcher(cmdStr, "SET YOPTION=")){
			String var = mFncUtil.matcher(cmdStr, "SET YOPTION=")[0];
			m$.var("YOPTION").set(var);
		}else if(mFncUtil.isMatcher(cmdStr, "s YQ=",  "  //SR16842")){
			String var = mFncUtil.matcher(cmdStr, "s YQ=",  "  //SR16842")[0];
			m$.var("YQ").set(var);
		}else if(mFncUtil.isMatcher(cmdStr, "S YONCHANGE=")){
			String var = mFncUtil.matcher(cmdStr, "S YONCHANGE=")[0];
			m$.var("YONCHANGE").set(var);
		}else if(mFncUtil.isMatcher(cmdStr, "IF $G(YSEITE)=0 SET YSEITE=")){
			String var = mFncUtil.matcher(cmdStr, "IF $G(YSEITE)=0 SET YSEITE=")[0];
			if(mOp.Equal(m$.Fnc.$get(m$.var("YSEITE")),0)){
				m$.var("YSEITE").set(var);
			}
		}else if(mFncUtil.isMatcher(cmdStr, "if $$IsInUse^INART(YKEY) set YHID = ")){
			String var = mFncUtil.matcher(cmdStr, "if $$IsInUse^INART(YKEY) set YHID = ")[0];
			if(mOp.Logical(m$.fnc$("INART.IsInUse",m$.var("YKEY").get()))){
				m$.var("YHID").set(var);
			}
		}else if(mFncUtil.isMatcher(cmdStr, "IF $$^INARTVERKHID(1)=1 SET YHID=")){
			String var = mFncUtil.matcher(cmdStr, "IF $$^INARTVERKHID(1)=1 SET YHID=")[0];
			if(mOp.Equal(m$.fnc$("INARTVERKHID.main",1),1)){
				m$.var("YHID").set(var);
			}
		}else if(mFncUtil.isMatcher(cmdStr, "IF $$^INARTVERKHID(",")=1 SET YHID=")){
			String[] varArray = mFncUtil.matcher(cmdStr, "IF $$^INARTVERKHID(",")=1 SET YHID=");
			if(mOp.Equal(m$.fnc$("INARTVERKHID.main",varArray[0]),1)){
				m$.var("YHID").set(varArray[1]);
			}
		}else if(mFncUtil.isMatcher(cmdStr, "W $$^WWWTEXT(",")")){
			String var = mFncUtil.matcher(cmdStr, "W $$^WWWTEXT(",")")[0];
			m$.Cmd.Write(m$.fnc$("WWWTEXT.main",var));
		}else if(mFncUtil.isMatcher(cmdStr, "I $P(YFELD,Y,138)>1 W $$^INARTPE(YKEY)")){
			//String var = mFncUtil.matcher(cmdStr, "I $P(YFELD,Y,138)>1 W $$^INARTPE(YKEY)")[0];
			if(mOp.Greater(m$.Fnc.$piece(m$.var("YFELD").get(), m$.var("Y").get(),138),1)){
				m$.Cmd.Write(m$.fnc$("INARTPE.main",m$.var("YKEY").get()));
			}
		}else if (cmdStr.equals("set ^CacheTempEvent(YUCI,\"VARAlertaLocalLinha\",\"Format\") = 1")){
			
			m$.var("^CacheTempEvent",m$.var("YUCI").get(),"VARAlertaLocalLinha","Format").set(1);
		}
		else if(cmdStr.startsWith("SET %TXT(1)=$$")){
			throw new UnsupportedOperationException("Implementation required for Xecute with command SET %TXT(1)=$$: '"+cmdStr+"'");
		}else if (cmdStr.startsWith("SET ") || cmdStr.startsWith("set ") || cmdStr.startsWith("S ") || cmdStr.startsWith("s ") ) {
			throw new UnsupportedOperationException("Implementation required for Xecute with command SET: '"+cmdStr+"'");
		} else if (cmdStr.startsWith("DO ") || cmdStr.startsWith("do ") || cmdStr.startsWith("D ") || cmdStr.startsWith("d ")){
			Do(String.valueOf(command).replaceFirst("DO |do |D |d ", ""));
		} else if (cmdStr.startsWith("USER ") || cmdStr.startsWith("user ") || cmdStr.startsWith("U ") || cmdStr.startsWith("u ")){
			//Do(String.valueOf(command).replaceFirst("DO |do |D |d ", ""));
		} else {
			throw new UnsupportedOperationException("Implementation required for Xecute with command: '"+cmdStr+"'");
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
