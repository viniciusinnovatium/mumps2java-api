package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;
import br.com.innovatium.mumps2java.todo.TODO;

public final class Tree extends Node {
	private int currentStackLevel = 0;
	private final StackVariables stackVariables = new StackVariables();
	private final StackVariables stackBlockVariables = new StackBlockVariables();

	private Map<String, Node> keyValue = new HashMap<String, Node>(100);
	private KillOperationOverNodes killSubnodesOperation = new KillOperationOverNodes();
	private AddOnTreeOperationOverNodes addSubnodesOperation = new AddOnTreeOperationOverNodes();
	private MergeOperationOverNodes mergeSubnodesOperation = new MergeOperationOverNodes(
			this);

	public Tree() {
		// We adopted this subscript to the tree because no one mumps variable
		// can be declared about this symbol.
		super(new Object[] { "@" }, null, "@");
	}

	public void stackingBlock(int blockIndex, Object... variables) {
		pushNodesToStack(blockIndex, stackBlockVariables,
				findSubnodesByVarName(variables));
	}

	public void unstackingBlock(int indexBlock) {
		final List<Node> stackedNodes = stackBlockVariables.pull(indexBlock);
		killStackedVariables(stackedNodes);
	}

	public void stacking(Object... variables) {
		currentStackLevel++;
		pushNodesToStack(currentStackLevel, stackVariables,
				findSubnodesByVarName(variables));
	}

	public boolean contains(Object[] subs) {
		return findNode(subs) != null;
	}

	public void unstacking() {
		final List<Node> stackedNodes = stackVariables.pull(currentStackLevel);
		killStackedVariables(stackedNodes);
		currentStackLevel--;
	}

	public void stackingExcept(Object... variables) {
		currentStackLevel++;
		pushNodesToStack(currentStackLevel, stackVariables,
				findSubnodesExceptsByVarName(variables));
	}

	public void stackingBlockExcept(int blockIndex, Object... variables) {
		pushNodesToStack(blockIndex, stackBlockVariables,
				findSubnodesExceptsByVarName(variables));
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

	private String generateKey(Object... subs) {
		return DataStructureUtil.generateKey(subs);
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

	public Node findNode(Object... subs) {
		return findNodeByKey(generateKey(subs));
	}

	public Node findNode(String variableName) {
		return findNodeByKey(variableName);
	}

	public Object get(Object... subs) {
		Node node = findNode(subs);
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

	public boolean isEmpty() {
		return !this.hasSubnodes();
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

		if (origNode.hasSubnodes()) {
			mergeSubnodesOperation.set(destSubs, origSubs);
			operateOverSubnodes(origNode.getSubnode(), mergeSubnodesOperation);
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
			operation.operate(node);
			Node next = node.getSubnode();
			while (next != null) {
				operateOverSubnodes(next, operation);
				next = next.getNext();
			}
		}
	}

	private boolean isNotPresentOnTree(Node node) {
		return node == null || !node.hasParent();
	}

	private List<Node> findSubnodesExceptsByVarName(Object... variables) {
		if (variables == null) {
			return null;
		}

		List<Node> subnodes = this.getFirstLevelSubnodes();
		if (subnodes == null || subnodes.isEmpty()) {
			return null;
		}

		final List<Node> list = new ArrayList<Node>(30);
		subnodes: for (Node node : subnodes) {
			for (int i = 0; i < variables.length; i++) {
				if (variables[i] != null
						&& variables[i].equals(node.getSubscriptAsString())) {
					continue subnodes;
				}
			}
			list.add(node);

		}
		return list;
	}

	private List<Node> findSubnodesByVarName(Object... variables) {
		if (variables == null) {
			return null;
		}

		List<Node> subnodes = new ArrayList<Node>(30);
		for (Object name : variables) {
			subnodes.add(findNode(name));
		}
		return subnodes;
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

	private final class MergeOperationOverNodes implements OperationOverNodes {
		private Object[] dest;
		private Object[] orig;
		private final Tree tree;

		public MergeOperationOverNodes(Tree tree) {
			this.tree = tree;
		}

		public void set(Object[] dest, Object[] orig) {
			this.dest = dest;
			this.orig = orig;
		}

		public void operate(Node node) {
			Object[] concatSubs = null;
			Object subnodeValue = node.getValue();
			concatSubs = DataStructureUtil.concat(dest,
					node.getSubs(orig.length));
			Node destNode = findNode(concatSubs);
			if (destNode == null) {
				tree.set(concatSubs, subnodeValue);
			} else if (subnodeValue != null) {
				destNode.setValue(subnodeValue);
			}
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

	private void killStackedVariables(Collection<Node> stackedNodes) {
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
		}
	}

	private void pushNodesToStack(int stackLevel, StackVariables stack,
			List<Node> nodes) {
		/*
		 * Iterating over variable names collection. Here we suppose the
		 * variable name is the first subscript of the array.
		 */
		for (Node node : nodes) {
			// Avoid some variables which does not exist into the tree.
			if (node != null) {
				node.setStackLevel(stackLevel);
				stack.push(node);
				kill(node);
			}
		}
	}

}
