package mLibrary;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class mPage extends mClass {

	public static Object EscapeURL(Object url){		
		try {
			return URLEncoder.encode(String.valueOf(url),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return url;
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

	public static Object UnescapeURL(Object url) {
		try {
			return URLDecoder.decode(String.valueOf(url),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return url;
		}
	}

	public static Object QuoteJS(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}

