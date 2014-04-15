package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.*;
import mLibrary.mLocal;

import org.junit.Test;

public class LocalVariableTest {

	private mLocal mLocal = new mLocal();
	
	public LocalVariableTest () {
		mLocal.subs("moto").set(88);
		mLocal.subs("carro").set(12);
		mLocal.subs("carro", "esportivo").set(6);
		mLocal.subs("carro", "passeio").set(8);
		mLocal.subs("carro", "esportivo", "amarelo").set(2);
		mLocal.subs("carro", "esportivo", "azul").set(4);
		mLocal.subs("carro", "esportivo", "branco").set(14);
		mLocal.subs("carro", "esportivo", "preto").set(4);
	}
	 
	@Test
	public void testSearchingVariableValue() {
		assertEquals("Fail on searching local variable value", 88, mLocal.get("moto"));
		assertEquals("Fail on searching local variable value", 12, mLocal.get("carro"));
		assertEquals("Fail on searching local variable value", 6, mLocal.get("carro", "esportivo"));
		assertEquals("Fail on searching local variable value", 2, mLocal.get("carro", "esportivo", "amarelo"));
		assertEquals("Fail on searching local variable value", 8, mLocal.get("carro", "passeio"));
	}

	@Test
	public void testOrderFirstLevel() {
		assertEquals("Fail on first navegation through the first level", "passeio", mLocal.subs("carro", "esportivo").order(1));
		assertEquals("Fail on second navegation through the first level", null, mLocal.subs("carro", "passeio").order(1));
		
		assertEquals("Fail on first navegation through the first level", "azul", mLocal.subs("carro", "esportivo", "amarelo").order(1));
		assertEquals("Fail on first navegation through the first level", "branco", mLocal.subs("carro", "esportivo", "azul").order(1));
		assertEquals("Fail on first navegation through the first level", "preto", mLocal.subs("carro", "esportivo", "branco").order(1));
		assertEquals("Fail on first navegation through the first level", null, mLocal.subs("carro", "esportivo", "preto").order(1));
	}
}
