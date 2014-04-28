package mLibrary;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class   mClass{
	public mContext m$;
	
	public void setContext(mContext context) {
		this.m$ = context;
	}

	public void cmd$Do(String methodName, Object... parameters) {
		m$.dispatch(methodName, parameters);
	}
	
	public mContext getContext(){
		return m$;
	}
	
	
	
	public void cmd$Do(String methodName) {
		cmd$Do(methodName, (Object[]) null);
	}

	public Object fnc$(String methodName, Object... parameters) {
		return m$.dispatch(methodName, parameters);
	}

	public void cmd$Write(Object... string) {
		try {
			for (Object str : string) {
				System.out.print(String.valueOf(str));
			}
		} catch (NullPointerException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void cmd$WriteJS(Object... string) {
		cmd$Write(string);
	}

	public void cmd$SQL() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}	
	
	public void cmd$WriteHtml(Object... string) {
		cmd$Write(string);
	}

	public mVar m$piece(mVar var, Object delimiter, Object from, Object to) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public mVar m$piece(mVar var, Object delimiter, Object from) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void cmd$Xecute(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void cmd$Merge(mVar target, mVar source) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void cmd$Goto(String label) {
		throw new UnsupportedOperationException();
	}

	/*
	 * Exibe mensagem no console solicitando entrada de dados. 
	 */
	public void cmd$Read(Object... parameters) {
		Scanner s = null;
		try {
			String variable = "";
			if (parameters != null && parameters.length > 0) {
				variable = String.valueOf(parameters[0]);
			}
			s = new Scanner(System.in);
			System.out.println(variable);
			String input = s.next();
			System.out.println("Input: " + input);
		} catch (Exception e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			if (s != null) {
				s.close();
			}
		}
	}

	public void cmd$Lock(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void cmd$Unlock(String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	
	public void cmd$Unlock(mVar var) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}	
	
	public void cmd$Unlock(mVar var, String str) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}	
	
	public void cmd$LockInc(mVar var, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}	
	/*
	 * Pausa o processamento por um determinado número de milisegundos
	 */
	public void cmd$Hang(Object obj) {
		try {
			Thread.sleep(Long.valueOf(String.valueOf(obj)));
		} catch (NumberFormatException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		} catch (InterruptedException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		}

	}
	public void cmd$Job(String string, String string2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}	
}
