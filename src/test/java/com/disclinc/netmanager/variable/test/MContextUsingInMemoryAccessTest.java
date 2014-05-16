package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mContext;
import mLibrary.mVar;

import org.junit.Before;
import org.junit.Test;

public class MContextUsingInMemoryAccessTest {
	private mContext m$;

	@Before
	public void init() {
		m$ = new mContext();
	}

	@Test
	public void testSettingVariableValueInTwoSteps() {
		// Setting value in two steps
		mVar pedido = m$.var("pedido");
		pedido.var("item").set(33);
		assertEquals(33, m$.var("pedido", "item").get());
	}

	@Test
	public void testSettingVariableValueInSingleStep() {
		// Setting value in single step
		m$.var("carro", "esportivo").set(66);
		assertEquals(66, m$.var("carro", "esportivo").get());
	}
	
	@Test
	public void testLocalVariableAccess() {
		m$.var("x").set("local");
		assertEquals("local", m$.var("x").get());
	}
	
	@Test
	public void testPublicVariableAccess() {
		m$.var("%x").set("publica");
		assertEquals("publica", m$.var("%x").get());
	}
	
	@Test
	public void testLastVarOperator() {
		m$.var("^y", "1", "2", "3").set(7);
		m$.lastVar("7", "8").set(76);
		assertEquals("This subscripts was merge by last var operator", 76, m$.var("%y", "1", "2", "7", "8").get());
	}
	
	@Test
	public void testLastVarOperatorOnTwoSteps() {
		mVar var = m$.var("%y", "1", "2", "3");
		var.set(7);
		var = var.lastVar("7", "8");
		var.set(76);
		assertEquals("This subscripts was merge by last var operator", 76, m$.var("%y", "1", "2", "7", "8").get());
	}
	
}
