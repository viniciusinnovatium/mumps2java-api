package br.com.innovatium.mumps2java.datastructure;

import java.util.Arrays;

import br.com.innovatium.mumps2java.todo.TODO;

/*
 * This class should be removed soon. Some analysis has appointed it is can be replaced 
 * to array objects on calling functions. It does not make sense anymore because we already abstract it 
 * employing another abstract layers.
 */
@TODO
public class Path {
	private final Object[] subs;

	public Path(Object... subs) {
		if (subs == null || subs.length == 0) {
			throw new IllegalArgumentException(
					"Subscripts does not accepts null or empty parameters");
		}

		this.subs = subs;
	}

	public Object[] toArray() {
		return subs;
	}

	public Object get(int index) {
		return subs[index];
	}

	public Object getSubscript() {
		return get(subs.length - 1);
	}

	public int length() {
		return subs.length;
	}

	public boolean isEmpty() {
		return subs.length == 0;
	}

	public boolean isLastEmpty() {
		Object last = subs[subs.length - 1];
		return last == null || last.toString().toCharArray().length == 0;
	}

	public Path generate(int length) {
		return new Path(Arrays.copyOf(subs, length));
	}

	@Override
	public String toString() {
		return Arrays.deepToString(subs);
	}
}
