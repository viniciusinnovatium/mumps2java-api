package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author vinicius
 */
public class Node {

	private Object subscript;
	private Object path;
	private Object value;
	private List<Node> subnodes;
	private Node parent;
	private int nivel;
	private static Map<Integer, List<Node>> niveis = new HashMap<Integer, List<Node>>();

	public Node(Object subscript) {
		this(subscript, null);
	}
	
	public Node(Object subscript, Object path) {
		this(subscript, path, null);
	}

	public Node(Object subscript, Object path, Object value) {
		this.subscript = subscript;
		this.path = path;
		this.value = value;
	}

	public boolean isRoot() {
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
		if (parent == null || !parent.hasChildren()) {
			return null;
		}
		List<Node> brothers = parent.subnodes;
		return brothers.get(0);
	}
	
	public Node firstChild() {
		if (!hasChildren()) {
			return null;
		}
		return subnodes.get(0);
	}
	
	public Node lastChild() {
		if (!hasChildren()) {
			return null;
		}
		return subnodes.get(subnodes.size() - 1);
	}
	
	public Node last() {
		if (parent == null || !parent.hasChildren()) {
			return null;
		}
		List<Node> brothers = parent.subnodes;
		return brothers.get(brothers.size() - 1);
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Node add(String key, Object value) {
		return add(new Node(key, null, value));
	}

	public void kill() {
		parent.subnodes.remove(this);
	}

	public Node add(Node... nodes) {
		if (subnodes == null) {
			subnodes = new ArrayList<Node>(20);
		}
		final int nivelNode = this.nivel + 1;
		for (Node node : nodes) {
			node.parent = this;
			node.nivel = nivelNode;
			subnodes.add(node);
		}
		if (!niveis.containsKey(nivelNode)) {
			niveis.put(nivelNode, new ArrayList<Node>());
		}
		niveis.get(nivelNode).addAll(Arrays.asList(nodes));
		return this;
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
		if (!parent.hasChildren()) {
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
		if (parent == null || !parent.hasChildren()) {
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
		if (!hasChildren()) {
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
		string.append("(")
				.append(subscript != null ? subscript.toString() : "")
				.append(", ").append(value != null ? value.toString() : "")
				.append(")");

		return string.toString();
	}

	public Object getPath() {
		return path;
	}

	/*
	private String generatePath(Node node, StringBuilder tree) {
		if (node.parent == null) {
			tree.append(node.subscript);
		} else {
			tree.append(node.subscript).append(",");
			generatePath(node.parent, tree);
		}

		String[] array = tree.toString().split(",");
		reverse(array);
		return Arrays.deepToString(array).replace("[", "").replace("]", "");
	}
	
	private void reverse(String[] arr) {

		if (arr == null || arr.length == 1) {
			return;
		}

		String x = null;
		int total = arr.length;
		int indice = arr.length - 1;
		int parada = arr.length / 2 - 1;
		for (int i = 0; i < total; i++) {
			x = arr[indice - i];
			arr[indice - i] = arr[i];
			arr[i] = x;
			if (i == parada) {
				break;
			}
		}
	}
	
	*/

	public Node get(String... subscripts) {
		return get(this, subscripts);
	}

	

	private Node get(Node node, String... subscripts) {
		if (subscripts == null) {
			return null;
		} else if (subscripts.length == 1
				&& node.subscript.equals(subscripts[0])) {
			return node;
		} else {
			Node back = null;
			for (int i = 0; i < subscripts.length; i++) {
				if (!node.subscript.equals(subscripts[0])) {
					return null;
				}
				if (!node.hasChildren()) {
					return null;
				}
				for (Node subnode : node.subnodes) {
					if (subnode.subscript.equals(subscripts[1])) {
						back = get(subnode, Arrays.copyOfRange(subscripts, 1,
								subscripts.length));
					}
				}
			}
			return back;
		}
	}

	public boolean hasChildren() {
		return subnodes != null && subnodes.size() > 0;
	}
	
	public boolean isLeaf() {
		return !isRoot() && !hasChildren();
	}
	
	public boolean hasSubnodePopulated() {
		return hasSubnodePopulated(this);
	}
	
	private boolean hasSubnodePopulated(Node node) {
		
		if (node.isLeaf() && node.getValue() != null) {
			return true;
		}

		boolean isPreenchido = false;
		if (node.hasChildren()) {
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
}
