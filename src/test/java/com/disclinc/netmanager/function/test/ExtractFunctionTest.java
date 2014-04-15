package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class ExtractFunctionTest {

	private final String stringTest = "1234Alabama567";

	@Test
	public void testExtractDefault() {
		assertEquals("Fail to extract string as default value", "1",  $extract(stringTest));
	}
	
	@Test
	public void testExtractSomeElement() {
		assertEquals("Fail to extract string as default value", "A",  $extract(stringTest, 5));
	}
	
	@Test
	public void testExtractIntervalElement() {
		assertEquals("Fail to extract string as default value", "Alabama",  $extract(stringTest, 5, 11));
	}
	
	@Test
	public void testNonPresenteElement() {
		assertEquals("Fail to extract string a non present element", null,  $extract(stringTest, 50));
	}
}
