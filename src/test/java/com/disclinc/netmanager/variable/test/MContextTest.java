package com.disclinc.netmanager.variable.test;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import mLibrary.mContext;
import mLibrary.mLocal;
import mLibrary.mVar;

public class MContextTest {
	private mContext m$;
	
	@Before
	public void init() {
		m$ = new mContext(new mLocal());
		mVar pedido = m$.var("pedido");
		pedido.var("item").set(33);
		
		m$.var("carro", "esportivo").set(66);
	}
	
	@Test
	public void test(){
		assertEquals(33, m$.var("pedido", "item").get());
	}
	
	@Test
	public void test2(){
		assertEquals(66, m$.var("carro", "esportivo").get());
	}
}
