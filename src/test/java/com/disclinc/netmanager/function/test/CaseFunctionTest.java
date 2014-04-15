package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.$select;
import static org.junit.Assert.assertEquals;
import mLibrary.BooleanObject;
import mLibrary.mFnc;

import org.junit.Test;

public class CaseFunctionTest {

	@Test
	public void testSelectSomeValidConditional() {
		Object target = "4";
		
		Object bool1 = "1";
		Object var1 = "amarelo";
		Object bool2 = "2";
		Object var2 = "vermelho";
		Object bool3 = "3";
		Object var3 = "azul";		
		
		String expected = "azul";
		Object actual = mFnc.$case(target, bool1, var1, bool2, var2, bool3, var3);
		
		assertEquals("Fail on case a valid condition", expected, actual);
	}

}
