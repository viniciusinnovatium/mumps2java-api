package mLibrary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class mRequest {
	
	private mData data = new mData();
	private Map<String,String[]> cgiEnvs = new HashMap<String,String[]>();
	private mContent content;
	
	public mRequest(Map<String, String[]> map) {
		populateParameter(map);
	}	
	
	public void populateParameter(Map<String, String[]> map){
		Set<Entry<String, String[]>> results = map.entrySet();
		for (Entry<String, String[]> result : results) {
			for (int i = 0; i < result.getValue().length; i++) {
				setData(result.getKey(), i+1, result.getValue()[i]);
			}
		}
	}	
	
	public Object getCgiEnvs(Object key){			
		return getCgiEnv(key, "");
	}
	
	
	public Map<String, String[]> getCgiEnvs() {
		return cgiEnvs;
	}

	public void setCgiEnvs(Map<String, String[]> cgiEnvs) {
		this.cgiEnvs = cgiEnvs;
		populateParameter(cgiEnvs);
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
		//TODO
		//mFnc.$get(string,pDefault);
		//return "";
		// referencia: http://www.stephenwithington.com/blog/index.cfm/2008/8/26/CGI-Variables-and-Their-Respective-ColdFusionJava-Servlet-Alternative-Methods
		return null;
	}	
}
