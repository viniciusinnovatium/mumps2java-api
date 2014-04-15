package br.com.innovatium.mumps2java.datastructure;

import java.util.Arrays;
import java.util.List;

public final class Tree extends Node {
	private static final String DELIMETER = "~";
	private int currentStackLevel = 0;
	private Node currentNode = null;
	private StackNode stack;

	public Tree() {
		super("", "", null, true);
	}

	public Node stacking(Object... subs) {
		if (stack == null) {
			stack = new StackNode();
		}
		currentStackLevel++;
		Node node = generateNode(subs[0]);
		node.setStackLevel(currentStackLevel);
		stack.push(node);
		kill(subs);
		return generateNode(subs);
	}

	public void unstacking() {
		if (stack == null) {
			stack = new StackNode();
		}
		final List<Node> stackedNodes = stack.pull(currentStackLevel);
		if (stackedNodes != null && !stackedNodes.isEmpty()) {
			for (Node stacked : stackedNodes) {
				Node node = searchSubnode((String)stacked.getSubscript());
				node.kill();
				addSubnode(stacked);
			}
			currentStackLevel--;
		}
	}

	public static final Path path(Object... subs) {
		return new Path(subs);
	}

	public Object order(Path path, int direction) {

		if (path == null) {
			return null;
		}

		if (path.isLastEmpty() && direction >= 1) {
			path = path.generate(path.length() - 1);
			changeToNode(path.toArray());
			return currentNode.firstChild().getSubscript();
		}

		if (path.isLastEmpty() && direction < 1) {
			path = path.generate(path.length() - 1);
			changeToNode(path.toArray());
			return currentNode.lastChild().getSubscript();
		}

		changeToNode(path.toArray());
		if (currentNode == null) {
			return null;
		}

		Node next = currentNode.next(direction);
		if (next != null) {
			return next.getSubscript();
		} else {
			return null;
		}

	}

	public Object order(int direction, Object... subs) {
		return order(path(subs), direction);
	}

	@Deprecated
	public Object get(Path path) {
		return get(path.toArray());
	}

	public Object get(Object... subs) {
		Node n = generateNode(subs);
		return n == null ? null : n.getValue();
	}

	@Deprecated
	public void kill(Path path) {
		kill(path.toArray());
	}

	/*
	 * This method should not return because we have to avoid some reference to
	 * node removed from the tree.
	 */
	public void kill(Object... subs) {
		Node node = searchSubnode(generateKey(subs));
		if (node != null) {
			node.kill();
		}
	}

	public void changeToNode(Object... subs) {
		currentNode = searchSubnode(generateKey(subs));
	}

	@Deprecated
	public void set(Path path, Object value) {

		if (path == null || path.isEmpty()) {
			return;
		}

		Node node = generateNode(path.toArray());
		node.setValue(value);
	}

	public void set(Object[] subs, Object value) {

		if (subs == null || subs.length == 0) {
			return;
		}

		Node node = generateNode(subs);
		node.setValue(value);
	}
	
	public void set(String path, Object value) {
		set(path.split(DELIMETER), value);
	}

	@Deprecated
	public int data(Path path) {
		return data(path.toArray());
	}

	public int data(Object... subs) {
		int cod = -1;

		Node node = generateNode(subs);
		boolean hasValue = node.getValue() != null;
		boolean hasChildPopulated = node.hasSubnodePopulated();
		if (!hasValue && !hasChildPopulated) {
			cod = 0;
		} else if (hasValue && !hasChildPopulated) {
			cod = 1;
		} else if (!hasValue && hasChildPopulated) {
			cod = 10;
		} else if (hasValue && hasChildPopulated) {
			cod = 11;
		}
		return cod;
	}

	public static String generateKey(Object... subs) {
		return generatePath(subs.length, subs);
	}

	public static String generateKey(int length, Object... subs) {
		return generatePath(length, subs);
	}

	public static String generateString(Path path) {
		return generatePath(path.toArray());
	}

	private static String generatePath(Object... subs) {
		return generatePath(subs.length, subs);
	}

	private static String generatePath(int length, Object... subs) {

		if (subs == null || subs.length == 0) {
			return null;
		}
		if (length > subs.length) {
			length = subs.length;
		}

		final StringBuilder builder = new StringBuilder();
		int index = length - 1;
		for (Object obj : subs) {
			builder.append(obj);
			if (index-- > 0) {
				builder.append(DELIMETER);
			}

		}
		return builder.toString();
	}

	protected final Node generateNode(Object... subs) {
		return generateNode(this, subs, 0);
	}

	private Node generateNode(final Node parent, final Object[] subs, int index) {
		String key = generateKey(Arrays.copyOf(subs, index + 1));
		Node node = parent.searchSubnode(key);
		boolean exist = node != null;
		if (!exist) {
			node = null;
			node = new Node(subs[index], generateKey(key));
		}
		// Indicating root node
		if (!exist) {
			parent.addSubnode(node);
		}

		index++;
		if (index < subs.length) {
			node = generateNode(node, subs, index);
		}
		return node;
	}
}
