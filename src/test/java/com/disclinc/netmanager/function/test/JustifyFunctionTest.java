package com.disclinc.netmanager.function.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mFnc;

import org.junit.Test;

public class JustifyFunctionTest {
	@Test
	public void testJustify() {

		Object expression = "250.50";
		int width	 = 20;	
		String decimal	 = "3";	
		
		String expected = "                    250.500";
		Object actual = mFnc.$justify(expression, width, decimal);
		
		assertEquals("Fail on justify with three arguments.", expected, actual);
	}

}
