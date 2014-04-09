package mLibrary;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public final class ListObject {
	private final List<Object> list = new LinkedList<Object>();

	private ListObject() {
	};

	static ListObject getInstance() {
		return new ListObject();
	}

	static ListObject concat(ListObject ... lists) {
		ListObject l = new ListObject();
		if (lists != null && lists.length > 0) {
			for (ListObject listObject : lists) {
				l.list.addAll(listObject.list);
			}
		}
		return l;
	}

	static ListObject add(Object ... elements) {
		ListObject l = new ListObject();
		l.list.addAll(Arrays.asList(elements));
		return l;
	}

	ListObject sublist(int init, int end) {
		final ListObject l = new ListObject();
		if (init < 0) {
			init = 0;
		}

		if (end > this.list.size()) {
			end = this.list.size();
		}

		if (init > end) {
			init = end;
		}

		l.list.addAll(this.list.subList(init, end));
		return l;
	}

	public Object element(int position) {
		if (position <= 0) {
			position = 0;
		} else {
			position = position - 1;
		}
		return this.list.get(position);
	}

	public Object firstElement() {
		return element(1);
	}

	public int length() {
		return list.size();
	}

}
