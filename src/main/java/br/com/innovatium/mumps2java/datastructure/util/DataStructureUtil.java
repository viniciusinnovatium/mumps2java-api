package br.com.innovatium.mumps2java.datastructure.util;

import mLibrary.mFncUtil;

public final class DataStructureUtil {
	public final static String DELIMITER = "~";
	public static final int PUBLIC = 1;
	public static final int GLOBAL = 2;
	public static final int LOCAL = 3;

	private DataStructureUtil() {
	}

	public static boolean isGlobal(Object[] subs) {
		return GLOBAL == getVariableType(subs);
	}

	public static boolean isLocal(Object[] subs) {
		return LOCAL == getVariableType(subs);
	}

	public static boolean isPublic(Object[] subs) {
		return PUBLIC == getVariableType(subs);
	}
	
	public static boolean isGlobal(String name) {
		return GLOBAL == getVariableType(name);
	}

	public static boolean isLocal(String name) {
		return LOCAL == getVariableType(name);
	}

	public static boolean isPublic(String name) {
		return PUBLIC == getVariableType(name);
	}

	public static int getVariableType(Object[] subs) {
		return getVariableType(subs[0].toString());
	}

	private static int getVariableType(String name) {
		final boolean isEmpty = name.length() == 0;
		if (!isEmpty && '%' == name.charAt(0)) {
			return PUBLIC;
		} else if (!isEmpty && '^' == name.charAt(0)) {
			return GLOBAL;
		} else {
			return LOCAL;
		}
	}

	public static Object[] concat(Object[] first, Object[] second) {
		return concat(first, second, first.length, 0);
	}

	public static Object[] concatSinceLast(Object[] first, Object[] second) {
		return concat(first, second, first.length - 1, 0);
	}

	public static Object[] concat(Object[] dest, Object orig) {
		return concat(dest, new Object[] { orig });
	}

	public static String generateKey(Object[] subs) {
		int delimiterOccurece = subs.length - 1;
		return generateKey(0, subs.length, delimiterOccurece, subs);
	}

	public static String generateKeyOfParent(Object[] subs) {
		int end = subs.length - 1;
		int delimiterOccurece = end - 1;
		return generateKey(0, end, delimiterOccurece, subs);
	}

	public static String generateKeyWithoutVarName(Object[] subs) {
		int delimiterOccurece = subs.length - 2;
		if (subs.length == 1) {
			return " ";
		}
		return generateKey(1, subs.length, delimiterOccurece, subs);
	}

	public static String generateKeyToLikeQuery(Object[] subs) {
		// Like query must remove the first subscript which means the table name
		// and remove the last subscript to fetch all children of that key.
		int delimiterOccurece = subs.length - 1;
		return generateKey(1, subs.length - 1, delimiterOccurece, subs);
	}

	private static String generateKey(int start, int end,
			int delimiterOccurence, Object... subs) {
		if (subs == null || subs.length == 0) {
			return " ";
		}

		final StringBuilder builder = new StringBuilder();

		for (int i = start; i < end; i++) {
			builder.append(mFncUtil.toString(subs[i]));
			if (delimiterOccurence-- > 0) {
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

	public static Object[] generateSubs(String tableName, String key) {
		return generateSubs(new StringBuilder("^").append(tableName)
				.append(DELIMITER).append(key).toString());
	}

	public static String generateTableName(Object... subs) {
		return subs[0].toString().replace("^", "");
	}

	private static Object[] concat(Object[] first, Object[] second,
			int lastIndexOfFirst, int startIndexOfSecond) {
		Object[] copy = new Object[lastIndexOfFirst + second.length];
		for (int i = 0; i < lastIndexOfFirst; i++) {
			copy[i] = first[i];
		}

		for (int i = startIndexOfSecond; i < second.length; i++) {
			copy[i + lastIndexOfFirst] = second[i];
		}
		return copy;
	}
}
