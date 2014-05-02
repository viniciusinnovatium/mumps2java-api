package br.com.innovatium.mumps2java.datastructure.util;

public final class DataStructureUtil {
	public final static String DELIMITER = "~";

	private DataStructureUtil() {
	}

	public static String generateKey(Object... subs) {
		if (subs == null || subs.length == 0) {
			return null;
		}

		final StringBuilder builder = new StringBuilder();
		int index = subs.length - 1;
		for (Object obj : subs) {
			builder.append(obj);
			if (index-- > 0) {
				builder.append(DELIMITER);
			}

		}
		return builder.toString();
	}

	public static Object[] generateSubs(String key) {
		if (key == null) {
			return null;
		}
		return key.split(DELIMITER);
	}
}
