package mLibrary;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import br.com.innovatium.mumps2java.todo.REVIEW;
import br.com.innovatium.mumps2java.todo.TODO;

 
public abstract class mPage extends mClass {

	public void OnPreHTTP(){
	}
	public abstract Object OnPage();
	public void OnPostHTTP(){
	}
	public void Page(){
		Page(true);
	}
	@REVIEW(description = "Revisar ordem de execução dos métodos")
	public void Page(boolean skipheader){
		if (!skipheader){
			OnPreHTTP();
		}
		OnPage();
		OnPostHTTP();
	}
	
	public static Object EscapeURL(Object url) {
		try {
			return URLEncoder.encode(String.valueOf(url), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return url;
		}
	}

	@TODO
	public static String Encrypt(String string) {
		return string;// TODO REVISAR
	}

	@TODO
	public static String Decrypt(String string) {
		return string;// TODO REVISAR
	}

	@TODO
	public String HyperEventCall(String method, String args, Integer type) {
		return "'"+m$.Fnc.$zconvert("\"\"\"cspHttpServerMethod(\"\"\"\""+method+"\"\"\"\","+args+")\"\"\"","","JS").toString()+"'";
	}

	public static String UnescapeURL(Object url) {
		try {
			return URLDecoder.decode(String.valueOf(url), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return url.toString();
		}
	}

	public static Object QuoteJS(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
