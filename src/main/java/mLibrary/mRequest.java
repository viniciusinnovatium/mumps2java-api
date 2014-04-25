package mLibrary;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class mRequest {
	
	private mData mData = new mLocal();
	
	public void populateParameter(Map<String, String[]> map){
		Set<Entry<String, String[]>> results = map.entrySet();
		for (Entry<String, String[]> result : results) {
			for (int i = 0; i < result.getValue().length; i++) {
				mData.subs(result.getKey(), i+1).set(result.getValue()[i]);				
			}
		}
	}	
	
	public Object getCgiEnvs(String str){		
		throw new UnsupportedOperationException();
	}
	
	public mVar getData(Object... args){
		throw new UnsupportedOperationException();
	}
	
	public Object setData(Object... args){
		throw new UnsupportedOperationException();
	}	
}
