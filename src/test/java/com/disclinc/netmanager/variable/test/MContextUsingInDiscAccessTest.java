package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import mLibrary.mContext;
import mLibrary.mVar;

import org.junit.Before;
import org.junit.Test;

public class MContextUsingInDiscAccessTest {
	private mContext m$;

	@Before
	public void init() {
		m$ = new mContext();
	}

	@Test
	public void testSettingVariableValueInSingleStep() {
		// Setting value in two steps
		m$.var("^www001", "t1", "t11").set("ainda teste");
		assertEquals("ainda teste", m$.var("^www001", "t1", "t11").get());
	}

	@Test
	public void testSettingVariableValueInTwoSteps() {
		// Setting value in two steps
		mVar pedido = m$.var("^www001", "pedido");
		pedido = pedido.var("cliente");
		pedido.set("54");

		assertEquals("54", m$.var("^www001", "pedido", "cliente").get());
	}

	@Test
	public void testLastVarOperationOnSingleStep() {
		m$.var("^www001", "x", "1").lastVar("3", "4").set(4);
		assertEquals("This variable was merged through last var operation and should be present into database", 4, m$.var("^www001", "x", "3", "4").get());
	}
	
	@Test
	public void testLastVarOperationOnTwoSteps() {
		mVar x = m$.var("^www001", "x", "1");
		mVar y = x.lastVar("3", "4");
		y.set(4);
		
		assertEquals("This variable was merged through last var operation and should be present into database", 4, m$.var("^www001", "x", "3", "4").get());
		assertEquals("This variable was merged through last var operation and should be present into database", 4, m$.var("^www001", "x", "3", "4").get());
	}
	
	@Test
	public void testLastVarOperationOnTreeSteps() {
		mVar x = m$.var("^www001", "x", "1");
		mVar y = x.lastVar("3", "4");
		y.set(4);
		
		assertEquals("This variable was merged through last var operation and should be present into database", 4, m$.var("^www001", "x", "3", "4").get());
	}
	
	@Test
	public void testLastVarOperationWithoutMVar() {
		// Initializing database variable to be merged.
		m$.var("^www001", "x", "3", "4").set(3);
		m$.var("^www001", "x", "1").set(1);
		
		assertEquals("This variable was merged through last var operation and should be present into database", 3, m$.lastVar("3", "4").get());
	}
	
	@Test
	public void testLastVarExceptionsPublicVariables() {
		boolean hasException = false;
		try {
			// Creating public variable
			m$.var("%w", "x", "1").lastVar("3", "4").set(3);
		} catch (UnsupportedOperationException e) {
			hasException = true;
		}
		assertTrue(
				"The public variables does not supports the last var operation",
				hasException);
	}

	@Test
	public void testLastVarExceptionsLocalVariables() {
		boolean hasException = false;
		try {
			// Creating local variable
			m$.var("w", "x", "1").lastVar("3", "4").set(3);
		} catch (UnsupportedOperationException e) {
			hasException = true;
		}
		assertTrue(
				"The local variables does not supports the last var operation",
				hasException);
	}

}
