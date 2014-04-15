package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author vinicius
 */
public class Node {
	private int nivel;
	private Integer stackLevel;
	private final boolean isTree;

	private Object subscript;
	private Object path;
	private Object value;
	private List<Node> subnodes;
	private Node parent;
	private static Map<Integer, List<Node>> niveis = new HashMap<Integer, List<Node>>();

	public Node(Object subscript) {
		this(subscript, null);
	}

	public Node(Object subscript, Object path) {
		this(subscript, path, null);
	}

	public Node(Object subscript, Object path, Object value) {
		this(subscript, path, value, false);
	}

	public Node(Object subscript, Object path, Object value, boolean isTree) {
		this.subscript = subscript;
		this.path = path;
		this.value = value;
		this.isTree = isTree;
	}

	public Integer getStackLevel() {
		return stackLevel;
	}

	public void setStackLevel(Integer stackLevel) {
		this.stackLevel = stackLevel;
	}

	public boolean isRoot() {
		return parent != null && parent.isTree;
	}

	public boolean isTree() {
		return parent == null;
	}

	public Object getSubscript() {
		return subscript;
	}

	public byte[] getSubscriptAsBytes() {
		return subscript == null ? null : subscript.toString().getBytes();
	}

	public List<Node> getSubnodes() {
		return subnodes;
	}

	public void setSubscript(String subscript) {
		this.subscript = subscript;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Object getValue() {
		return value;
	}

	public String getValueAsString() {
		if (value == null) {
			return "";
		} else if (value instanceof Collection) {
			Collection<?> lista = (Collection<?>) value;
			StringBuilder string = new StringBuilder("[");
			int indice = lista.size() - 1;
			int i = 0;
			for (Object object : lista) {
				string.append(object);
				if (i++ < indice) {
					string.append(",");
				}
			}
			string.append("]");
			return string.toString();
		}
		return value.toString();
	}

	public Node next(int direction) {
		return direction >= 1 ? right() : left();
	}

	public Node first() {
		if (parent == null || !parent.hasSubnodes()) {
			return null;
		}
		List<Node> brothers = parent.subnodes;
		return brothers.get(0);
	}

	public Node firstChild() {
		if (!hasSubnodes()) {
			return null;
		}
		return subnodes.get(0);
	}

	public Node lastChild() {
		if (!hasSubnodes()) {
			return null;
		}
		return subnodes.get(subnodes.size() - 1);
	}

	public Node last() {
		if (parent == null || !parent.hasSubnodes()) {
			return null;
		}
		List<Node> brothers = parent.subnodes;
		return brothers.get(brothers.size() - 1);
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Node add(String key, Object value) {
		Node node = new Node(key, null, value);
		add(node);
		return node;
	}

	public void clear() {
		subnodes.clear();
	}

	public void kill() {
		if (parent != null) {
			parent.subnodes.remove(this);
		}
	}

	public void add(Node node) {
		if (subnodes == null) {
			subnodes = new ArrayList<Node>(20);
		}
		final int nivelNode = this.nivel + 1;
		node.parent = this;
		node.nivel = nivelNode;
		subnodes.add(node);

		if (!niveis.containsKey(nivelNode)) {
			niveis.put(nivelNode, new ArrayList<Node>());
		}

		niveis.get(nivelNode).add(node);
	}

	public Node addChain(Node subnode) {
		add(subnode);
		return subnode;
	}

	public Node addChain(Object key, Object value) {
		Node subnode = new Node(subscript, null, value);
		add(subnode);
		return subnode;
	}

	public Node left() {
		if (!parent.hasSubnodes()) {
			return null;
		}
		List<Node> brothers = parent.subnodes;
		try {
			return brothers.get(brothers.indexOf(this) - 1);
		} catch (IndexOutOfBoundsException e) {
			// retornando o extremo a direita
			return null;
		}
	}

	public Node right() {
		if (parent == null || !parent.hasSubnodes()) {
			return null;
		}
		List<Node> brothers = parent.subnodes;
		try {
			return brothers.get(brothers.indexOf(this) + 1);
		} catch (IndexOutOfBoundsException e) {
			// retornando o extremo a esquerda
			return null;
		}
	}

	public Node up() {
		return parent;
	}

	public List<Node> down() {
		return subnodes;
	}

	public List<Node> down(int nivel) {
		return niveis.get(nivel);
	}

	public Node down(int nivel, int posicao) {
		return niveis.get(nivel).get(posicao);
	}

	public Node subnode(int index) {
		if (!hasSubnodes()) {
			return null;
		}
		try {
			return subnodes.get(index);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("(").append(path != null ? path : "").append(", ")
				.append(value != null ? value.toString() : "").append(")");

		return string.toString();
	}

	public Object getPath() {
		return path;
	}

	public String dump() {
		StringBuilder dump = new StringBuilder();
		dump(this, dump);
		return dump.toString();
	}

	public Node searchSubnode(String subs) {
		if (subs == null) {
			return null;
		}
		return searchSubnode(this, subs);
	}

	public boolean hasSubnodes() {
		return subnodes != null && subnodes.size() > 0;
	}

	public boolean isLeaf() {
		return !isRoot() && !hasSubnodes();
	}

	public boolean hasSubnodePopulated() {
		return hasSubnodePopulated(this);
	}

	private boolean hasSubnodePopulated(Node node) {

		if (node.isLeaf() && node.getValue() != null) {
			return true;
		}

		boolean isPreenchido = false;
		if (node.hasSubnodes()) {
			for (Node subnode : node.subnodes) {
				if (subnode.getValue() != null) {
					isPreenchido = true;
					break;
				}

				isPreenchido = hasSubnodePopulated(subnode);
				if (isPreenchido) {
					return true;
				}
			}
		}

		return isPreenchido;
	}

	private void dump(Node node, StringBuilder dump) {
		dump.append(node.toString()).append("\n");
		if (node.hasSubnodes()) {
			for (Node subnode : node.subnodes) {
				dump(subnode, dump);
			}
		}
	}

	private Node searchSubnode(Node parent, String pathTarget) {
		Node node = null;
		if (parent != null && parent.hasSubnodes()) {
			for (Node subnode : parent.getSubnodes()) {
				if (pathTarget.equals(subnode.getPath())) {
					node = subnode;
					break;
				} else {
					node = searchSubnode(subnode, pathTarget);
				}
			}
		}
		return node;
	}
}
