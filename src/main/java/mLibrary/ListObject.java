package mLibrary;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;

public final class ListObject {
	private final List<Object> list = new LinkedList<Object>();

	private ListObject() {
	};

	static ListObject getInstance() {
		return new ListObject();
	}

	static ListObject concat(ListObject... lists) {
		ListObject l = new ListObject();
		if (lists != null && lists.length > 0) {
			for (ListObject listObject : lists) {
				l.list.addAll(listObject.list);
			}
		}
		return l;
	}

	static ListObject add(Object... elements) {
		ListObject l = new ListObject();
		if (elements == null) {
			return l;
		}
		l.list.addAll(Arrays.asList(elements));
		return l;
	}

	static int find(Object listObj, Object obj, Object startAfter) {
		if (listObj instanceof ListObject) {

			int init = mFncUtil.integerConverter(startAfter);
			if (init == -1) {
				return 0;
			} else if (init <= -2) {
				throw new IllegalArgumentException(
						"Start less than -1 is not allowed");
			}

			List<Object> list = ((ListObject) listObj).list;
			for (int i = init; i < list.size(); i++) {
				Object item = list.get(i);
				item = mFncUtil.toString(item);
				obj = mFncUtil.toString(obj);
				if (item.equals(obj)) {
					return i + 1;
				}
			}
		}
		return 0;
	}

	public int find(Object obj, Object start) {
		return find(this, obj, start);
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
		if (position == 0) {
			return "";
		}
		if (position < 0) {
			position = list.size() - 1;
		} else {
			position = position - 1;
		}
		return this.list.get(position);
	}

	public Object firstElement() {
		return element(1);
	}

	public int length() {
		int length = list.size();
		if (length == 1) {
			if ("".equals(list.get(0))) {
				return 0;
			}
		}
		return length;
	}

	public String toString() {
		int lastDelimiter = list.size() - 1;
		int count = 0;
		StringBuilder string = new StringBuilder();
		for (Object o : list) {
			string.append(o);
			if (count < lastDelimiter) {
				string.append(DataStructureUtil.DELIMITER);
			}
			count++;
		}
		return string.toString();
	}
	
	static ListObject listFromString(String string, String delimiter) {
		ListObject l = new ListObject();
		if (string.isEmpty()) {
			return l;
		}
		l.list.addAll(Arrays.asList(string.split(Pattern.quote(delimiter))));
		return l;
	}

}
