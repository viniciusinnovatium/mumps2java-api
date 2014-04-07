package mLibrary;

import java.util.Arrays;

public final class mFnc {

	private mFnc() {
	}
	
	public static int $data(mVar mVar){
		return mVar.data();
	}
	
	public static Object $select(Object x, Object y) {
		throw new UnsupportedOperationException();
	}
	
	public static Object $select(Object x, Object y, Object Z, Object padrao) {
		throw new UnsupportedOperationException();
	}
	
	public static Object $get(Object content, Object defaultValue){
		throw new UnsupportedOperationException();
	}
	
	public static Object $get(Object content){
		throw new UnsupportedOperationException();
	}
	
	public static Object $job(){
		throw new UnsupportedOperationException();
	}
	
	public static String $zts(){
		return $ztimestamp();
	}  
	
	public static String $ztimestamp(){
		throw new UnsupportedOperationException();
	}  
	
	public Object $order(mData mData) {
		return $order(mData, 1);
	}
	
	public Object $order(mData mData, int direction) {
		return mData.order(direction);
	}

	public static Object $setpiece(Object string, Object delimiter, Object position, Object value) {
		return setPieceImpl(castString(string), castString(delimiter), castInt(position), castString(value));
	}

	public static String setPieceImpl(String string, String delimiter, Integer position, String value) {
		if (string == null || position < 0) {
			return string;
		}

		final String[] array = string.split(delimiter);
		if (value == null) {
			value = "";
		}
		array[position - 1] = value;

		return generateString(array, delimiter);
	}


	public static Object $piece(Object string, Object delimiter, Object from, Object to) {
		return pieceImpl(castString(string), castString(delimiter), castInt(from), castInt(to));
	}
	
	public static String pieceImpl(String string, String delimiter, int from, int to) {
		if (string == null) {
			return null;
		}

		if (from > to) {
			return null;
		}

		final String[] array = Arrays.copyOfRange(string.split(delimiter),
				from - 1, to);

		return generateString(array, delimiter);
	}

	public static String $piece(Object string, Object delimiter, Object index) {
		return pieceImpl(castString(string), castString(delimiter), castInt(index));
	}
	

	public static String pieceImpl(String string, String delimiter, int index) {
		
		if (string == null) {
			return null;
		}

		return string.split(delimiter)[index - 1];
	}

	public static Object $piece(Object string, Object delimiter) {
		return pieceImpl(castString(string), castString(delimiter), 1);
	}
	
	public static String pieceImpl(String string, String delimiter) {
		return $piece(string, delimiter, 1);
	}

	public static String $extract(Object string, Object from, Object to) {
		return extractImpl(castString(string), castInt(from), castInt(to));
	}
	
	public static String $e(Object string, Object from, Object to) {
		return $extract(castString(string), castInt(from), castInt(to));
	}
	
	public static String extractImpl(String string, int from, int to) {
		if (string == null) {
			return null;
		}
		try {
			return string.substring(from - 1, to);
		} catch (Exception e) {
			return null;
		}

	}

	public static String $extract(Object value, Object index) {
		return $extract(value, index, index);
	}
	
	public static String $e(Object value, Object index) {
		return $extract(value, index);
	}

	public static String $extract(String string) {
		return $extract(string, 1);
	}
	
	public static String $e(String string) {
		return $extract(string);
	}
	
	public static int $find(Object string, Object substring, Object start) {
		return findImpl(castString(string), castString(substring), castInt(start));
	}
	
	public static int findImpl(String string, String substring, int start) {
		if (string == null || string.trim().length() == 0 || substring == null
				|| substring.length() == 0) {
			return 0;
		}

		if (start < 1 || start >= string.length()) {
			return 0;
		}

		string = string.substring(start);

		char[] substringChar = substring.toCharArray();
		char[] stringChar = string.toCharArray();

		int j = 0;
		int max = substringChar.length;
		for (int i = 0; i < stringChar.length; i++) {
			if (stringChar[i] == substringChar[j]) {
				j++;
			} else {
				j = 0;
			}

			if (j == max) {
				return ++i + (start + 1);
			}
		}
		return 0;
	}

	public static int $find(Object string, Object substring) {
		return $find(string, substring, 1);
	}

	public Object length(Object string, Object delimiter) {
		return lengthImpl(castString(string), castString(delimiter));
	}
	
	public Object lengthImpl(String string, String delimiter) {
		if (delimiter == null && string == null) {
			return 0;
		}

		if (delimiter != null && string == null) {
			return 1;
		}

		return string.split(delimiter).length;
	}

	public Object length(Object string) {
		return length(castString(string));
	}
	
	public int lengthImpl(String string) {
		if (string == null) {
			return 0;
		}

		return string.length();
	}

	public static ListObject $listbuild(Object... elements) {
		return ListObject.add(elements);
	}
	
	public static ListObject $lb(Object... elements) {
		return $listbuild(elements);
	}

	public static ListObject $list(ListObject list, int init, int end) {
		return list.sublist(init - 1, end);
	}
	
	public static ListObject $l(ListObject list, int init, int end) {
		return $list(list, init, end);
	}

	public static Object list(ListObject list, int position) {
		return list.element(position);
	}

