package mLibrary;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import br.com.innovatium.mumps2java.todo.REVIEW;
import br.com.innovatium.mumps2java.todo.TODO;

 
public abstract class mPage extends mClass {

	public boolean OnPreHTTP(){
		return true;
	}
	public Object OnPage() {
		return null;
	}
	public void OnPostHTTP(){
	}
	@REVIEW(description = "Revisar ordem de execução dos métodos")
	public void Page(){
		if (OnPreHTTP()){
			OnPage();
			OnPostHTTP();
		}
	}
	/*
	public void Page(boolean skipheader){
	}*/
	
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
		return "cspHttpServerMethod(\""+method+"\","+args+")";
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

	public static Object QuoteJS(Object js) {
		return "'"+mFncUtil.escapeJS(String.valueOf(js))+"'";
	}
}
