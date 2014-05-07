package mLibrary;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mCmd extends mParent{


	public mCmd(mContext m$) {
		super(m$);
		// TODO Auto-generated constructor stub
	}

	public void Close(Object $$$OprLog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	/*
	public void Do(Object objClass, String methodName, Object... parameters) {
		methodName = m$.defineMethodName(null, methodName);
		m$.dispatch(null, methodName, parameters);
		
	}
	
	public void Do(String methodName) {
		Do(methodName, (Object[]) null);
	}
	
	public void Do(String methodName, Object... parameters) {
		Do(null, methodName, parameters);
	}
*/
	public void Do(Object... args) {
		m$.fnc$(args);
	}
	
	public void Goto(String label) {
		throw new UnsupportedOperationException();
	}

	/*
	 * Pausa o processamento por um determinado número de milisegundos
	 */
	public void Hang(Object obj) {
		try {
			Thread.sleep(Long.valueOf(String.valueOf(obj)));
		} catch (NumberFormatException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		} catch (InterruptedException e) {
			Logger.getLogger(mClass.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	public void Job(String string, String string2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Lock(String string, String string2, String string3) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void LockInc(mVar var, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Merge(mVar target, mVar source) {
		// TODO Auto-generated method stub
		Object obj1 = m$.Fnc.$order(target);
		Object obj2 = source.order();
		
		throw new UnsupportedOperationException();
	}

	public void Open(Object $$$OprLog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Open(Object object, String string, int i) {
		// TODO Auto-generated method stub
		
	}

	public void Open(Object object, String string, Object concat, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	/*
	 * Exibe mensagem no console solicitando entrada de dados.
	 */
	public void Read(Object... parameters) {
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

	public void SQL() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Unlock(mVar var) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Unlock(mVar var, String str) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Unlock(String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Use(Object $$$OprLog) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void Write(Object... string) {
		try {
			for (Object str : string) {
				if(str instanceof Double ){
					System.out.print(BigDecimal.valueOf((Double)str));
				}else{
					System.out.print(String.valueOf(str));
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
		Write(string);
	}

	public void Xecute(Object object) {
		if (String.valueOf(object).startsWith("do ")) {
			String methodName = String.valueOf(object);
			String methodSrc = String.valueOf(object).replaceAll("do ", "");
			if (object.equals("do ^WWWFORM")) {
				methodName = "WWWFORM.main";
			} else {
				if (methodSrc.contains("^")) {
					String[] methodSplit = methodSrc.split("\\^");
					if (methodSplit.length == 2) {
						methodName = methodSplit[1].concat(".").concat(
								methodSplit[0]);
					} else if (methodSplit.length == 1) {
						methodName = methodSplit[0];
					}
				}
			}
			Do(methodName);
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
