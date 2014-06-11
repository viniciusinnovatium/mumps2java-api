package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node implements Comparable<Node> {

	private final Object[] subs;
	private final String key;
	private final Object susbscript;
	final boolean isNumeric;
	private Integer stackLevel;

	private Object value;

	private Node parent;
	private Node subnode;
	private Node next;
	private Node previous;

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
		if(temp == null){
			throw new IllegalArgumentException("Subscript must not be empty. The subs setted is "+Arrays.deepToString(subs));
		}
		if (temp instanceof Double && (Double) temp % 1 == 0) {
			temp = ((Double) temp).longValue();
		}
		if ((temp = isNumber(temp.toString())) != null) {
			susbscript = temp;
			isNumeric = true;
		} else {
			susbscript = subs[index].toString();
			isNumeric = false;
		}

		if (susbscript == null) {
			throw new IllegalArgumentException(
					"The subscripts must not be null");
		}
	}

	public Node getParent() {
		return parent;
	}

	public Node getSubnode() {
		return subnode;
	}

	public void setSubnode(Node subnode) {
		this.subnode = subnode;
	}

	public Object[] getSubs(int start) {
		return Arrays.copyOfRange(subs, start, subs.length);
	}

	public void cancelReferences() {
		// Canceling all references that node does.
		this.parent = null;
		this.next = null;
		this.previous = null;
	}

	public Object[] getSubs() {
		return subs;
	}

	public String getKey() {
		return key;
	}

	public Object[] getSubsExceptFirst() {
		return Arrays.copyOfRange(subs, 1, subs.length);
	}

	public Object getSubscript() {
		return susbscript;
	}

	public Object getSubscriptAsString() {
		return susbscript.toString();
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public Node getPrevious() {
		return previous;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public void setPrevious(Node previous) {
		this.previous = previous;
	}

	public boolean hasParent() {
		return this.parent != null;
	}

	void addSubnode(Node newSubnode) {
		if (subnode == null) {
			subnode = newSubnode;
		} else {
			Node previous = findLessThan(subnode, newSubnode);
			// When previous node is the first sub node into the hierarchy we
			// have to switch its positions to maintain the order mechanism.
			if (previous.isFirstSubnode() && previous.isAfter(newSubnode)) {
				subnode = newSubnode;
				newSubnode.next = previous;
				newSubnode.previous = null;
				previous.previous = newSubnode;
			} else {
				newSubnode.next = previous.next;
				previous.next = newSubnode;
				newSubnode.previous = previous;
				if (newSubnode.next != null) {
					newSubnode.next.previous = newSubnode;
				}
			}

		}
		newSubnode.parent = this;
	}

	Node findLessThan(Node initial, Node reference) {
		if (initial.compareTo(reference) > 0) {
			if (initial.hasPrevious()
					&& initial.getPrevious().compareTo(reference) < 0) {
				return initial.getPrevious();
			}
			return initial;
		} else if (initial.hasNext()) {
			if (initial == initial.next) {
				throw new IllegalArgumentException(
						"Previous node is equal the next node");
			}
			return findLessThan(initial.next, reference);
		} else {
			return initial;
		}

	}

	public boolean hasPrevious() {
		return this.previous != null;
	}

	public boolean isFirstSubnode() {
		if (parent != null && parent.hasSubnodes()) {
			return parent.getSubnode().equals(this);
		}
		return false;
	}

	public boolean isAfter(Node node) {
		return this.compareTo(node) > 0;
	}

	public boolean hasNext() {
		return next != null && !next.equals(previous);
	}

	public boolean isRoot() {
		return parent == null;
	}

	public boolean hasSubnodes() {
		return subnode != null;
	}

	public boolean isLeaf() {
		return !isRoot() && !hasSubnodes();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int compareTo(Node o) {
		boolean boothNumeric = this.isNumeric && o.isNumeric;
		if (boothNumeric) {
			try {
				return ((Integer) susbscript).compareTo((Integer) o.susbscript);
			} catch (ClassCastException e) {
				throw new IllegalArgumentException(
						"There is some inconsistence when was setted the nodes "
								+ this + " and " + o
								+ ". Fail to compare subscript: "
								+ this.susbscript + " and this subscript: "
								+ ((Node) o).susbscript
								+ ". They must have to be the same type.", e);
			}
		}
		return this.susbscript.toString().compareTo(o.susbscript.toString());
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("(").append(key != null ? key : "").append(", ")
				.append(value).append(")");

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

	public List<Node> getFirstLevelSubnodes() {

		if (this == null || !this.hasSubnodes()) {
			return null;
		}

		final List<Node> list = new ArrayList<Node>(30);
		Node next = this.getSubnode();
		do {
			list.add(next);
		} while ((next = next.getNext()) != null);
		return list;
	}

	public Integer getStackLevel() {
		return stackLevel;
	}

	public void setStackLevel(Integer stackLevel) {
		this.stackLevel = stackLevel;
	}

	private Integer isNumber(String string) {
		try {
			return Integer.parseInt(string);
		} catch (Exception e) {
			return null;
		}
	}
}