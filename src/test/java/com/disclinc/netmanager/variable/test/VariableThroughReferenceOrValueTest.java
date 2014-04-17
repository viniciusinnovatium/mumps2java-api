package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.*;
import mLibrary.mContext;
import mLibrary.mLocal;
import mLibrary.mVar;

import org.junit.Before;
import org.junit.Test;

public class VariableThroughReferenceOrValueTest {
	private mContext m$ = null;

	@Before
	public void init() {
		m$ = new mContext(new mLocal());
		m$.var("pedido").set(55);
	}

	@Test
	public void testUsageVariableThroughReferece() {
		mVar var = m$.var("pedido");
		mVar other = m$.newVarRef("pedido", var);
		assertEquals(
				"This method have to return the same object when used a wrapper variable as second parameter",
				var, other);
	}

	@Test
	public void testUsageVariableThroughValue() {
		mVar var = m$.var("pedido");
		mVar other = m$.newVarRef("pedido", "cod1");
		assertEquals(
				"This method have to return the same object when used a wrapper variable as second parameter",
				"cod1", var.get());
	}
}
