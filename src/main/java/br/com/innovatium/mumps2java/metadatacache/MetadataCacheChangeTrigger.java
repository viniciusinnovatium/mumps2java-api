package br.com.innovatium.mumps2java.metadatacache;

import javax.ejb.Local;

@Local
public interface MetadataCacheChangeTrigger {
	void insert(Object[] subs, Object value);
}
