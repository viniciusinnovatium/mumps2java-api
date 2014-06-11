package br.com.innovatium.mumps2java.datastructure;

public final class MetadataCache {
	private static final MetadataCache cache;
	private final Tree tree;
	static {
		cache = new MetadataCache();
	}

	private MetadataCache() {
		tree = new Tree();
	}

	public static MetadataCache getCache() {
		return cache;
	}

	public void set(Object[] subs, Object value) {
		tree.set(subs, value);
	}

	public Object get(Object[] subs) {
		return tree.get(subs);
	}

	public boolean contains(Object[] subs) {
		return get(subs) != null;
	}

	public void kill(Object[] subs) {
		tree.kill(subs);
	}

	public int data(Object[] subs) {
		return tree.data(subs);
	}

	public Object order(Object[] subs) {
		return tree.order(subs);
	}

	public void merge(Object[] destSubs, Object[] origSubs) {
		tree.merge(destSubs, origSubs);
	}
}
