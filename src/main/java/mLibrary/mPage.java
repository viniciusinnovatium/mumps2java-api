package mLibrary;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class mPage extends mClass {

	public static String EscapeURL(String string){		
		try {
			return URLEncoder.encode(string,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return string;
		}
	}
	
	public static String Encrypt(String string){
		return string;
	}
	
	public static String Decrypt(String string){
		return string;
	}
	
	public static String HyperEventCall(String method , String args, Integer type){
		return "";
	}
}

