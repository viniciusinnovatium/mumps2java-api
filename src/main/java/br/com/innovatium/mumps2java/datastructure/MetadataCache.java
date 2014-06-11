package br.com.innovatium.mumps2java.datastructure;



public final class MetadataCache {
	private static final MetadataCache metadataCache;
	private final Tree tree;
	private QueryCache queryCache = new QueryCache();
	static {
		metadataCache = new MetadataCache();
	}

	private MetadataCache() {
		tree = new Tree();
		initGlobalOfTheSystem();
	}

	public static MetadataCache getCache() {
		return metadataCache;
	}

	public void set(Object[] subs, Object value) {
		tree.set(subs, value);
	}

	public Object get(Object[] subs) {
		return tree.get(subs);
	}

	public boolean isQueried(Object[] subs) {
		return this.queryCache.isCached(subs);
	}
	
	public void addQueried(Object[] subs) {
		this.queryCache.add(subs);
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
	
	private void initGlobalOfTheSystem(){
		queryCache.add(new Object[]{"^|%SYS|SYS"});
		queryCache.add(new Object[]{"^|%SYS|SYS", "UserIdentification"});
		
	}
}
