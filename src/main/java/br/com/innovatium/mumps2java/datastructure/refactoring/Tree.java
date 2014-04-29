package br.com.innovatium.mumps2java.datastructure.refactoring;

import java.util.Arrays;

public final class Tree extends Node {
	private static final String DELIMETER = "~";
	private Node lookedUp;

	public Tree() {
		super(new Object[] { "" }, null, null);
	}

	public void set(Object[] subs, Object value) {

		if (subs == null || subs.length == 0) {
			return;
		}

		Node node = generateNode(subs);
		node.setValue(value);
	}

	protected final Node generateNode(Object[] subs) {
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
			for (Node subnode : node.getSubnodes()) {
				if (subnode.susbcript.equals(subs[index])) {
					if (index == subs.length - 1) {
						lookedUp = subnode;
					}
					findSubnode(subnode, subs, index + 1);
				}
			}
		}
	}

	public static void main(String[] asd) {
		Tree tree = new Tree();
		tree.set(new Object[] { "pedido" }, 32);
		tree.set(new Object[] { "pedido", "item", 1 }, 66);
		tree.set(new Object[] { "pedido", "medicamento", 1 }, 770);
		tree.set(new Object[] { "pedido", "entrada", 1 }, 90);
		tree.set(new Object[] { "pedido", "entrada" }, 33);

		System.out.println(tree.findNode(
				new Object[] { "pedido", "entrada", 1 }).getValue());
		System.out.println(tree.findNode(new Object[] { "pedido", "entrada" })
				.getValue());

		System.out.println(tree.findNode(new Object[] { "pedido" }).getValue());
	}

}
