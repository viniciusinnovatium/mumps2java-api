package br.com.innovatium.mumps2java.datastructure;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {

	private final Object[] subs;
	private final String key;
	private final Object susbcript;
	final boolean isInteger;
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

	public Node getParent() {
		return parent;
	}

	public Node getSubnode() {
		return subnode;
	}

	public void setSubnode(Node subnode) {
		this.subnode = subnode;
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

	public Object getSusbcript() {
		return susbcript;
	}

	public Object getSusbcriptAsString() {
		return susbcript.toString();
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

	public void addSubnode(Node newSubnode) {
		if (subnode == null) {
			subnode = newSubnode;
		} else {
			Node previous = findPrevious(subnode, newSubnode);
			System.out.println("previos "
					+ previous.getSusbcript()
					+ " de "
					+ newSubnode.getSusbcript()
					+ " = "
					+ previous.getSusbcript().toString()
							.compareTo(newSubnode.getSusbcript().toString()));

			// When previous node is the first sub node into the hierarchy we
			// have to switch its positions to maintain the order mechanism.
			if (previous.isFirstSubnode() && previous.isAfter(newSubnode)) {
				subnode = newSubnode;
				subnode.next = previous;
				previous.previous = subnode;
			} else {
				newSubnode.next = previous.next;
				previous.next = newSubnode;
				newSubnode.previous = previous;
			}

		}
		newSubnode.parent = this;
	}

	public Node findPrevious() {
		return findPrevious(parent.subnode, this);
	}

	public Node findPrevious(Node previous, Node subnode) {
		if (previous.compareTo(subnode) > 0) {
			if (previous.hasPrevious()
					&& previous.getPrevious().compareTo(subnode) < 0) {
				return previous.getPrevious();
			}
			return previous;
		} else if (previous.hasNext()) {
			return findPrevious(previous.next, subnode);
		} else {
			return previous;
		}

	}

	public boolean hasPrevious() {
		return !isFirstSubnode();
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
		return this.next != null;
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
		if (isInteger) {
			try {
				return ((Integer) susbcript).compareTo((Integer) o.susbcript);
			} catch (ClassCastException e) {
				throw new IllegalArgumentException(
						"There is some inconsistence when was setted the nodes "
								+ this + " and " + o
								+ ". Fail to compare subscript: "
								+ this.susbcript + " and this subscript: "
								+ ((Node) o).susbcript
								+ ". They must have to be the same type.", e);
			}
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

	public List<Node> getSubnodes() {

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

	private Integer toInt(String string) {
		try {
			return Integer.valueOf(string);
		} catch (Exception e) {
			return null;
		}
	}
}