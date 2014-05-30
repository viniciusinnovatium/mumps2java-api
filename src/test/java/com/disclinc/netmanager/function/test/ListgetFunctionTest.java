package com.disclinc.netmanager.function.test;

import static mLibrary.mFnc.$listbuild;
import static mLibrary.mFnc.$listget;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import mLibrary.ListObject;

import org.junit.Test;

public class ListgetFunctionTest {
	@Test
	public void testListgetWithoutIndex() {
		ListObject list = $listbuild("x", "y", "z");
		assertEquals("x", $listget(list));
	}

	@Test
	public void testListgetWithIndex() {
		ListObject list = $listbuild("x", "y", "z");
		assertEquals("x", $listget(list, 1));
		assertEquals("y", $listget(list, 2));
		assertEquals("z", $listget(list, 3));
	}

	@Test
	public void testListgetWithNegativeIndex() {
		ListObject list = $listbuild("x", "y", "z");
		assertEquals(
				"Negative index must returns the last element of the list",
				"z", $listget(list, -1));
		assertEquals(
				"Negative index must returns the last element of the list",
				"z", $listget(list, -2));
		assertEquals(
				"Negative index must returns the last element of the list",
				"z", $listget(list, -3));
	}

	@Test
	public void testListgetWithIndexLargerThanListSize() {
		ListObject list = $listbuild("x", "y", "z");
		assertEquals("Index larger than list size must returns empty string",
				"", $listget(list, 5));
	}

	@Test
	public void testListgetWithUndefinedElement() {
		ListObject list = $listbuild("x", null, "z");
		assertEquals(
				"Index pointing to undefined element must returns empty string",
				"", $listget(list, 2));
	}

	@Test
	public void testListgetWithUndefinedElementReturningDefaultValue() {
		ListObject list = $listbuild("x", null, "z");
		assertEquals(
				"Default value must be returned when element is undefined",
				"teste", $listget(list, 2, "teste"));
	}

	@Test
	public void testListgetWithIndexLargerAndDefaultValue() {
		ListObject list = $listbuild("x", null, "z");
		assertEquals(
				"Default value must be returned when index is larger thant list size",
				"teste", $listget(list, 20, "teste"));
	}

	@Test
	public void testListgetWithNegativeIndexAndDefaultValue() {
		ListObject list = $listbuild("x", null, "z");
		assertEquals(
				"The last element must be returned when index is negative",
				"z", $listget(list, -1, "teste"));
	}

	@Test
	public void testListgetWithNullDefaultValue() {
		ListObject list = $listbuild("x", null, "z");
		boolean throwed = false;
		try {
			$listget(list, -1, null);
		} catch (IllegalArgumentException e) {
			throwed = true;
		}
		assertTrue("Default value is always required", throwed);
	}

	@Test
	public void testListgetZeroIndex() {
		ListObject list = $listbuild("x", "y", "z");
		assertEquals("Zero index must be returned empty string", "",
				$listget(list, 0));
	}

	@Test
	public void testListgetEmptyList() {
		ListObject list = $listbuild("");
		assertEquals("Empty list must be returned empty string always", "",
				$listget(list));
	}

	@Test
	public void testListgetEmptyListAndPositiveIndex() {
		ListObject list = $listbuild("");
		assertEquals("Empty list must be returned empty string always", "",
				$listget(list, 2));
	}

	@Test
	public void testListgetEmptyListAndNegativeIndex() {
		ListObject list = $listbuild("");
		assertEquals("Empty list must be returned empty string always", "",
				$listget(list, -2));
	}
}
