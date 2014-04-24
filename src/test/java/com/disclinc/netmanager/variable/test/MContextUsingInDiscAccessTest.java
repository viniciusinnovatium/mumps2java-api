package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mContext;
import mLibrary.mGlobal;
import mLibrary.mVar;

import org.junit.Before;
import org.junit.Test;

import br.com.innovatium.mumps2java.cachemanager.CacheType;

public class MContextUsingInDiscAccessTest {
	private mContext m$;

	@Before
	public void init() {
		m$ = new mContext(new mGlobal(CacheType.JDBC));
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
	public void testSearchNodeAtSameLevelThroughOrderFunction() {
		m$.var("^www001", "pai", "filho1", "neto1").set("1");
		m$.var("^www001", "pai", "filho1", "neto166").set("1");
		m$.var("^www001", "pai", "filho2", "neto2").set("12");
		m$.var("^www001", "pai", "filho3", "equipamento1").set("13");
		
		mVar item = m$.var("^www001", "pai", "filho1");
		assertEquals("Fail to find the next node on the tree through order function", "filho2", item.order());
		assertEquals("Fail to find the next node on the tree through order function", "filho3", item.order());
		assertEquals("Fail to find the next node on the tree through order function", "", item.order());
	}
}