	public static Object list(ListObject list) {
		return list.firstElement();
	}

	public static int $listlength(ListObject list) {
		return list.length();
	}

	public static ListObject $concat(ListObject... lists) {
		return ListObject.concat(lists);
	}

	public static int listData(ListObject list, int position) {
		return hasElementOnListObject(list, position) ? 1 : 0;
	}

	public static Object listGet(ListObject list, int position,
			String defaultValue) {
		if (hasElementOnListObject(list, position)) {
			return list(list, position);
		} else {
			return defaultValue;
		}
	}

	public static String $replace(String string, String oldSubstring,
			String newSubstring) {
		if (string == null) {
			return null;
		}

		if (oldSubstring == null) {
			return string;
		}

		return string.replace(oldSubstring, newSubstring);
	}

	public static Object $translate(Object string, Object oldCharsequence, Object newCharsequence) {
		return translateImpl(castString(string), castString(oldCharsequence), castString(newCharsequence));
	}
	
	public static String translateImpl(String string, String oldCharsequence,
			String newCharsequence) {

		if (string == null || oldCharsequence == null
				|| newCharsequence == null || oldCharsequence.length() == 0) {
			return string;
		}

		/*
		 * Esse mecanismo foi escolhido para evidar de criar muitas strings no
		 * pool e evitar o consumo de memoria excessivo.
		 */
		char[] stringChars = string.toCharArray();
		char[] oldChars = oldCharsequence.toCharArray();
		char[] newChars = newCharsequence.toCharArray();

		// Aqui vamos completar o newChars de substiticao pois quando
		// os tamanhos nao forem identicos isso indicara que os valores
		// excedentes devem ser substituidos pelo caracter "vazio".
		if (newChars.length < oldChars.length) {
			newChars = Arrays.copyOf(newChars, oldChars.length);
		}

		final StringBuilder result = new StringBuilder();
		char copy;
		for (int i = 0; i < stringChars.length; i++) {
			copy = stringChars[i];
			for (int j = 0; j < oldChars.length; j++) {
				if (copy == oldChars[j]) {
					copy = newChars[j];
					break;
				}
			}

			// Caso o caracter seja vazio nao devemos inclui-lo na string
			// gerada.
			if ('\0' != copy) {
				result.append(copy);
			}

		}

		return result.toString();
	}

	public static Object zconvert(Object string, Object mode) {
		return zconvertImpl(castString(string), castString(mode));
	}
	
	public static String zconvertImpl(String string, String mode) {
		if (mode == null || mode.trim().length() == 0) {
			return string;
		}

		if ("L".equalsIgnoreCase(mode)) {
			string = string.toLowerCase();
		} else if ("U".equalsIgnoreCase(mode)) {
			string = string.toUpperCase();
		}
		return string;
	}

	public static Object $char(Object ... codes) {
		if (codes == null || codes.length == 0) {
			return null;
		}
		return characterImpl(castIntArray(codes));
	}

	public static String characterImpl(Integer... codes) {
		if (codes == null) {
			return null;
		}
		
		if (codes.length == 1 && codes[0] == null) {
			return null;
		}

		final Character[] chars = new Character[codes.length];
		for (int i = 0; i < codes.length; i++) {
			// Apenas os inteiros nao-negativos deve ser convertidos.
			if (codes[i] != null && codes[i] > 0) {
				chars[i] = Character.toChars(codes[i])[0];
			}
		}

		return generateString(chars, null, true);
	}

	public static Object $select(BooleanObject... conditions) {
		if (conditions == null || conditions.length == 0) {
			return null;
		}

		Object value = null;
		for (BooleanObject conditional : conditions) {
			if (conditional.isTrue()) {
				value = conditional.getValue();
				break;
			}
		}
		return value;
	}

	public static Integer castInt(Object obj) {
		try {
			return (Integer) obj;
		} catch (ClassCastException e) {
			return 0;
		}
	}
	
	public static Integer[] castIntArray(Object...obj) {
		if (obj == null) {
			return null;
		} else if (obj.length == 0) {
			return new Integer[]{};
		}
		
		final Integer [] array = new Integer[obj.length];
		for (int i = 0; i < obj.length; i++) {
			array[i] = castInt(obj[i]);
		}
		return array;
	}

	public static Double castDouble(Object obj) {
		try {
			return (Double) obj;
		} catch (ClassCastException e) {
			return 0d;
		}
	}

	public static String castString(Object obj) {
		try {
			return (String) obj;
		} catch (ClassCastException e) {
			return null;
		}
	}

	private static String generateString(Object[] array, String delimiter) {
		return generateString(array, delimiter, false);
	}

	private static String generateString(Object[] array, String delimiter,
			boolean avoidNull) {
		final StringBuilder result = new StringBuilder();
		final int indexToInsert = array.length - 1;
		for (int i = 0; i < array.length; i++) {
			if (avoidNull && array[i] == null) {
				continue;
			}
			result.append(array[i]);
			if (delimiter != null && i < indexToInsert) {
				result.append(delimiter);
			}
		}
		return result.toString();
	}

	private static boolean hasElementOnListObject(ListObject list, int position) {
		Object elemet = list(list, position);
		return elemet != null && !"".equals(elemet.toString().trim());
	}

}
