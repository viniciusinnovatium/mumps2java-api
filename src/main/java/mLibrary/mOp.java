package mLibrary;

public final class mOp {
	private mOp() {
	}

	/*
	 * public static Object Select(Object x, Object y) { throw new
	 * UnsupportedOperationException(); }
	 */
	public static boolean Not(Object obj) {
		return !Logical(obj);
	}

	public static Object Positive(Object num) {
		if (num == null) {
			return null;
		}
		double d1 = mFncUtil.numberConverter(num);
		return d1;
	}

	public static Object Negative(Object num) {
		if (num == null) {
			return null;
		}
		double d1 = mFncUtil.numberConverter(num);
		d1 = d1 * -1;
		return d1;
	}

	public static boolean Logical(Object object) {
		return mFncUtil.booleanConverter(object);
	}

	public static boolean Equal(Object x, Object y) {
		if (x instanceof Double && (((Double) x) % 1 == 0)) {
			x = ((Double) x).longValue();
		}
		if (y instanceof Double && (((Double) y) % 1 == 0)) {
			y = ((Double) y).longValue();
		}
		if (x instanceof Boolean) {
			x = ((Boolean) x) ? 1 : 0;
		}
		if (y instanceof Boolean) {
			y = ((Boolean) y) ? 1 : 0;
		}
		if (String.valueOf(x).equals(String.valueOf(y))) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean NotEqual(Object x, Object y) {
		return !Equal(x, y);
	}

	public static Object Concat(Object...strings) {
		Object result = null;
		for (Object var : strings) {
			if (result==null){
				result = var;
			} else {
				result = Concat(result,var);
			}
		}
		return result;
	}
	public static Object Concat(Object string1, Object string2) {
		if (string1 instanceof ListObject) {
			ListObject lo1 = (ListObject) string1;
			ListObject lo2 = null;
			if (string2 == null || string2.toString().isEmpty()) {
				return lo1;
			} else if (string2 instanceof ListObject) {
				lo2 = mFnc.$listbuild(string2);
				return ListObject.concat(lo1, lo2);
			}
		} else if (string2 instanceof ListObject) {
			ListObject lo2 = (ListObject) string2;
			ListObject lo1 = null;
			if (string1 == null || string1.toString().isEmpty()) {
				return lo2;
			} else if (string1 instanceof ListObject) {
				lo1 = mFnc.$listbuild(string1);
				return ListObject.concat(lo1, lo2);
			}
		}
		return mFncUtil.toString(string1).concat(mFncUtil.toString(string2));

	}

	public static boolean SortsAfter(Object object, Object object2) {

		String var1 = String.valueOf(object);
		String var2 = String.valueOf(object2);
		boolean isNumVar1 = mFnc.$isvalidnum(var1);
		boolean isNumVar2 = mFnc.$isvalidnum(var2);

		boolean isAfter = false;

		if (isNumVar1 && isNumVar2) {
			isAfter = Double.parseDouble(var1) > Double.parseDouble(var2);
		} else if (isNumVar1 && !isNumVar2) {
			isAfter = false;
		} else if (!isNumVar1 && isNumVar2) {
			isAfter = true;
		} else {
			isAfter = var1.compareTo(var2) > 0;
		}
		return isAfter;
	}

	public static boolean Greater(Object object, Object object2) {
		return mFncUtil.numberConverter(object) > mFncUtil
				.numberConverter(object2);

	}

	public static Object Subtract(Object num1, Object num2) {
		double d1 = mFncUtil.numberConverter(num1);
		double d2 = mFncUtil.numberConverter(num2);
		return (d1 - d2);
	}

	public static Object Divide(Object num1, Object num2) {
		return IntegerDivide(num1, num2);
	}

	public static boolean GreaterOrEqual(Object object, Object object2) {
		return mFncUtil.numberConverter(object) >= mFncUtil
				.numberConverter(object2);
	}

	public static boolean LessOrEqual(Object object, Object object2) {
		return mFncUtil.numberConverter(object) <= mFncUtil
				.numberConverter(object2);
	}

	public static boolean Contains(Object str1, Object str2) {
		return String.valueOf(str1).contains(String.valueOf(str2));
	}

	public static Object Multiply(Object num1, Object num2) {
		double d1 = mFncUtil.numberConverter(num1);
		double d2 = mFncUtil.numberConverter(num2);
		return (d1 * d2);
	}

	public static Object Add(Object num1, Object num2) {
		double d1 = mFncUtil.numberConverter(num1);
		double d2 = mFncUtil.numberConverter(num2);
		return (d1 + d2);
	}

	public static Object Modulus(Object num1, Object num2) {
		double d1 = mFncUtil.numberConverter(num1);
		double d2 = mFncUtil.numberConverter(num2);
		return (d1 % d2);
	}

	public static Object IntegerDivide(Object num1, Object num2) {
		double d1 = mFncUtil.numberConverter(num1);
		double d2 = mFncUtil.numberConverter(num2);
		return Double.valueOf(d1 / d2).intValue();
	}

	public static boolean Less(Object object, Object object2) {
		return mFncUtil.numberConverter(object) < mFncUtil
				.numberConverter(object2);
	}

	public static Boolean Match(Object string, Object regex) {
		String regexJava;
		regexJava = mRegExConverter.convertPattern(String.valueOf(regex));
		return String.valueOf(string).matches(regexJava);
	}

	public static boolean NotLess(Object object, Object object2) {
		return !Less(object, object2);
	}

	public static boolean NotGreater(Object object, Object object2) {
		return !Less(object, object2);
	}

	public static boolean Or(Object x, Object y) {
		boolean a = mFncUtil.booleanConverter(x);
		boolean b = mFncUtil.booleanConverter(y);
		return a | b;
	}

	public static boolean NotContains(Object str1, Object str2) {
		return !Contains(str1, str2);
	}

	public static boolean And(Object x, Object y) {
		boolean a = mFncUtil.booleanConverter(x);
		boolean b = mFncUtil.booleanConverter(y);
		return a & b;
	}

}
