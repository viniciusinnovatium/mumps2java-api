package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mContext;

import org.junit.Before;
import org.junit.Test;

public class StackingVariableAndNewOperatorTest {
	private mContext m$ = null;

	@Before
	public void init() {
		m$ = new mContext();
		m$.var("pedido").set("11");
		m$.var("pedido", "item").set("99");
		m$.var("pedido", "item", "medicamento").set("100");
		m$.var("contrato").set("cod1");
		m$.var("contrato", "clausula").set("mm77");
		m$.var("item").set("63");
		m$.var("item", "detergente").set("43x");
		m$.var("item", "detergente", "neutro").set("9");
		m$.var("plastico").set("18");
		m$.var("eletronico").set("92");
	}

	private void recoverStackedVariablesValues() {
		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("pedido").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("item").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"92", m$.var("eletronico").get());

		assertEquals(
				"This variable was not removed from the tree, therefore, its value must be the same",
				"18", m$.var("plastico").get());

		assertEquals(
				"This variable was not removed from the tree, therefore, its value must be the same",
				null, m$.var("contrato").get());

	}

	private void stackingExceptionMultipleVariables() {
		m$.newVarExcept(m$.var("pedido"), m$.var("item"), m$.var("eletronico"));
	}

	private void stackingExceptionVariables() {
		m$.newVarExcept(m$.var("pedido"));
	}

	private void stackingMultipleVariables() {
		m$.newVar(m$.var("pedido"), m$.var("item"), m$.var("contrato"));
	}

	private void stackingVariables() {
		m$.newVar(m$.var("pedido"));
	}

	public void testStackingMultipleVariableThroughNewExceptionsOperatorCalling() {

		stackingExceptionMultipleVariables();

		assertEquals(
				"This variable was not removed from the tree after calling new exception operator, therefore, its value must be the same",
				"11", m$.var("pedido").get());
		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				"cod1", m$.var("contrato").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				"63", m$.var("item").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("plastico").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("eletronico").get());
	}

	@Test
	public void testStackingMultipleVariableThroughNewOperatorCalling() {

		stackingMultipleVariables();
		recoverStackedVariablesValues();
	}

	@Test
	public void testStackingMultipleVariableThroughNewOperatorMultipleCalling() {

		// Stacking variables first time
		stackingMultipleVariables();
		recoverStackedVariablesValues();

		// Inserting new variable values to the first time calling of the new
		// operator
		m$.var("pedido").set("pedido1");
		m$.var("item").set("item1");
		m$.var("eletronico").set("eletronico1");

		// Stacking variables second time. This code block must be the same as
		// before.
		stackingMultipleVariables();
		recoverStackedVariablesValues();

		m$.var("pedido").set("pedido2");
		m$.var("item").set("item2");
		m$.var("eletronico").set("eletronico2");

		// Recovering variables values from the second stack level.
		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"pedido2", m$.var("pedido").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"item2", m$.var("item").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"eletronico2", m$.var("eletronico").get());

		assertEquals(
				"This variable was not removed from the tree, therefore, its value must be the same",
				"18", m$.var("plastico").get());

		assertEquals(
				"This variable was not removed from the tree, therefore, its value must be the same",
				"cod1", m$.var("contrato").get());

		// Recovering variables values from the first stack level.
		unstackingVariables();
		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"pedido1", m$.var("pedido").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"item1", m$.var("item").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"eletronico1", m$.var("eletronico").get());

		assertEquals(
				"This variable was not removed from the tree, therefore, its value must be the same",
				"18", m$.var("plastico").get());

		assertEquals(
				"This variable was not removed from the tree, therefore, its value must be the same",
				"cod1", m$.var("contrato").get());

		// Recovering variables values from the default stack level, so, they
		// have to provide the first setting value.
		unstackingVariables();
		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"11", m$.var("pedido").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"63", m$.var("item").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"92", m$.var("eletronico").get());

		assertEquals(
				"This variable was not removed from the tree, therefore, its value must be the same",
				"18", m$.var("plastico").get());

		assertEquals(
				"This variable was not removed from the tree, therefore, its value must be the same",
				"cod1", m$.var("contrato").get());
	}

	@Test
	public void testStackingVariableThroughNewExceptionsOperatorCalling() {

		stackingExceptionVariables();
		assertEquals(
				"This variable was not removed from the tree after calling new exception operator, therefore, its value must be the same",
				"11", m$.var("pedido").get());
		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("contrato").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("item").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("plastico").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("eletronico").get());
	}

	@Test
	public void testStackingVariableThroughNewOperator() {
		stackingVariables();
		
		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("pedido").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("pedido", "item").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("pedido", "item", "medicamento").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"cod1", m$.var("contrato").get());

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"mm77", m$.var("contrato", "clausula").get());
	}

	@Test
	public void testStackingVariableThroughNewOperatorMultipleCalling() {

		stackingVariables();

		assertEquals(
				"This variable was removed from the tree after stacking variable through calling new operator first time",
				null, m$.var("pedido").get());

		m$.var("pedido").set("pilha1");
		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling new operator first time",
				"pilha1", m$.var("pedido").get());

		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling new operator first time, because he was not stacked",
				"cod1", m$.var("contrato").get());

		stackingVariables();

		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling new operator twice",
				null, m$.var("pedido").get());

		m$.var("pedido").set("pilha2");

		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling new operator twice",
				"pilha2", m$.var("pedido").get());

		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling new operator twice, because he was not stacked",
				"cod1", m$.var("contrato").get());

		unstackingVariables();

		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling old operator first time. He should get value from de stack node",
				"pilha1", m$.var("pedido").get());

		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling new operator twice, because he was not stacked",
				"cod1", m$.var("contrato").get());

		unstackingVariables();
		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling old operator first time. He should get value from de stack node",
				"11", m$.var("pedido").get());

		assertEquals(
				"Fail to recover value from the tree after stacking variable through calling new operator twice, because he was not stacked",
				"cod1", m$.var("contrato").get());
	}

	@Test
	public void testStakingVariableAndSettingNewValue() {

		assertEquals("Fail to recover value from the tree", "11",
				m$.var("pedido").get());

		stackingVariables();

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("pedido", "item").get());

		m$.var("pedido").set("VALOR EMPILHADO");
		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				"VALOR EMPILHADO", m$.var("pedido").get());

	}

	@Test
	public void testUnstackingMultipleVariableThroughNewExceptionsOperatorCalling() {

		stackingExceptionMultipleVariables();

		assertEquals(
				"This variable was not removed from the tree after calling new exception operator, therefore, its value must be the same",
				"11", m$.var("pedido").get());
		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("contrato").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				"63", m$.var("item").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("plastico").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				"92", m$.var("eletronico").get());

		unstackingVariables();

		assertEquals(
				"This variable was not removed from the tree after calling new exception operator, therefore, its value must be the same",
				"11", m$.var("pedido").get());
		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				"cod1", m$.var("contrato").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				"63", m$.var("item").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				"18", m$.var("plastico").get());

		assertEquals(
				"After calling new exception operator, variables should be removed from the tree, therefore, they have not be present into that",
				"92", m$.var("eletronico").get());
	}

	@Test
	public void testUnstackingVariableThroughOldOperator() {

		stackingVariables();

		assertEquals(
				"After calling new operator variables should be removed from the tree, therefore, they have not be present into that",
				null, m$.var("pedido").get());

		unstackingVariables();

		assertEquals(
				"Fail to recover value from the tree after unstacking variable after calling old operator",
				"11", m$.var("pedido").get());

		assertEquals(
				"After calling old operator variables should be reinsert into the tree",
				"100", m$.var("pedido", "item", "medicamento").get());

		assertEquals(
				"Fail to recover value from the tree after unstacking variable after calling old operator",
				"cod1", m$.var("contrato").get());

		assertEquals(
				"Fail to recover value from the tree after unstacking variable after calling old operator",
				"mm77", m$.var("contrato", "clausula").get());

	}

	private void unstackingVariables() {
		m$.oldvar(1);
	}
}
