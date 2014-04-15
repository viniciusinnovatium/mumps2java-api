package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class StackNode {
	private Map<String, Deque<Node>> stack;
	private Map<Integer, Deque<Node>> stackLevel;

	public StackNode() {
		stack = new HashMap<String, Deque<Node>>();
		stackLevel = new HashMap<Integer, Deque<Node>>();
	}

	public Node push(Node node) {
		if (node == null) {
			return null;
		}

		Integer stack = node.getStackLevel();
		if (stack == null) {
			throw new IllegalArgumentException(
					"The inclusion of empty level node is not allowed.");
		}
		Deque<Node> queue = stackLevel.get(stack);

		if (queue == null) {
			queue = new ArrayDeque<Node>();
			stackLevel.put(stack, queue);
		}
		queue.addFirst(node);
		return node;
	}

	public List<Node> pull(Integer stackLevel) {
		Deque<Node> queue = this.stackLevel.get(stackLevel);
		if (queue != null) {
			List<Node> nodes = new ArrayList<Node>(queue);
			this.stackLevel.remove(stackLevel);
			return nodes;
		}
		return null;
	}

	@Deprecated
	public Node pull(String path) {
		Deque<Node> queue = stack.get(path);
		if (queue != null) {
			return queue.pollFirst();
		}
		return null;
	}
}
