package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mContext;

import org.junit.Test;

public class NewVarBlockVariablesTest {
	private mContext m$ = new mContext();

	@Test
	public void testSimpleExecutingNewVariable() {
		init();

		int firstBlock = 1;
		m$.newVarBlock(firstBlock, m$.var("x"));
		// Checking first time execution newVarBlock operator
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("x").get());
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				2, m$.var("y").get());
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				3, m$.var("z").get());

		m$.newVarBlock(firstBlock, m$.var("x"), m$.var("y"));
		// Checking execution newVarBlock operator twice.
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("x").get());
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("y").get());
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				3, m$.var("z").get());

		m$.newVarBlock(firstBlock, m$.var("x"), m$.var("y"), m$.var("z"));
		// Checking execution newVarBlock operator twice.
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("x").get());
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("y").get());
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("z").get());

	}

	@Test
	public void testExecutionewVarBlockInsideAloop() {
		init();

		int firstBlock = 1;
		for (int i = 0; i < 2; i++) {
			m$.newVarBlock(firstBlock, m$.var("x"));
			// Checking first time execution newVarBlock operator
			assertEquals(
					"This variable was removed through new var operator and must be remove from the tree",
					null, m$.var("x").get());
			assertEquals(
					"This variable was removed through new var operator and must be remove from the tree",
					2, m$.var("y").get());
			assertEquals(
					"This variable was removed through new var operator and must be remove from the tree",
					3, m$.var("z").get());
		}
	}

	@Test
	public void testSimpleExecutionRestorenewVarBlock() {
		init();

		int firstBlock = 1;
		m$.newVarBlock(firstBlock, m$.var("x"), m$.var("y"), m$.var("z"));
		// Checking first time execution newVarBlock operator
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("x").get());
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("y").get());
		assertEquals(
				"This variable was removed through new var operator and must be remove from the tree",
				null, m$.var("z").get());

		m$.restoreVarBlock(firstBlock);
		// Checking restore variables after first time execution newVarBlock
		// operator
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				1, m$.var("x").get());
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				2, m$.var("y").get());
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				3, m$.var("z").get());

	}

	@Test
	public void testExecutionRestorenewVarBlockAfterALoop() {
		init();

		int firstBlock = 1;
		// Executing newVarBlock into a loop
		for (int i = 0; i < 2; i++) {
			m$.newVarBlock(firstBlock, m$.var("x"));
			// Checking first time execution newVarBlock operator
			assertEquals(
					"This variable was removed through new var operator and must be remove from the tree",
					null, m$.var("x").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					2, m$.var("y").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					3, m$.var("z").get());

		}

		m$.restoreVarBlock(firstBlock);
		// Checking restore variables after first time execution newVarBlock
		// operator
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				1, m$.var("x").get());
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				2, m$.var("y").get());
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				3, m$.var("z").get());

	}

	@Test
	public void testExecutionRestorenewVarBlockAfterNestedLoops() {
		init();

		int firstBlock = 1;
		int secondBlock = 2;
		// Executing newVarBlock into a loop
		for (int i = 0; i < 2; i++) {
			m$.newVarBlock(firstBlock, m$.var("x"));
			// Checking first time execution newVarBlock operator
			assertEquals(
					"This variable was removed through new var operator and must be remove from the tree",
					null, m$.var("x").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					2, m$.var("y").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					3, m$.var("z").get());

			// Setting new value to variable x which will be test into nested
			// loop
			m$.var("x").set(55);
			m$.var("w").set(66);
			for (int j = 0; j < 2; j++) {
				m$.newVarBlock(secondBlock, m$.var("w"));
				// Checking first time execution newVarBlock operator
				assertEquals(
						"This variable was not removed through new var operator and must be into the tree",
						55, m$.var("x").get());
				assertEquals(
						"This variable was not removed through new var operator and must be into the tree",
						2, m$.var("y").get());
				assertEquals(
						"This variable was not removed through new var operator and must be into the tree",
						3, m$.var("z").get());

				assertEquals(
						"This variable was removed through new var operator and must not be into the tree",
						null, m$.var("w").get());

				// Setting some value to be test in next iteration.
				m$.var("w").set(j);
			}
			m$.restoreVarBlock(secondBlock);
			// Checking restores execution newVarBlock operator of the nested
			// loop.
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					55, m$.var("x").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					2, m$.var("y").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					3, m$.var("z").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					66, m$.var("w").get());

		}

		m$.restoreVarBlock(firstBlock);
		// Checking restore variables after first time execution newVarBlock
		// operator
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				1, m$.var("x").get());
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				2, m$.var("y").get());
		assertEquals(
				"This variable was restore through restore var operator and must be into the tree and its value have to be the same as before new var block execution",
				3, m$.var("z").get());

	}

	@Test
	public void testExecutionNewVarBlockInARandomOrder() {
		init();
		int firsBlock = 1;
		int secondBlock = 2;
		// Setting new value to variable x which will be test into nested
		// loop
		m$.var("x").set(55);
		m$.var("w").set(66);
		for (int j = 0; j < 2; j++) {
			m$.newVarBlock(secondBlock, m$.var("w"));
			// Checking first time execution newVarBlock operator
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					55, m$.var("x").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					2, m$.var("y").get());
			assertEquals(
					"This variable was not removed through new var operator and must be into the tree",
					3, m$.var("z").get());

			assertEquals(
					"This variable was removed through new var operator and must not be into the tree",
					null, m$.var("w").get());

			// Setting some value to be test in next iteration.
			m$.var("w").set(j);
		}
		m$.restoreVarBlock(secondBlock);

		// Executing another newVarBlock called as firstBlock after the
		// secondBlock, because it could be generate by the mumps2java code
		// generator.
		m$.newVarBlock(firsBlock, m$.var("x"), m$.var("y"), m$.var("z"),
				m$.var("w"));
		assertEquals(
				"This variable was removed through new var operator and must not be into the tree",
				null, m$.var("x").get());
		assertEquals(
				"This variable was removed through new var operator and must not be into the tree",
				null, m$.var("y").get());
		assertEquals(
				"This variable was removed through new var operator and must not be into the tree",
				null, m$.var("z").get());
		assertEquals(
				"This variable was removed through new var operator and must not be into the tree",
				null, m$.var("w").get());
		
		// Setting a new value to variable x which will no be present into the tree after restore variables.
		m$.var("x").set(888);
		
		// Restoring variables of the first block.
		m$.restoreVarBlock(firsBlock);
		assertEquals(
				"This variable was not removed through new var operator and must be into the tree",
				55, m$.var("x").get());
		assertEquals(
				"This variable was not removed through new var operator and must be into the tree",
				2, m$.var("y").get());
		assertEquals(
				"This variable was not removed through new var operator and must be into the tree",
				3, m$.var("z").get());

		assertEquals(
				"This variable was removed through new var operator and must not be into the tree",
				66, m$.var("w").get());

	}

	private void init() {
		m$.var("x").set(1);
		m$.var("x", "1").set(11);
		m$.var("y").set(2);
		m$.var("y", "1").set(21);
		m$.var("z").set(3);
	}
}
