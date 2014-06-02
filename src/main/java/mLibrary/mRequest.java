package mLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class mRequest {

	private mData data = new mData();
	private HttpServletRequest originalRequest;
	private mContent content;

	public mRequest(HttpServletRequest request) {
		originalRequest = request;
		populateRequest();
	}
	
	public mRequest(HttpServletRequest request, String queryString) {
		originalRequest = request;
		forceRequestData(queryString);
	}
	
	private void populateRequest() {
		Set<Entry<String, String[]>> entries = originalRequest.getParameterMap().entrySet();
		for (Entry<String, String[]> entry : entries) {
			String key = entry.getKey();
			int keyCount = 0;
			for (String value : entry.getValue())
			{
				keyCount++;
				setData(key, keyCount, value);
			}
			
			/*
			for (int keyCount = 1; keyCount <= entry.getValue().length; keyCount++) {
				setData(entry.getKey(), keyCount, entry.getValue()[keyCount-1]);
			}
			*/
		}
	
	}
	
	private void forceRequestData(String queryString) {
		String[] atributos = queryString.split("&");
		String[] parameter = null;
		String key,value;
		for (String attr : atributos) {
			parameter = attr.split("=");
			key = parameter[0];
			value = (parameter.length == 1)?"":parameter[1];
			setData(key, 1, value);
		}
	}
	
	public Object getCgiEnvs(Object... key) {
		return getCgiEnv(key[0], "");
	}

	public void setCgiEnvs(Map<String, String[]> cgiEnvs) {
		throw new UnsupportedOperationException();
	}

	public mVar varData(Object... args) {
		mVar var = null;

		List<Object> list = new ArrayList<Object>();
		list.add("data");
		list.addAll(Arrays.asList(args));
		args = list.toArray();
		if (args != null && args.length > 0) {
			var = new mVar(args, this.data);
		}
		return var;
	}

	public Object getData(Object... args) {
		return varData("data", args).get();
	}

	public void setData(Object subs, Object idx, Object value) {
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
		Object result = pDefault;
		switch (cgiVarName) {
		case "AUTH_TYPE":
			result = originalRequest.getAuthType();
			break;
		case "CONTENT_LENGTH":
			result = originalRequest.getContentLength();
			break;
		case "CONTEXT_PATH":
			result = originalRequest.getContextPath();
			break;
		case "CONTENT_TYPE":
			result = originalRequest.getContentType();
			break;
		case "PATH_INFO":
			result = originalRequest.getPathInfo();
			break;
		case "PATH_TRANSLATED":
			result = originalRequest.getPathTranslated();
			break;
		case "QUERY_STRING":
			result = originalRequest.getQueryString();
			break;
		case "REMOTE_ADDR":
			result = originalRequest.getRemoteAddr();
			break;
		case "REMOTE_HOST":
			result = originalRequest.getRemoteHost();
			break;
		case "REMOTE_USER":
			result = originalRequest.getRemoteUser();
			break;
		case "REQUEST_METHOD":
			result = originalRequest.getMethod();
			break;
		case "SCRIPT_NAME":
			result = originalRequest.getServletPath();
			break;
		case "SERVER_NAME":
			result = originalRequest.getServerName();
			break;
		case "SERVER_PORT":
			result = originalRequest.getServerPort();
			break;
		case "SERVER_PORT_SECURE":
			result = originalRequest.isSecure();
			break;
		case "SERVER_PROTOCOL":
			result = originalRequest.getProtocol();
			break;
		case "SERVER_SOFTWARE":
			result = originalRequest.getHeader("Server-Software");
			break;
		case "HTTP_ACCEPT":
			result = originalRequest.getHeader("Accept");
			break;
		case "HTTP_ACCEPT_CHARSET":
			result = originalRequest.getHeader("Accept-Charset");
			break;
		case "HTTP_ACCEPT_ENCODING":
			result = originalRequest.getHeader("Accept-Encoding");
			break;
		case "HTTP_ACCEPT_LANGUAGE":
			result = originalRequest.getHeader("Accept-Language");
			break;
		case "HTTP_CONNECTION":
			result = originalRequest.getHeader("Connection");
			break;
		case "HTTP_COOKIE":
			result = originalRequest.getHeader("Cookie");
			break;
		case "HTTP_HOST":
			result = originalRequest.getHeader("Host");
			break;
		case "HTTP_REFERER":
			result = originalRequest.getHeader("Referer");
			break;
		case "HTTP_USER_AGENT":
			result = originalRequest.getHeader("User-Agent");
			break;
		default:
			break;
		}
		return (result != null) ? result : pDefault;
	}
}
