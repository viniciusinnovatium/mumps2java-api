package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.*;
import static org.junit.Assert.*;
import mLibrary.mFnc;

import org.junit.Test;

public class IsNumberFunctionTest {

	@Test
	public void testFormatInteger() {
		Object obj = "12345";
		boolean actual = mFnc.$isNumber(obj);
		boolean expected = true;
		assertEquals("Fail to generate isNumber from a string",expected , actual);
	}
	
	@Test
	public void testFormatDecimal() {
		Object obj = "49.95";
		boolean actual = mFnc.$isNumber(obj);
		boolean expected = true;
		assertEquals("Fail to generate isNumber from a string",expected , actual);
	}	
	
	@Test
	public void testFormatAlpha() {
		Object obj = "-TEST";
		boolean actual = mFnc.$isNumber(obj);
		boolean expected = false;
		assertEquals("Fail to generate isNumber from a string",expected , actual);
	}		
	
	@Test
	public void testFormatAlphaNum() {
		Object obj = "TESTE123.5";
		boolean actual = mFnc.$isNumber(obj);
		boolean expected = false;
		assertEquals("Fail to generate isNumber from a string",expected , actual);
	}			
	
	@Test
	public void testFormatNumAlpha() {
		Object obj = "34.3ABCD";
		boolean actual = mFnc.$isNumber(obj);
		boolean expected = true;
		assertEquals("Fail to generate isNumber from a string",expected , actual);
	}		

	@Test
	public void testFormatDoublePoints() {
		Object obj = "34.34.666";
		boolean actual = mFnc.$isNumber(obj);
		boolean expected = true;
		assertEquals("Fail to generate isNumber from a string",expected , actual);
	}			
	
	@Test
	public void testFormatSign() {
		Object obj = "+-+-++---+34.34xxx";
		boolean actual = mFnc.$isNumber(obj);
		boolean expected = true;
		assertEquals("Fail to generate isNumber from a string",expected , actual);
	}	
	
	@Test
	public void testFormatStartWithZero() {
		Object obj = "0000034.34xxx";
		boolean actual = mFnc.$isNumber(obj);
		boolean expected = true;
		assertEquals("Fail to generate isNumber from a string",expected , actual);
	}			
}
