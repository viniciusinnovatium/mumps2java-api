package br.com.innovatium.mumps2java.datastructure.refactoring;

import java.util.HashSet;
import java.util.Set;

class Node implements Comparable<Node> {

	final Object[] subs;
	final String key;
	final Object susbcript;
	final boolean isInteger;
	Object value;

	private Node parent;

	Set<Node> subnodes;

	public Node(Object[] subs, String key) {
		this(subs, null, key);
	}

	public Node(Object[] subs, Object value, String key) {
		if (subs == null) {
			throw new IllegalArgumentException(
					"The subs array must not be null");
		}
		this.subs = subs;
		this.value = value;
		this.key = key;
		final int index = subs.length == 1 ? 0 : subs.length - 1;
		Object temp = subs[index];
		if ((temp = toInt(temp.toString())) != null) {
			susbcript = temp;
			isInteger = true;
		} else {
			susbcript = subs[index].toString();
			isInteger = false;
		}

		if (susbcript == null) {
			throw new IllegalArgumentException(
					"The subscripts must not be null");
		}
	}

	public void addSubnode(Node subnode) {
		if (subnodes == null) {
			subnodes = new HashSet<Node>();
		}
		if (!subnodes.add(subnode)) {
			throw new IllegalStateException(
					"Nao foi possivel adicionar o node: " + subnode.key);
		}
		subnode.parent = this;
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean hasSubnodes() {
		return subnodes != null && !subnodes.isEmpty();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Set<Node> getSubnodes() {
		return subnodes;
	}

	public int compareTo(Node o) {
		if (isInteger) {
			return ((Integer) susbcript).compareTo((Integer) o.susbcript);
		}
		return this.susbcript.toString().compareTo(o.susbcript.toString());
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("(").append(key != null ? key : "").append(", ")
				.append(value != null ? value.toString() : "").append(")");

		return string.toString();
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof Node && this.key.equals(((Node) o).key);
	}

	@Override
	public int hashCode() {
		return this.key.hashCode();
	}

	private Integer toInt(String string) {
		try {
			return Integer.valueOf(string);
		} catch (Exception e) {
			return null;
		}
	}

	

	public static void main(String[] asd) {
		Node pedido = new Node(new Object[] { "pedido" }, "pedido",
				Tree.generateKey(new Object[] { "pedido" }));
		pedido.addSubnode(new Node(new Object[] { "10" }, 10, Tree
				.generateKey(new Object[] { "10" })));
		pedido.addSubnode(new Node(new Object[] { "2" }, 2, Tree
				.generateKey(new Object[] { "2" })));
		pedido.addSubnode(new Node(new Object[] { "1" }, 1, Tree
				.generateKey(new Object[] { "1" })));

		pedido.addSubnode(new Node(new Object[] { "item", "1" }, 1, Tree
				.generateKey(new Object[] { "item", "1" })));
		pedido.addSubnode(new Node(new Object[] { "item", "2" }, 1, Tree
				.generateKey(new Object[] { "item", "2" })));
		pedido.addSubnode(new Node(new Object[] { "medicamento", "1" }, 1, Tree
				.generateKey(new Object[] { "medicamento", "1" })));
		pedido.addSubnode(new Node(new Object[] { "medicamento", "2" }, 1,
				"valor"));
		pedido.addSubnode(new Node(new Object[] { "medicamento", "antibiotico",
				1 }, 1, Tree.generateKey(new Object[] { "medicamento",
				"antibiotico", 1 })));
		pedido.addSubnode(new Node(new Object[] { "medicamento", "antibiotico",
				2 }, 1, Tree.generateKey(new Object[] { "medicamento",
				"antibiotico", 2 })));

		for (Node e : pedido.getSubnodes()) {
			System.out.println(e);
		}

	}
}