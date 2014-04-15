package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mContext;
import mLibrary.mLocal;

import org.junit.Test;

public class MContextVariableTest {

	private mContext m$ = null;

	@Test
	public void testStackingVariableThroughNewOperator() {
		stackingVariables();

		assertEquals(
				"After calling new operator variables reinitialized should be removed from the tree",
				null, m$.var("pedido").get());

		assertEquals(
				"After calling new operator should not such variables reinitialized should be removed from the tree",
				null, m$.var("pedido", "item").get());

		assertEquals(
				"After calling new operator should not such variables reinitialized should be removed from the tree",
				null, m$.var("pedido", "item", "medicamento").get());

		assertEquals(
				"After calling new operator should not such variables reinitialized should be removed from the tree",
				"XSA-3322", m$.var("contrato").get());

		assertEquals(
				"After calling new operator should not such variables reinitialized should be removed from the tree",
				"mm77", m$.var("contrato", "clausula").get());
	}

	@Test
	public void testStakingVariableAndSettingNewValue() {
		init();

		assertEquals("Fail on recovering value from the tree", "11",
				m$.var("pedido").get());

		m$.newvar("pedido");

		assertEquals(
				"After calling new operator should not such variables reinitialized should be removed from the tree",
				null, m$.var("pedido", "item").get());

		m$.var("pedido").set("VALOR EMPILHADO");
		assertEquals(
				"After calling new operator should not such variables reinitialized should be removed from the tree",
				"VALOR EMPILHADO", m$.var("pedido").get());

	}

	@Test
	public void testUnstackingVariableThroughOldOperator() {
		stackingVariables();

		assertEquals(
				"After calling new operator should not such variables reinitialized should be removed from the tree",
				null, m$.var("pedido").get());

		unstackingVariables();

		assertEquals(
				"Fail on recovering value from the tree after unstacking variable after calling old operator",
				"11", m$.var("pedido").get());

		assertEquals(
				"After calling old operator variables should be reinsert into the tree",
				"100", m$.var("pedido", "item", "medicamento").get());

		assertEquals(
				"Fail on recovering value from the tree after unstacking variable after calling old operator",
				"XSA-3322", m$.var("contrato").get());

		assertEquals(
				"Fail on recovering value from the tree after unstacking variable after calling old operator",
				"mm77", m$.var("contrato", "clausula").get());

	}

	@Test
	public void testStackingVariableThroughMultipleNewOperatorCalling() {
		init();
		m$.newvar("pedido");
		m$.var("pedido").set("pilha1");

		assertEquals(
				"Fail on recovering value from the tree after stacking variable through calling new operator first time",
				"pilha1", m$.var("pedido").get());
		
		assertEquals(
				"Fail on recovering value from the tree after stacking variable through calling new operator first time, because he was not stacked",
				"XSA-3322", m$.var("contrato").get());
		
		m$.newvar("pedido");
		m$.var("pedido").set("pilha2");

		assertEquals(
				"Fail on recovering value from the tree after stacking variable through calling new operator twice",
				"pilha2", m$.var("pedido").get());
		
		assertEquals(
				"Fail on recovering value from the tree after stacking variable through calling new operator twice, because he was not stacked",
				"XSA-3322", m$.var("contrato").get());
		
		unstackingVariables();
		assertEquals(
				"Fail on recovering value from the tree after stacking variable through calling old operator first time. He should get value from de stack node",
				"pilha1", m$.var("pedido").get());
		
		assertEquals(
				"Fail on recovering value from the tree after stacking variable through calling new operator twice, because he was not stacked",
				"XSA-3322", m$.var("contrato").get());
		
		unstackingVariables();
		assertEquals(
				"Fail on recovering value from the tree after stacking variable through calling old operator first time. He should get value from de stack node",
				"11", m$.var("pedido").get());
		
		assertEquals(
				"Fail on recovering value from the tree after stacking variable through calling new operator twice, because he was not stacked",
				"XSA-3322", m$.var("contrato").get());
	}

	private void init() {
		m$ = new mContext(new mLocal());
		m$.var("pedido").set("11");
		m$.var("pedido", "item").set("99");
		m$.var("pedido", "item", "medicamento").set("100");
		m$.var("contrato").set("XSA-3322");
		m$.var("contrato", "clausula").set("mm77");
	}

	private void stackingVariables() {
		init();
		m$.newvar("pedido");
	}

	private void unstackingVariables() {
		m$.oldvar("pedido");
	}
}
