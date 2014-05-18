package mLibrary;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;


public class mDataAccessLocal extends mDataAccessMemory {
	
	public mDataAccessLocal(mVariables mVariables){
		super(mVariables, DataStructureUtil.LOCAL);
	}
}
