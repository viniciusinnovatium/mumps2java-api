package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;
import br.com.innovatium.mumps2java.todo.TODO;

public final class Tree extends Node {
	private int currentStackLevel = 0;
	private StackNode stack;
	private Map<String, Node> keyValue = new HashMap<String, Node>(100);
	private KillOperationOverNodes killSubnodesOperation = new KillOperationOverNodes();
	private AddOnTreeOperationOverNodes addSubnodesOperation = new AddOnTreeOperationOverNodes();

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
		for (Object variableName : subs) {
			node = findNode(variableName.toString());
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
				/*
				 * First of all, we are to looking for some node with the same
				 * subscritps of the stacked node, then remove it from the tree
				 * and add the stacked node there. At second, we suppose that
				 * each stacked variable has the key as they variable name, for
				 * instance: subs = [%x] has the name variable as %x, then, it
				 * is identical to its generated key.
				 */
				Node nodeOnTheTree = findNode(stackedNode.getKey());
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
		List<Node> nodes = findSubnodeExcepts(subs);
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
		setting(subs, value);
	}

	public Node kill(Object... subs) {
		Node node = findNode(subs);
		kill(node);
		return node;
	}

	public Object order(Object[] subs, int direction) {
		// Here we are treating the case when we order function is called with
		// empty subscript. At this moment we have to return the first tree
		// subnode.
		if (subs.length == 1 && "".equals(subs[0])) {
			return this.hasSubnodes() ? this.getSubnode().getSubscript() : "";
		}

		Object lastSubscript = subs[subs.length - 1];
		Object subscript = "";

		final boolean isEmptyLastSubs = lastSubscript == null
				|| lastSubscript.toString().length() == 0;

		final boolean isFoward = direction >= 1;
		// Condition to return to first element on list of subnodes.
		if (isEmptyLastSubs) {
			subs = Arrays.copyOf(subs, subs.length - 1);
		}

		final Node node = findNode(subs);
		if (isEmptyLastSubs && isFoward && node != null) {
			subscript = node.hasSubnodes() ? node.getSubnode().getSubscript()
					: "";
		} else if (isEmptyLastSubs && !isFoward && node != null) {
			subscript = node.hasSubnodes() ? findLastNode(node.getSubnode())
					.getSubscript() : "";
		} else if (isFoward && node != null) {
			subscript = node.hasNext() ? node.getNext().getSubscript() : "";
		} else if (node != null) {
			subscript = node.hasPrevious() ? node.getPrevious().getSubscript()
					: "";
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
			for (Node subnode : node.getFirstLevelSubnodes()) {
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
		return findNodeByKey(generateKey(subs));
	}

	public Node findNode(String variableName) {
		return findNodeByKey(variableName);
	}

	public Object get(Object... subs) {
		Node node = keyValue.get(generateKey(subs));
		if (node != null) {
			return node.getValue();
		}
		return null;
	}

	public String dump() {
		StringBuilder string = new StringBuilder();
		operateOverSubnodes(this, new DumpOperationOverNodes(string));
		return string.toString();
	}

	public void merge(final Object[] destSubs, final Object[] origSubs) {
		Node origNode = findNode(origSubs);
		Node destNode = findNode(destSubs);

		if (destNode == null) {
			destNode = setting(destSubs, null);
		}

		if (origNode == null) {
			origNode = setting(origSubs, null);
		}

		if (origNode.getValue() != null) {
			destNode.setValue(origNode.getValue());
		}

		if(origNode.hasSubnodes()) {
			mergeSubnodes(destSubs, origSubs, origNode.getSubnode());	
		}
		
	}
	
	private void mergeSubnodes(final Object[] dest, final Object[] orig, Node node) {

		Object[] concatSubs = null;
		boolean hasSubnodes = node.hasSubnodes();
		boolean subnodeHasNext = hasSubnodes && node.getSubnode().hasNext();

		Object subnodeValue = node.getValue();
		concatSubs = DataStructureUtil.concat(dest, node.getSubs(orig.length));
		Node destNode = findNode(concatSubs);
		if (destNode == null) {
			set(concatSubs, subnodeValue);
		} else if (subnodeValue != null) {
			destNode.setValue(subnodeValue);
		}
		
		if (hasSubnodes) {
			mergeSubnodes(dest, orig, node.getSubnode());
		}

		if (subnodeHasNext) {
			mergeSubnodes(dest, orig, node.getSubnode().getNext());
		}
		
		if (node.hasNext()) {
			mergeSubnodes(dest, orig, node.getNext());
		}
	}

	public Object order(Object... subs) {
		return order(subs, 1);
	}

	/*
	 * Generic method which be reused by anothers when search subnodes is need.
	 * This may occur when we are dumping the tree and kill all subnodes of some
	 * node, for instance.
	 */
	private void operateOverSubnodes(Node node, OperationOverNodes operation) {
		if (node != null) {
			Node next = node;
			do {
				operation.operate(node);

				if (next.hasSubnodes()) {
					operateOverSubnodes(next.getSubnode(), operation);
				}

			} while ((next = next.getNext()) != null);
		}
	}

	private boolean isNotPresentOnTree(Node node) {
		return node == null || !node.hasParent();
	}

	private List<Node> findSubnodeExcepts(Object... subs) {
		if (subs == null) {
			return null;
		}

		List<Node> variables = this.getFirstLevelSubnodes();
		if (variables == null || variables.isEmpty()) {
			return null;
		}

		List<Node> list = null;
		subnodes: for (Node node : variables) {
			for (int i = 0; i < subs.length; i++) {
				if (subs[i] != null
						&& subs[i].equals(node.getSubscriptAsString())) {
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

	private Node setting(Object[] subs, Object value) {

		if (subs == null || subs.length == 0) {
			return null;
		}

		Node node = findNode(subs);
		if (node == null) {
			node = generateNode(subs);
		}
		node.setValue(value);
		return node;
	}

	private void replaceNode(Node stackedNode, Node currentNode) {
		if (currentNode == null) {
			addSubnode(stackedNode);
		} else {
			stackedNode.setNext(currentNode.getNext());
			stackedNode.setPrevious(currentNode.getPrevious());
			stackedNode.setParent(currentNode.getParent());

			if (currentNode.hasPrevious()) {
				currentNode.getPrevious().setNext(stackedNode);
			}
			if (currentNode.hasNext()) {
				currentNode.getNext().setPrevious(stackedNode);
			}

			currentNode.cancelReferences();
		}
		addAllSubnodes(stackedNode);
	}

	private void kill(Node node) {
		if (isNotPresentOnTree(node)) {
			return;
		}
		if (node.isFirstSubnode()) {
			node.getParent().setSubnode(node.getNext());
		} else {
			node.getPrevious().setNext(node.getNext());
		}
		if (node.getNext() != null) {
			node.getNext().setPrevious(node.getPrevious());
		}
		node.cancelReferences();
		// We have to remove from the map all subnodes references, other wise,
		// some another routine can recover them throught get(subs) method.
		killAllSubnodes(node);
	}

	private void killAllSubnodes(Node node) {
		operateOverSubnodes(node, killSubnodesOperation);
	}

	private void addAllSubnodes(Node node) {
		operateOverSubnodes(node, addSubnodesOperation);
	}

	private Node findLastNode(Node node) {
		while (node.hasNext()) {
			node = node.getNext();
		}
		return node;
	}

	private final Node generateNode(Object[] subs) {
		return generateNode(this, subs, 0);
	}

	private Node generateNode(final Node parent, final Object[] subs, int index) {
		index = index + 1;
		final Object[] subnodeArray = Arrays.copyOf(subs, index);
		// Node nodefound = findSubnode(parent, subnodeArray);
		Node nodefound = findNode(subnodeArray);
		boolean exist = nodefound != null;
		if (!exist) {
			nodefound = new Node(subnodeArray, generateKey(subnodeArray));
			parent.addSubnode(nodefound);
			keyValue.put(nodefound.getKey(), nodefound);
		}

		if (index < subs.length) {
			nodefound = generateNode(nodefound, subs, index);
		}

		if (nodefound.getParent() == null) {
			throw new IllegalStateException("Ferrou..." + nodefound);
		}
		return nodefound;
	}

	/**
	 * This class was created to reuse subnodes searching method. Dumping the
	 * tree and kill subnodes methods have the same implementation.
	 * 
	 * @author vinicius
	 * 
	 */
	private interface OperationOverNodes {
		void operate(Node node);
	}

	private final class KillOperationOverNodes implements OperationOverNodes {

		public void operate(Node node) {
			keyValue.remove(node.getKey());
		}

	}

	private final class AddOnTreeOperationOverNodes implements
			OperationOverNodes {

		public void operate(Node node) {
			keyValue.put(node.getKey(), node);
		}

	}

	private final class DumpOperationOverNodes implements OperationOverNodes {
		private StringBuilder dump;

		public DumpOperationOverNodes(StringBuilder dump) {
			this.dump = dump;
		}

		public void operate(Node node) {
			dump.append(node).append("\n");
		}

	}

	private Node findNodeByKey(String key) {
		return keyValue.get(key);
	}

	public static void main(String[] asd) {
		// teste1();
		teste3();
	}

	private static void teste3() {
		Tree tree = new Tree();
		tree.set(new Object[] { "x" }, "teste");
		tree.set(new Object[] { "y" }, "1");
		tree.merge(new Object[] { "x" }, new Object[] { "y" });
		System.out.println("depois:\n" + tree.dump());
	}

	private static void teste1() {

		Tree tree = new Tree();
		tree.set(new Object[] { "x", "10" }, "dec");
		tree.set(new Object[] { "x", "2" }, "seg");
		tree.set(new Object[] { "x", "1" }, "pri");

		System.out.println("first node of the tree: " + tree.order(""));
		System.out.println("first node of the tree: "
				+ tree.order(new Object[] { "" }, -1));

		Object order = "1";
		int i = 0;
		System.out.println("ordering----------");
		while (++i < 10) {
			order = tree.order(new Object[] { "x", order }, 1);
			System.out.println("forward: " + order);
		}
		i = 0;
		System.out.println("ordering----------");
		while (++i < 10) {
			order = tree.order(new Object[] { "x", order }, 0);
			System.out.println("backward: " + order);
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

	private static void teste2() {

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
}
