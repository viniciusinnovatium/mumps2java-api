package br.com.innovatium.mumps2java.datastructure;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Tree {

	private Map<Object, Node> mapa = new HashMap<Object, Node>();
	private Node currentNode = null;
	private static final String DELIMETER = "~";

	public Tree() {
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
	
	public Object get(Object...subs) {
		Node n = mapa.get(generateKey(subs));
		return n == null ? null : n.getValue();
	}

	@Deprecated
	public void kill(Path path) {
		kill(path.toArray());
	}
	
	public void kill(Object... subs) {
		Node node = generateNode(subs);
		node.kill();
		mapa.remove(node);
	}
	
	public void kill() {
		mapa.clear();
	}

	public void changeToNode(Object... subcripts) {
		currentNode = mapa.get(generatePath(subcripts));
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
	
	public void add(Node node) {
		if (node == null || node.getPath() == null) {
			return;
		}
		set(toPath(node.getPath()), node.getValue());
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		Set<Entry<Object, Node>> entry = mapa.entrySet();
		for (Entry<Object, Node> e : entry) {
			b.append(e.getValue().getPath()).append(" = ")
					.append(e.getValue().getValueAsString()).append("\n");
		}

		return b.toString();
	}

	public Map<Object, Object> getAll() {
		Map<Object, Object> m = new HashMap<Object, Object>(50);

		Set<Entry<Object, Node>> entry = mapa.entrySet();
		for (Entry<Object, Node> e : entry) {
			m.put(e.getValue().getPath(), e.getValue().getValueAsString());
		}

		return m;
	}

	@Deprecated
	public int data(Path path) {
		return data(path.toArray());
	}
	
	public int data(Object...subs) {
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

	public void clear() {
		this.mapa.clear();
	}
	
	public static String generateKey(Object... subs) {
		return generatePath(subs);
	}
	
	public static String generateString(Path path) {
		return generatePath(path.toArray());
	}

	private Object[] filterSubscritps(Object[] subs, int init, int end) {
		Object[] x = new Object[end - init + 1];
		for (int j = 0, i = init; i <= end; j++, i++) {
			x[j] = subs[i];
		}

		return x;
	}

	private String generatePath(Object[] path, int end) {
		return generatePath(Arrays.copyOf(path, end));
	}
	
	private static String generatePath(Object... subs) {

		if (subs == null || subs.length == 0) {
			return null;
		}
		final StringBuilder builder = new StringBuilder();
		int index = subs.length - 1;
		for (Object obj : subs) {
			builder.append(obj);
			if (index-- > 0) {
				builder.append(DELIMETER);
			}

		}
		return builder.toString();
	}

	private Node mapping(Node node) {
		mapa.put(node.getPath(), node);
		return node;
	}

	protected final Node generateNode(Object... subs) {
		return generateNode(subs, 0, subs.length - 1, null);
	}
	
	protected final Node generateNode(Path path, Object value) {
		return new Node(path.getSubscript(), generatePath(path.toArray()), value);
	}
	
	public static final Node newNode(Object value, Object... subs) {
		return new Node(subs[subs.length - 1], generatePath(subs), value);
	}

	protected final Node generateNode(Path path) {
		return generateNode(path.toArray(), 0, path.toArray().length - 1, null);
	}

	private Node generateNode(final Object[] subs, int startLevel,
			int endLevel, Node pai) {
		String path = generatePath(subs, startLevel + 1);

		Node node = mapa.get(path);
		Node nodex = null;
		boolean finish = startLevel == endLevel;

		if (node == null) {
			node = new Node(subs[startLevel], path);
			mapping(node);

			if (pai != null) {
				pai.add(node);
			}

			if (!finish) {

				Object[] s = filterSubscritps(subs, startLevel + 1,
						subs.length - 1);

				Node filho = null;
				StringBuilder pathBuild = new StringBuilder(path);

				int index = subs.length - 1;
				for (Object subscript : s) {

					if (index-- > 0) {
						pathBuild.append(DELIMETER);
					}

					pathBuild.append(subscript);
					filho = new Node(subscript, pathBuild.toString());
					node.add(filho);

					node = filho;
					mapping(filho);
				}
				nodex = filho;

			} else {
				nodex = node;
			}

		} else if (node != null && !finish) {
			nodex = generateNode(subs, startLevel + 1, endLevel, node);
		} else if (node != null && finish) {
			nodex = node;
		}
		return nodex;
	}
	
	private Object[] toArray(String path) {
		if (path == null) {
			return null;
		}
		return path.split(DELIMETER);
	}
	
	private Path toPath(Object path) {
		if (path == null) {
			return null;
		}
		return new Path(toArray(path.toString()));
	}
	
	
}
