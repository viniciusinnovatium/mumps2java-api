package mLibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class mRequest {
	
	private mData mDataRequest = new mLocal();
	private Map<String,String[]> cgiEnvs = new HashMap<String,String[]>();
	
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
	
	public mVar getCgiEnvs(String key){			
		mVar var = varData(key,1);
		return var;
	}
	
	public mVar getCgiEnvs(String key, int idx){			
		mVar var = varData(key,idx);
		return var;
	}
		
	
	public Map<String, String[]> getCgiEnvs() {
		return cgiEnvs;
	}

	public void setCgiEnvs(Map<String, String[]> cgiEnvs) {
		this.cgiEnvs = cgiEnvs;
		populateParameter(cgiEnvs);
	}

	public mVar varData(Object... args){
		return new mVar(args, this.mDataRequest);
	}
	
	public Object getData(Object... args){
		return varData(args).get();
	}
	
	
	public void setData(Object subs, Object idx, Object value){
		mDataRequest.subs(subs, idx).set(value);			
	}

	public void killData(Object object, int i) {
		throw new UnsupportedOperationException();
		
	}	
}
