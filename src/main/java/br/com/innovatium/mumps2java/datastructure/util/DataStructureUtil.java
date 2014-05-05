package br.com.innovatium.mumps2java.datastructure.util;

public final class DataStructureUtil {
	public final static String DELIMITER = "~";

	private DataStructureUtil() {
	}

	public static String generateKey(Object... subs) {
		return generateKey(false, subs);
	}

	public static String generateKey(boolean isDiskAccess, Object... subs) {
		if (subs == null || subs.length == 0) {
			return null;
		}

		final StringBuilder builder = new StringBuilder();
		int index = subs.length - 1;
		for (int i = isDiskAccess ? 1 : 0; i < subs.length; i++) {
			builder.append(subs[i]);
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
