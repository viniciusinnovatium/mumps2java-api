package com.disclinc.netmanager.function.test;

import mLibrary.ListObject;
import static mLibrary.mFnc.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class ListLengthFunctionTest {
	@Test
	public void testSimpleLength() {
		ListObject list = $listbuild("x", "y", "z");
		assertEquals(3, $listlength(list));
	}
	
	@Test
	public void testNestedListLength() {
		ListObject list = $listbuild("x", "y", "z");
		list = $listbuild(list, "w", "z");
		assertEquals(3, $listlength(list));
	}
	
	@Test
	public void testEmptyListLength() {
		ListObject list = $listbuild("");
		assertEquals(0, $listlength(list));
	}
	
	@Test
	public void testNullListLength() {
		ListObject list = $listbuild((Object[])null);
		assertEquals(0, $listlength(list));
	}
}
