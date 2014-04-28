package mLibrary;

import java.net.URI;

public class mPage extends mClass {

	public String EscapeURL(String string){		
		return URI.create(string).toASCIIString();
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

