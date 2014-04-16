package mLibrary;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.regex.Matcher;

import br.com.innovatium.mumps2java.todo.TODO;
import antlr.StringUtils;

public final class mFnc {


	/**
	 * Converts a character to a numeric code.
	 * 
	 * @param expression
	 * @param position
	 * <br/>
	 *            expression -> The character to be converted. <br/>
	 *            position -> Optional — The position of a character within a
	 *            character string, counting from 1. The default is 1.
	 * @return numeric code
	 */
	public static Object $ascii(Object expression, Object position) {
		Double convertedPosition = numberConverter(position);
		return Character.codePointAt(String.valueOf(expression).toCharArray(),
				convertedPosition.intValue() - 1);
	}

	public static Object $ascii(Object expression) {
		return $ascii(expression, 1);
	}

	/**
	 * Converts the integer value of an expression to the corresponding ASCII or
	 * Unicode character.
	 * 
	 * @param i
	 * @return
	 */
	public static Object $c(Object... intArgs) {
		return $char(intArgs);
	}

	/**
	 * Compares expressions and returns the value of the first matching case.
	 * 
	 * @param args
	 *            </br> target - A literal or expression the value of which is
	 *            to be matched against cases. </br> case - A literal or
	 *            expression the value of which is to be matched with the
	 *            results of the evaluation of target. </br> value - The value
	 *            to be returned upon a successful match of the corresponding
	 *            case. </br> default - Optional — The value to be returned if
	 *            no case matches target.
	 * @return Object
	 */
	public static Object $case(Object... args) {
		Boolean hasTrue = false;
		Object returnObj = null;
		if (args != null) {
			if (args.length < 3) {
				throw new IllegalArgumentException(
						"This method requires at least one pair of condition and value that returns as true.");
			}
			String target = String.valueOf(args[0]);
			for (int i = 1; i < args.length; i++) {
				if (i % 2 != 0) {
					if (target.equals(args[i])) {
						hasTrue = true;
						returnObj = args[i + 1];
						break;
					}
				}
			}
			if (!hasTrue) {
				if (args.length % 2 != 0) {
					throw new IllegalArgumentException(
							"This method requires at least one pair of condition and value.");
				} else {
					returnObj = args[args.length - 1];
				}
			}
		}
		return returnObj;
	}

	public static Object $char(Object... codes) {
		if (codes == null || codes.length == 0) {
			return null;
		}
		return characterImpl(castIntArray(codes));
	}

	public static ListObject $concat(ListObject... lists) {
		return ListObject.concat(lists);
	}

	public static boolean $d(mVar var) {
		return booleanConverter($data(var));
	}

	public static int $data(mVar mVar) {
		return mVar.data();
	}

	public static String $e(Object value, Object index) {
		return $extract(value, index);
	}

	public static String $e(Object string, Object from, Object to) {
		return $extract(castString(string), castInt(from), castInt(to));
	}

	public static String $e(Object string) {
		return $extract(string);
	}

	public static String $extract(Object value, Object index) {
		return $extract(value, index, index);
	}

	public static String $extract(Object string, Object from, Object to) {
		return extractImpl(castString(string), castInt(from), castInt(to));
	}

	public static String $extract(Object string) {
		return $extract(string, 1);
	}

	public static int $find(Object string, Object substring) {
		return $find(string, substring, 1);
	}

	public static int $find(Object string, Object substring, Object start) {
		return findImpl(castString(string), castString(substring),
				castInt(start));
	}

	/**
	 * Formats a numeric value with a specified format; optionally rounds to a
	 * specified precision.
	 * 
	 * @param inumber
	 * @param format
	 * @param decimal
	 * @return returns the number specified by inumber in the specified format
	 */

	public static Object $fnumber(Object inumber, String format, Object decimal) {

		DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
		DecimalFormat formatter = new DecimalFormat();
		Double inumberDbl = Double.valueOf(String.valueOf(inumber));

		if (decimal != null) {
			formatter.setMaximumFractionDigits(Integer.valueOf(String
					.valueOf(decimal)));
			formatter.setMinimumFractionDigits(Integer.valueOf(String
					.valueOf(decimal)));			
		} 			
		if (format != null) {
			if (".".contains(format)) {
				dfs.setGroupingSeparator(String.valueOf(format).charAt(0));
				dfs.setDecimalSeparator(',');
			} else if (",".contains(format)) {
				dfs.setGroupingSeparator(String.valueOf(format).charAt(0));
				dfs.setDecimalSeparator('.');
			} else if (String.valueOf(format).equals("T")) {
				formatter.setNegativeSuffix("-");
			} else if (String.valueOf(format).equals("T+")) {
				formatter.setPositiveSuffix("+");
			} else if (String.valueOf(format).equals("P")) {
				formatter.setNegativePrefix("(");
				formatter.setNegativeSuffix(")");
			} else if ("+".equals(format)) {
				if (!(inumberDbl < 0)) {
					formatter.setPositivePrefix("+");
				} else {
					formatter.setNegativePrefix("");
				}
			} else if ("-".equals(format)) {
				if ((inumberDbl < 0)) {
					formatter.setPositivePrefix("+");
				} else {
					formatter.setNegativePrefix("-");
				}
			}

		}

		formatter.setDecimalFormatSymbols(dfs);

		String formatedString = formatter.format(inumberDbl);

		return formatedString;
	}

