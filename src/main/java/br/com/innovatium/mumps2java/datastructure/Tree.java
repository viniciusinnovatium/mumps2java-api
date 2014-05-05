package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;
import br.com.innovatium.mumps2java.todo.TODO;

public final class Tree extends Node {
	private int currentStackLevel = 0;
	private Node lookedUp;
	private StackNode stack;

	public Tree() {
		super(new Object[] { "root" }, null, "root");
	}

	public void stacking(Object... subs) {
		if (stack == null) {
			stack = new StackNode();
		}
		currentStackLevel++;
		Node node = null;
		/*
		 * Iterating over variable names collection. Here we suppose the
		 * variable name is the first subscript of the array.
		 */
		for (Object subscript : subs) {
			node = findNode(new Object[] { subscript });
			// Avoid some variables which does not exist into the tree.
			if (node != null) {
				node.setStackLevel(currentStackLevel);
				stack.push(node);
				kill(node);
			}
		}
	}

	public void unstacking() {
		if (stack == null) {
			stack = new StackNode();
		}
		final List<Node> stackedNodes = stack.pull(currentStackLevel);
		if (stackedNodes != null && !stackedNodes.isEmpty()) {
			for (Node stackedNode : stackedNodes) {
				// First of all, we have to looking for if there is some node
				// with the same subscritps of the stacked node, then remove it
				// from the tree and add the stacked node there.
				Node nodeOnTheTree = findNode(stackedNode.getSubs());
				replaceNode(stackedNode, nodeOnTheTree);
				stackedNode.setStackLevel(0);
			}
			currentStackLevel--;
		}
	}

	public void stackingExcept(Object... subs) {
		if (stack == null) {
			stack = new StackNode();
		}
		currentStackLevel++;
		List<Node> nodes = searchSubnodeExcepts(subs);
		if (nodes != null) {
			for (Node node : nodes) {
				node.setStackLevel(currentStackLevel);
				stack.push(node);
				kill(node);
			}
		}
	}

	public int data(Object[] subs) {
		Node node = findNode(subs);
		if (node == null) {
			return 0;
		}
		int cod = -1;
		boolean hasValue = node.getValue() != null;
		boolean hasChildPopulated = hasPopulatedSubnode(node);
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

	public Object[] generateSubs(String key) {
		return DataStructureUtil.generateSubs(key);
	}

	public Object[] generateSubs(String tableName, String key) {
		return DataStructureUtil.generateSubs(tableName, key);
	}

	public void set(Object[] subs, Object value) {

		if (subs == null || subs.length == 0) {
			return;
		}

		Node node = generateNode(subs);
		node.setValue(value);
	}

	public Node kill(Object... subs) {
		Node node = findNode(subs);
		kill(node);
		return node;
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

	public static String generateKey(Object... subs) {
		return DataStructureUtil.generateKey(subs);
	}

	public static String generateKey(boolean isDiskAccess, Object... subs) {
		return DataStructureUtil.generateKey(isDiskAccess, subs);
	}

	public boolean hasPopulatedSubnode(Node node) {

		boolean isPreenchido = false;
		if (node.hasSubnodes()) {
			for (Node subnode : node.getSubnodes()) {
				if (subnode.getValue() != null) {
					isPreenchido = true;
					break;
				}

				isPreenchido = hasPopulatedSubnode(subnode);
				if (isPreenchido) {
					return true;
				}
			}
		}

		return isPreenchido;

	}

	// The method which returns the sub nodes of the node should be enhanced.
	@TODO
	public boolean hasPopulatedSubnode(Node node, boolean found) {

		if (!found && node.hasSubnodes()) {
			Node subnode = node.getSubnode();
			if (subnode.getValue() != null) {
				found = true;
			}
			found = hasPopulatedSubnode(subnode, found);
		} else if (!found && node.isLeaf() && node.getValue() != null) {
			found = true;
		} else if (!found && node.hasNext()) {
			found = hasPopulatedSubnode(node.getNext(), found);
		}
		return found;
	}

	public Node findNode(Object[] subs) {
		return findSubnode(this, subs);
	}

	public Object get(Object... subs) {
		Node node = findSubnode(this, subs);
		if (node != null) {
			return node.getValue();
		}
		return null;
	}

	public String dump() {
		StringBuilder string = new StringBuilder();
		dump(this, string);
		return string.toString();
	}

	private void dump(Node node, final StringBuilder string) {
		if (node != null) {
			Node next = node;
			do {
				string.append(next).append("\n");
				if (next.hasSubnodes()) {
					dump(next.getSubnode(), string);
				}
			} while ((next = next.getNext()) != null);
		}
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
				// Here the looked up subscripts is coming as string, so we have
				// to compare strings always, on other way, we never find the
				// nodes.
				if (subnode.getSusbcriptAsString().equals(
						subs[index].toString())) {
					if (index == subs.length - 1) {
						lookedUp = subnode;
						break;
					}
					findSubnode(subnode, subs, index + 1);
				}
			} while ((subnode = subnode.getNext()) != null);
		}
	}

	private List<Node> searchSubnodeExcepts(Object... subs) {
		if (subs == null) {
			return null;
		}

		List<Node> list = null;
		List<Node> variables = this.getSubnodes();

		subnodes: for (Node node : variables) {
			for (int i = 0; i < subs.length; i++) {
				if (subs[i] != null
						&& subs[i].equals(node.getSusbcriptAsString())) {
					continue subnodes;
				}
			}
			if (list == null) {
				list = new ArrayList<Node>(30);
			}
			list.add(node);

		}
		return list;
	}

