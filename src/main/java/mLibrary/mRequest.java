package mLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class mRequest {
	
	private mData data = new mData();
	private HttpServletRequest originalRequest;
	private mContent content;
	
	public mRequest(HttpServletRequest request) {
		originalRequest = request;
	}	
 
	public Object getCgiEnvs(Object...key){			
		return getCgiEnv(key[0], "");
	}
	
	public void setCgiEnvs(Map<String, String[]> cgiEnvs) {
		throw new UnsupportedOperationException();
	}

	public mVar varData(Object... args){
		mVar var = null;
		
		List<Object> list = new ArrayList<Object>();
		list.add("data");
		list.addAll(Arrays.asList(args));
		args = list.toArray();
		if(args!=null && args.length>0){
			var = new mVar(args, this.data);
		}
		return var;
	}
	
	public Object getData(Object... args){
		return varData("data", args).get();
	}
	
	
	public void setData(Object subs, Object idx, Object value){
		data.subs("data", subs, idx).set(value);			
	}

	public void killData(Object object, int i) {
		throw new UnsupportedOperationException();
		
	}

	public void setCgiEnvs(String string, Object object) {
		throw new UnsupportedOperationException();
		
	}

	public mContent getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	public Object getURL() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Object getCgiEnv(Object string, Object pDefault) {
		String cgiVarName = String.valueOf(string);
		switch ( cgiVarName ) {
		case "AUTH_TYPE": return originalRequest.getAuthType();
		case "CONTENT_LENGTH": return originalRequest.getContentLength();
		case "CONTEXT_PATH": return originalRequest.getContextPath();
		case "CONTENT_TYPE": return originalRequest.getContentType();
		case "PATH_INFO": return originalRequest.getPathInfo();
		case "PATH_TRANSLATED": return originalRequest.getPathTranslated();
		case "QUERY_STRING": return originalRequest.getQueryString();
		case "REMOTE_ADDR": return originalRequest.getRemoteAddr();
		case "REMOTE_HOST": return originalRequest.getRemoteHost();
		case "REMOTE_USER": return originalRequest.getRemoteUser();
		case "REQUEST_METHOD": return originalRequest.getMethod();
		case "SCRIPT_NAME": return originalRequest.getServletPath();
		case "SERVER_NAME": return originalRequest.getServerName();
		case "SERVER_PORT": return originalRequest.getServerPort();
		case "SERVER_PORT_SECURE": return originalRequest.isSecure();
		case "SERVER_PROTOCOL": return originalRequest.getProtocol();
		case "SERVER_SOFTWARE": return originalRequest.getHeader("Server-Software");
		case "HTTP_ACCEPT": return originalRequest.getHeader("Accept");
		case "HTTP_ACCEPT_CHARSET": return originalRequest.getHeader("Accept-Charset");
		case "HTTP_ACCEPT_ENCODING": return originalRequest.getHeader("Accept-Encoding");
		case "HTTP_ACCEPT_LANGUAGE": return originalRequest.getHeader("Accept-Language");
		case "HTTP_CONNECTION": return originalRequest.getHeader("Connection");
		case "HTTP_COOKIE": return originalRequest.getHeader("Cookie");
		case "HTTP_HOST": return originalRequest.getHeader("Host");
		case "HTTP_REFERER": return originalRequest.getHeader("Referer");
		case "HTTP_USER_AGENT": return originalRequest.getHeader("User-Agent");
	}
	
		return null;
	}	
}
