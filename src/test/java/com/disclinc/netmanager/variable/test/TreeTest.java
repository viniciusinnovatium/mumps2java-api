package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import br.com.innovatium.mumps2java.datastructure.Tree;

public class TreeTest {
	private Tree tree;
	Object[] x = new Object[] { "x" };
	Object[] y = new Object[] { "y" };
	Object[] z = new Object[] { "z" };
	Object[] contrato = new Object[] { "contrato" };
	Object[] carro = new Object[] { "carro" };
	Object[] _10 = new Object[] { "10" };

	@Before
	public void init() {

		tree = new Tree();
		tree.set(new Object[] { "1" }, 1);
		tree.set(new Object[] { "10" }, 10);
		tree.set(new Object[] { "2" }, 2);
		tree.set(new Object[] { "carro" }, 65);
		tree.set(new Object[] { "carro", "esportivo" }, 99);
		tree.set(new Object[] { "carro", "esportivo", "amarelo" }, 76);
		tree.set(new Object[] { "contrato" }, 66);
		tree.set(new Object[] { "contrato", "transporte" }, 123);
		tree.set(new Object[] { "contrato", "transporte", "publico" }, 88);
		tree.set(new Object[] { "contrato", "uu", "publico" }, 62);
		tree.set(new Object[] { "", "teste" }, 88);
		tree.set(new Object[] { "" }, "sou vazio");

		tree.set(x, 1);
		tree.set(y, 2);
		tree.set(z, 3);

	}

	@Test
	public void testRecoveringVariable() {
		assertEquals(65, tree.get(new Object[] { "carro" }));
		assertEquals(99, tree.get(new Object[] { "carro", "esportivo" }));
		assertEquals(76,
				tree.get(new Object[] { "carro", "esportivo", "amarelo" }));

	}

	@Test
	public void testRemoveNodes() {
		tree.kill("carro");
		assertEquals(
				"The node should not be into the tree after killing node method",
				null, tree.get("carro"));
		assertEquals(
				"The node should not be into the tree after killing node method",
				null, tree.get("carro", "esportivo"));
		assertEquals(
				"The node should not be into the tree after killing node method",
				null, tree.get("carro", "esportivo", "amarelo"));
		assertEquals(
				"The node should be into the tree because he was not removed from the tree",
				66, tree.get("contrato"));
		assertEquals(
				"The node should be into the tree because he was not removed from the tree",
				123, tree.get("contrato", "transporte"));
		assertEquals(
				"The node should be into the tree because he was not removed from the tree",
				88, tree.get("contrato", "transporte", "publico"));
	}

	@Test
	public void testFindPrevious() {
		System.out.println(tree.dump());

		testWithContrato();

		// Remove contrato to test previous without it
		tree.kill(contrato);

		testWithouContrato();

		// Including into the tree again
		tree.set(contrato, "novo valor");

		tree.stacking(contrato);
		testWithouContrato();

		tree.set(contrato, "contrato stack 1");
		testWithContrato();

		// Checking the new value defined.
		assertEquals("contrato stack 1", tree.get(contrato));

		tree.stackingBlock(1, contrato);
		testWithouContrato();
		// Here the contrato must be undefined
		assertEquals(null, tree.get(contrato));

		// Setting new value
		tree.set(contrato, "contrato block 1");

		tree.stackingBlock(2, contrato);
		testWithouContrato();
		// Here the contrato must be undefined again
		assertEquals(null, tree.get(contrato));

		tree.set(contrato, "contrato block 2");
		testWithContrato();
		// Checking the contrato value of block 2
		assertEquals("contrato block 2", tree.get(contrato));

		// Unstacking contrato from the block 2
		tree.unstackingBlock(2);
		testWithContrato();
		// Checking the contrato value of block 1 was done before block 2
		assertEquals("contrato block 1", tree.get(contrato));

		// Unstacking contrato from the block 1
		tree.unstackingBlock(1);
		testWithContrato();
		// Checking the contrato value of stack 1
		assertEquals("contrato stack 1", tree.get(contrato));

		// Checking the contrato value before stack 1
		tree.unstacking();
		testWithContrato();
		// Checking the contrato value before stack 1
		assertEquals("novo valor", tree.get(contrato));

		// Unstacking some undefined block index and befores this action all
		// variable values must remain the same
		tree.unstackingBlock(4);
		testWithContrato();
		// Checking the contrato value before stack 1
		assertEquals("novo valor", tree.get(contrato));
		assertEquals(65, tree.get(carro));
	}

	private void testWithContrato() {
		assertTrue(Arrays.equals(contrato, tree.findNext(carro)));
		assertTrue(Arrays.equals(x, tree.findNext(contrato)));
		assertTrue(Arrays.equals(y, tree.findNext(x)));
		assertTrue(Arrays.equals(z, tree.findNext(y)));
		assertTrue(Arrays.equals(null, tree.findNext(z)));

		assertTrue(Arrays.equals(_10, tree.findPrevious(carro)));
		assertTrue(Arrays.equals(carro, tree.findPrevious(contrato)));
		assertTrue(Arrays.equals(contrato, tree.findPrevious(x)));
		assertTrue(Arrays.equals(x, tree.findPrevious(y)));
		assertTrue(Arrays.equals(y, tree.findPrevious(z)));

	}

	private void testWithouContrato() {
		assertTrue(Arrays.equals(x, tree.findNext(carro)));
		assertTrue(Arrays.equals(y, tree.findNext(x)));
		assertTrue(Arrays.equals(z, tree.findNext(y)));
		assertTrue(Arrays.equals(null, tree.findNext(z)));

		assertTrue(Arrays.equals(_10, tree.findPrevious(carro)));
		assertTrue(Arrays.equals(carro, tree.findPrevious(x)));
		assertTrue(Arrays.equals(x, tree.findPrevious(y)));
		assertTrue(Arrays.equals(y, tree.findPrevious(z)));
	}
}
