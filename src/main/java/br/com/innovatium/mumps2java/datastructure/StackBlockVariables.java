package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class StackBlockVariables extends StackVariables {
	@Override
	public List<Node> pull(final Integer stackLevel) {
		int last = mapLevel.size();
		if (last == 0) {
			return null;
		}
		final List<Node> nodes = new ArrayList<Node>();
		for (int level = stackLevel; level <= last; level++) {
			Deque<Node> queue = this.mapLevel.get(level);
			if (queue != null) {
				nodes.addAll(queue);
				this.mapLevel.remove(level);
			}
		}
		if (nodes.isEmpty()) {
			return null;
		}
		return nodes;
	}

}
