package br.com.innovatium.mumps2java.datastructure.refactoring;

class Node implements Comparable<Node> {

	private final Object[] subs;
	private final String key;
	private final Object susbcript;
	final boolean isInteger;
	Object value;

	private Node parent;
	private Node subnode;
	private Node next;

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

	public Object[] getSubs() {
		return subs;
	}

	public String getKey() {
		return key;
	}

	public Object getSusbcript() {
		return susbcript;
	}

	public Node getNext() {
		return next;
	}

	public void addSubnode(Node newSubnode) {
		if (subnode == null) {
			subnode = newSubnode;
		} else {
			Node previous = findPrevious(subnode, newSubnode);
			// When previous node is the first sub node into the hierarchy we
			// have to switch its positions to maintain the order mechanism.
			if (previous.isFirstSubnode()) {
				subnode = newSubnode;
				subnode.next = previous;
			} else {
				newSubnode.next = previous.next;
				previous.next = newSubnode;
			}

		}
		subnode.parent = this;
	}

	public Node findPrevious(Node previous, Node subnode) {
		if (previous.compareTo(subnode) > 0) {
			return previous;
		} else if (previous.hasNext()) {
			return findPrevious(previous.next, subnode);
		} else {
			return previous;
		}

	}

	public boolean isFirstSubnode() {
		return this.parent.getSubnode().equals(this);
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

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
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
}