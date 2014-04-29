package br.com.innovatium.mumps2java.datastructure.refactoring;

import java.util.Arrays;

public final class Tree extends Node {
	private static final String DELIMETER = "~";
	private Node lookedUp;

	public Tree() {
		super(new Object[] { "root" }, null, "root");
	}

	public void set(Object[] subs, Object value) {

		if (subs == null || subs.length == 0) {
			return;
		}

		Node node = generateNode(subs);
		node.setValue(value);
	}

	public Object order(Object[] subs, int direction) {
		Object lastSubscript = subs[subs.length - 1];
		Object subscript = "";

		final boolean isEmptyLastSubs = lastSubscript == null
				|| lastSubscript.toString().length() == 0;

		// Condition to return to first element on list of subnodes.
		if (isEmptyLastSubs) {
			subs = Arrays.copyOf(subs, subs.length - 1);
		}

		final Node node = findNode(subs);
		if (isEmptyLastSubs && node != null) {
			subscript = node.hasSubnodes() ? node.getSubnode().getSusbcript()
					: "";
		} else if (node != null) {
			subscript = node.hasNext() ? node.getNext().getSusbcript() : "";
		}

		return subscript;
	}

	private final Node generateNode(Object[] subs) {
		return generateNode(this, subs, 0);
	}

	private Node generateNode(final Node parent, final Object[] subs, int index) {
		final Object[] subnodeArray = Arrays.copyOf(subs, index + 1);
		Node nodefound = findSubnode(parent, subnodeArray);
		boolean exist = nodefound != null;

		if (!exist) {
			nodefound = new Node(subnodeArray, generateKey(subnodeArray));
			parent.addSubnode(nodefound);
		}

		if (++index < subs.length) {
			nodefound = generateNode(nodefound, subs, index);
		}
		return nodefound;
	}

	public static String generateKey(Object... subs) {
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

	public Node findNode(Object[] subs) {
		return findSubnode(this, subs);
	}

	public Object get(Object[] subs) {
		Node node = findSubnode(this, subs);
		if (node != null) {
			return node.getValue();
		}
		return null;
	}

	private Node findSubnode(Node node, Object[] subs) {
		lookedUp = null;
		findSubnode(node, subs, node.isRoot() ? 0 : 1);
		return lookedUp;
	}

	private void findSubnode(Node node, Object[] subs, int index) {
		if (index >= subs.length) {
			return;
		}
		if (node.hasSubnodes()) {
			Node subnode = node.getSubnode();
			do {
				if (subnode.getSusbcript().equals(subs[index])) {
					if (index == subs.length - 1) {
						lookedUp = subnode;
					}
					findSubnode(subnode, subs, index + 1);
				}
			} while ((subnode = subnode.getNext()) != null);
		}
	}

	public static void main(String[] asd) {
		Tree tree = new Tree();
		tree.set(new Object[] { "pedido", "medicamento" }, 770);
		tree.set(new Object[] { "pedido", "item" }, 66);
		tree.set(new Object[] { "pedido", "automovel" }, 88);

		System.out.println(tree.get(new Object[] { "pedido", "medicamento" }));
		System.out.println(tree.get(new Object[] { "item" }));

		Object order = "item";
		int i = 0;
		while (++i < 10) {
			order = tree.order(new Object[] { "pedido", order }, 1);
			System.out.println("ordenando: " + order);
		}
	}

}