	public static Object $fnumber(Object inumber, String format) {
		return $fnumber(inumber, format, null);
	}
	
	public static Object $g(mVar pstrClass) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $get(Object content) {
		throw new UnsupportedOperationException();
	}

	public static Object $get(Object content, Object defaultValue) {
		throw new UnsupportedOperationException();
	}

	public static Object $h() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $horolog() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $i(mVar var) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $increment(mVar var) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $increment(mVar var, Object increment) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $io() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static boolean $isNumber(Object numStr) {
		boolean isNumber = true;
		try {
			Double dbl = numberConverter(numStr);
			if (dbl == 0d && !String.valueOf(dbl).equals("0")) {
				isNumber = false;
			}
		} catch (NumberFormatException nfe) {
			isNumber = false;
		}
		return isNumber;
	}

	public static boolean $isNumber2(Object numStr) {
		boolean isNumber = true;
		try {
			if (String.valueOf(numStr).startsWith("0")
					|| String.valueOf(numStr).contains(" ")) {
				isNumber = false;
			} else {
				Double.valueOf(String.valueOf(numStr));
			}
		} catch (NumberFormatException nfe) {
			isNumber = false;
		}
		return isNumber;
	}

	public static Object $isobject(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $job() {
		throw new UnsupportedOperationException();
	}

	public static Object $justify(Object expression, int width, String decimal) {
		if(decimal!=null){
			expression = $fnumber(expression,",",decimal);
		}
		String strFormated = new String(new char[width-expression.toString().length()]).replace("\0", " ").concat(String.valueOf(expression));//.format("%"+"-"+width+"s", String.valueOf(expression));  
		return strFormated;
	}

	public static Object $justify(String expression, int width) {
		return $justify(expression, width, null);
	}

	public static Object $justify(String expression, Object width) {
		int widthInt = numberConverter(width).intValue();
		return $justify(expression, widthInt, null);
	}

	public static ListObject $l(ListObject list, int init, int end) {
		return $list(list, init, end);
	}

	public static Object $l(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static ListObject $lb(Object... elements) {
		return $listbuild(elements);
	}

	public static Object $length(Object $$$inaufInvoiceNumber) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $length(Object object, Object $$$ParentSeparator) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $length(Object object, String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static ListObject $list(ListObject list, int init, int end) {
		return list.sublist(init - 1, end);
	}

	public static ListObject $listbuild(Object... elements) {
		return ListObject.add(elements);
	}

	public static Object $listfind(Object $listbuild, Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $listget(Object object, Object object2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static int $listlength(ListObject list) {
		return list.length();
	}

	public static Object $listlength(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $order(mVar var) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $order(mVar var, boolean negative) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $order(mVar var, Object $get) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $piece(Object string, Object delimiter) {
		return pieceImpl(castString(string), castString(delimiter), 1);
	}

	public static String $piece(Object string, Object delimiter, Object index) {
		return pieceImpl(castString(string), castString(delimiter),
				castInt(index));
	}

	public static Object $piece(Object string, Object delimiter, Object from,
			Object to) {
		return pieceImpl(castString(string), castString(delimiter),
				castInt(from), castInt(to));
	}

	public static Object $qlength(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static ListObject $qsubscript(Object object, Object object2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $query(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $replace(Object object, String oldSubstring,
			String newSubstring) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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

	public static String $reverse(Object $$$inaufInvoiceNumber) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
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

	/**
	 * Returns the value associated with the first true expression.
	 * 
	 * @param args
	 * <br/>
	 *            expression - The select test for the associated value
	 *            parameter. <br/>
	 *            value - The value to be returned if the associated expression
	 *            evaluates to true.
	 * @return Object associated
	 */
	public static Object $select(Object... args) {
		Boolean hasTrue = false;
		Object returnObj = null;
		if (args != null) {
			if (args.length % 2 != 0) {
				throw new IllegalArgumentException(
						"This method requires at least one pair of  condition and value that returns as true.");
			}
			for (int i = 0; i < args.length; i++) {
				if (i % 2 == 0) {
					boolean bool = booleanConverter(args[i]);
					if (bool) {
						hasTrue = bool;
						returnObj = args[i + 1];
						break;
					}
				}
			}
			if (!hasTrue) {
				throw new IllegalArgumentException(
						"This method requires at least one pair of  condition and value that returns as true.");
			}
		}
		return returnObj;
	}
	
	@TODO
	public static Object $test() {
		// TODO Auto-generated method stub
		return 1;
	}

	public static Object $tlevel() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	//
	public static Object $translate(Object $get, Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $translate(Object string, Object oldCharsequence,
			Object newCharsequence) {
		return translateImpl(castString(string), castString(oldCharsequence),
				castString(newCharsequence));
	}

	public static Object $zconvert(Object object, String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zconvert(Object object, String string, String string2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zcrc(Object object, int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zdate(Object... object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zdateh(Object... object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zdatetime(Object... object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zeof() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zhorolog() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zorder(Object object) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zstrip(Object object, String string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zstrip(Object object, String string, Object object2,
			String string2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static String $ztimestamp() {
		throw new UnsupportedOperationException();
	}

	public static ListObject $zu(int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Object $zutil(int i) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public static Boolean booleanConverter(Object obj) {
		Boolean bool = false;
		if (obj instanceof Boolean) {
			bool = Boolean.parseBoolean(String.valueOf(obj));
		} else {
			Double dbl = numberConverter(obj);
			if (dbl != 0) {
				bool = true;
			}
		}
		return bool;
	}

	public static Double castDouble(Object obj) {
		try {
			return (Double) obj;
		} catch (ClassCastException e) {
			return 0d;
		}
	}

	public static Integer castInt(Object obj) {
		try {
			return (Integer) obj;
		} catch (ClassCastException e) {
			return 0;
		}
	}

	public static Integer[] castIntArray(Object... obj) {
		if (obj == null) {
			return null;
		} else if (obj.length == 0) {
			return new Integer[] {};
		}

		final Integer[] array = new Integer[obj.length];
		for (int i = 0; i < obj.length; i++) {
			array[i] = castInt(obj[i]);
		}
		return array;
	}

	public static String castString(Object obj) {
		try {
			return (String) obj;
		} catch (ClassCastException e) {
			return null;
		}
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

	public static Object list(ListObject list) {
		return list.firstElement();
	}

	public static Object list(ListObject list, int position) {
		return list.element(position);
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

	public static Double numberConverter(Object obj) {
		Double dbl = null;
		try {
			dbl = Double.valueOf(String.valueOf(obj));
		} catch (NumberFormatException nfe) {
			String result = "";
			char[] charArray = obj.toString().toCharArray();
			boolean startNumber = false;
			boolean hasPoint = false;
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				if (!startNumber && (c == '+' || c == '-')) {
					if (result.isEmpty()) {
						result = String.valueOf(c);
					} else {
						if (result.equals(String.valueOf(c))) {
							result = "+";
						} else {
							result = "-";
						}
					}
					continue;
				}
				if (Character.isDigit(c)) {
					startNumber = true;
					result = result.concat(String.valueOf(c));
					continue;
				}
				if (c == '.') {
					startNumber = true;
					if (!hasPoint) {
						hasPoint = true;
						result = result.concat(String.valueOf(c));
						continue;
					}
				}
				if (result.isEmpty() || !startNumber) {
					result = "0";
				}
				break;
			}
			dbl = Double.valueOf(result);
		}
		return dbl;
	}

	public static String pieceImpl(String string, String delimiter) {
		return $piece(string, delimiter, 1);
	}

	public static String pieceImpl(String string, String delimiter, int index) {

		if (string == null) {
			return null;
		}

		return string.split(delimiter)[index - 1];
	}

	public static String pieceImpl(String string, String delimiter, int from,
			int to) {
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

	public static String setPieceImpl(String string, String delimiter,
			Integer position, String value) {
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

	private mFnc() {
	}

	public Object $orderx(mData mData) {
		return $order(mData, 1);
	}

	public Object $orderx(mData mData, int direction) {
		return mData.order(direction);
	}

	public Object length(Object string) {
		return length(castString(string));
	}

	public Object length(Object string, Object delimiter) {
		return lengthImpl(castString(string), castString(delimiter));
	}

	public int lengthImpl(String string) {
		if (string == null) {
			return 0;
		}

		return string.length();
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

}
