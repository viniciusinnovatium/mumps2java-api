package mLibrary;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;

public class mPage extends mClass {

	public String EscapeURL(String string){		
		try {
			return URLEncoder.encode(string,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return string;
		}
	}
	
	public String Encrypt(String string){
		return string;
	}
	
	public String Decrypt(String string){
		return string;
	}
	
	String HyperEventCal(String method , String args, Integer type){
		return "";
	}
}

