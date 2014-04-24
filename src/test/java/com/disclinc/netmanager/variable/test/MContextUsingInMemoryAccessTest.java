package com.disclinc.netmanager.variable.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import mLibrary.mContext;
import mLibrary.mLocal;
import mLibrary.mVar;

public class MContextUsingInMemoryAccessTest {
	private mContext m$;

	@Before
	public void init() {
		m$ = new mContext(new mLocal());
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
}
