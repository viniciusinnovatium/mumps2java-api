package mLibrary;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;


public class mDataAccessPublic extends mDataAccessMemory {
	
	public mDataAccessPublic(mVariables mVariables){
		super(mVariables, DataStructureUtil.PUBLIC);
	}
}
