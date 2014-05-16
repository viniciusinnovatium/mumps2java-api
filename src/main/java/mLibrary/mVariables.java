package mLibrary;

import static br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil.getVariableType;

import java.util.HashMap;
import java.util.Map;

import br.com.innovatium.mumps2java.datastructure.Node;
import br.com.innovatium.mumps2java.datastructure.Tree;
public final class mVariables {
	private final Map<Integer, Tree> variablesMap;

	public mVariables() {
		variablesMap = new HashMap<Integer, Tree>();
		variablesMap.put(1, new Tree());
		variablesMap.put(2, new Tree());
		variablesMap.put(3, new Tree());
	}

	public Tree getVariables(int type) {
		return variablesMap.get(type);
	}
	
	public void merge(Object[] dest, Object[] orig){
		Tree destTree = variablesMap.get(getVariableType(dest));
		Tree origTree = variablesMap.get(getVariableType(orig));
		
		Node origNode = origTree.findNode(orig);
		destTree.merge(dest, origNode);
	}
}
