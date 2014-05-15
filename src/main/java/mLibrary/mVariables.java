package mLibrary;

import java.util.HashMap;
import java.util.Map;

import br.com.innovatium.mumps2java.datastructure.Tree;

public final class mVariables {
	private final Map<Character, Tree> variablesMap;

	public mVariables() {
		variablesMap = new HashMap<Character, Tree>();
		variablesMap.put('%', new Tree());
		variablesMap.put('^', new Tree());
		variablesMap.put(null, new Tree());
	}

	public Tree getVariables(Character type) {
		return variablesMap.get(type);
	}
}
