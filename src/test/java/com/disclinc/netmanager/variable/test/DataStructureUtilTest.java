package com.disclinc.netmanager.variable.test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;

import br.com.innovatium.mumps2java.datastructure.util.DataStructureUtil;

public class DataStructureUtilTest {
	private Object[] start = new Object[] { 1, 2, 3, 4 };
	private Object[] end = new Object[] { 5, 6, 7, 8, 9, 0 };
	private Object[] query = new Object[] { "^www001", "x", "1", "2", "3" };

	@Test
	public void testConcat() {
		Object[] concat = DataStructureUtil.concat(start, end);
		Object[] result = new Object[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 0 };
		assertTrue("All elements must have the same value",
				Arrays.equals(result, concat));

	}

	@Test
	public void testConcatLastElement() {
		Object[] concat = DataStructureUtil.concat(start, 88);
		Object[] result = new Object[] { 1, 2, 3, 4, 88 };
		assertTrue("All elements must have the same value",
				Arrays.equals(result, concat));
	}

	@Test
	public void testConcatSinceLastElement() {
		Object[] concat = DataStructureUtil.concatSinceLast(start, end);
		Object[] result = new Object[] { 1, 2, 3, 5, 6, 7, 8, 9, 0 };
		assertTrue("All elements must have the same value",
				Arrays.equals(result, concat));
	}

	@Test
	public void testGenerateKeyOfTheSubscripts() {
		assertEquals("^www001~x~1~2~3", DataStructureUtil.generateKey(query));
	}

	@Test
	public void testGenerateKeyOfParentSubscripts() {
		assertEquals("^www001~x~1~2",
				DataStructureUtil.generateKeyOfParent(query));
	}

	@Test
	public void testGenerateKeyToLikeQuery() {
		assertEquals("x~1~2~", DataStructureUtil.generateKeyToLikeQuery(query));
	}

	@Test
	public void testVariableTypes() {
		Object[] x = new Object[] { "%x", 2 };
		Object[] y = new Object[] { "^y", 3 };
		Object[] z = new Object[] { "z", 4 };
		Object[] w = new Object[] { "", 5 };

		assertEquals("This mustbe a public variable", 1,
				DataStructureUtil.getVariableType(x));
		assertEquals("This mustbe a global variable", 2,
				DataStructureUtil.getVariableType(y));
		assertEquals("This mustbe a local variable", 3,
				DataStructureUtil.getVariableType(z));
		assertEquals("This mustbe a local variable", 3,
				DataStructureUtil.getVariableType(w));

	}

}