	private void replaceNode(Node newNode, Node oldNode) {
		if (newNode == null) {
			return;
		} else if (oldNode == null) {
			addSubnode(newNode);
		} else {
			newNode.setNext(oldNode.getNext());
			newNode.setPrevious(oldNode.getPrevious());
			newNode.setParent(oldNode.getParent());

			if (oldNode.hasPrevious()) {
				oldNode.getPrevious().setNext(newNode);
			}
			if (oldNode.hasNext()) {
				oldNode.getNext().setPrevious(newNode);
			}

			oldNode.cancelReferences();
		}
	}

	private void kill(Node node) {
		if (node == null) {
			return;
		}
		if (node.isFirstSubnode()) {
			node.getParent().setSubnode(node.getNext());
		} else {
			node.getPrevious().setNext(node.getNext());
		}
		node.cancelReferences();
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

	public static void main(String[] asd) {
		teste1();

		Tree tree = new Tree();
		tree.set(new Object[] { "a1" }, 1);
		tree.set(new Object[] { "e10" }, 1);
		tree.set(new Object[] { "e" }, 1);
		tree.set(new Object[] { "d" }, 1);
		tree.set(new Object[] { "b" }, 2);
		tree.set(new Object[] { "c" }, 3);

		tree.set(new Object[] { "a" }, 2);

		System.out.println("antes do stack.....");
		System.out.println(tree.dump());
		tree.stacking("b", "a");
		System.out.println("depois do stack.....");
		System.out.println(tree.dump());

		tree.set(new Object[] { "b" }, "novo valor para b");
		System.out.println("recuperando: " + tree.get("a"));
		System.out.println("recuperando: " + tree.get("b"));
		System.out.println("recuperando: " + tree.get("c"));
		System.out.println("unstacking: " + tree.get("a1"));
		System.out.println("unstacking: " + tree.get("a2"));

		tree.unstacking();
		System.out.println("depois do unstack.....");
		System.out.println(tree.dump());
		System.out.println("unstacking: " + tree.get("a"));
		System.out.println("unstacking: " + tree.get("b"));
		System.out.println("unstacking: " + tree.get("c"));
		System.out.println("unstacking: " + tree.get("a1"));
		System.out.println("unstacking: " + tree.get("a2"));

		System.out.println("comparando: " + "a2".compareTo("a1"));
	}

	private static void teste1() {
		Tree tree = new Tree();
		tree.set(new Object[] { "x", "10" }, "dec");
		tree.set(new Object[] { "x", "2" }, "seg");
		tree.set(new Object[] { "x", "1" }, "pri");

		Object order = "1";
		int i = 0;
		while (++i < 10) {
			order = tree.order(new Object[] { "x", order }, 1);
			System.out.println("subscritp: " + order + " value: "
					+ tree.get(new Object[] { "x", order }));
		}

		tree.set(new Object[] { "y", "elemento2" }, "e1");
		tree.set(new Object[] { "y", "elemento1" }, "e1");
		tree.set(new Object[] { "y", "aelemento1" }, "a1");

		order = "";
		i = 0;
		while (++i < 4) {
			order = tree.order(new Object[] { "y", order }, 1);
			System.out.println("subscritp: " + order + " value: "
					+ tree.get(new Object[] { "y", order }));
		}

		tree.set(new Object[] { "w", "1" }, "1");
		tree.set(new Object[] { "w", "2" }, "2");
		tree.set(new Object[] { "w", "3" }, "3");
		tree.set(new Object[] { "w", "1", "1" }, "11");
		tree.set(new Object[] { "w", "1", "2" }, "12");
		tree.set(new Object[] { "w", "1", "3" }, "13");

		System.out.println("\n----- dumping -----");
		System.out.println(tree.dump());

		System.out.println("pegando: "
				+ tree.get(new Object[] { "w", "1", "2" }));
		System.out.println("matou: " + tree.kill(new Object[] { "w", "1" }));

		System.out.println("\n----- dumping -----");
		System.out.println(tree.dump());

		tree.set(new Object[] { "1" }, 1);
		tree.set(new Object[] { "1", "11" }, null);
		tree.set(new Object[] { "1", "12" }, 12);
		tree.set(new Object[] { "1", "3" }, null);
		tree.set(new Object[] { "1", "3", "33" }, 33);
		tree.set(new Object[] { "1", "4" }, 4);
		tree.set(new Object[] { "2" }, null);
		System.out.println(tree.get(new Object[] { "1", "3", "33" }));

		System.out
				.println("Funcao data deve ser igual a 0 valor: false subnodes: false => "
						+ tree.data(new Object[] { "2" }));
		System.out
				.println("Funcao data deve ser igual a 0 valor: false subnodes: false => "
						+ tree.data(new Object[] { "1", "11" }));
		System.out
				.println("Funcao data deve ser igual a 1 valor: true subnodes: false => "
						+ tree.data(new Object[] { "1", "4" }));
		System.out
				.println("Funcao data deve ser igual a 10 valor: false subnodes: true => "
						+ tree.data(new Object[] { "1", "3" }));
		System.out
				.println("Funcao data deve ser igual a 11 valor: true subnodes: true => "
						+ tree.data(new Object[] { "1" }));

	}
}
