package mLibrary;

public class mLocal extends mData {

	@Override
	public Object get(Object... subs) {
		return tree.get(subs);
	}

	@Override
	public void set(Object value) {
		tree.set(tempSubs, value);
	}

	@Override
	void onPopulateTree() {
	}

	@Override
	void onOrder() {
	}

	@Override
	void onKill(Object... subs) {
		tree.kill(subs);
	}

	@Override
	void stacking(Object... subs) {
		tree.stacking(subs);
	}

	@Override
	void unstacking() {
		tree.unstacking();
	}

	@Override
	public String dump() {
		return tree.dump();
	}

}
