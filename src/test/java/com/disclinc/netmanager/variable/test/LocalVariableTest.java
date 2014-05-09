package com.disclinc.netmanager.variable.test;

import static org.junit.Assert.assertEquals;
import mLibrary.mData;

import org.junit.Test;

public class LocalVariableTest {

	private mData mData = new mData();
	
	public LocalVariableTest () {
		mData.subs("moto").set(88);
		mData.subs("carro").set(12);
		mData.subs("carro", "esportivo").set(6);
		mData.subs("carro", "esportivo", "amarelo").set(2);
		mData.subs("carro", "esportivo", "azul").set(4);
		mData.subs("carro", "esportivo", "branco").set(14);
		mData.subs("carro", "esportivo", "preto").set(4);
		mData.subs("carro", "passeio").set(8);
	}
	 
	@Test
	public void testSearchingVariableValue() {
		assertEquals("Fail on searching local variable value", 88, mData.get("moto"));
		assertEquals("Fail on searching local variable value", 12, mData.get("carro"));
		assertEquals("Fail on searching local variable value", 6, mData.get("carro", "esportivo"));
		assertEquals("Fail on searching local variable value", 2, mData.get("carro", "esportivo", "amarelo"));
		assertEquals("Fail on searching local variable value", 8, mData.get("carro", "passeio"));
	}

	@Test
	public void testOrderFirstLevel() {
		assertEquals("Fail on first navegation through the first level", "carro", mData.order(""));
		
		assertEquals("Fail on first navegation through the first level", "esportivo", mData.order("carro", ""));
		assertEquals("Fail on first navegation through the first level", "passeio", mData.order("carro", "esportivo"));
		assertEquals("Fail on first navegation through the first level", "passeio", mData.order("carro", "esportivo"));
		assertEquals("Fail on second navegation through the first level", "", mData.order("carro", "passeio"));
		
		assertEquals("Fail on first navegation through the first level", "azul", mData.order("carro", "esportivo", "amarelo"));
		assertEquals("Fail on first navegation through the first level", "branco", mData.order("carro", "esportivo", "azul"));
		assertEquals("Fail on first navegation through the first level", "preto", mData.order("carro", "esportivo", "branco"));
		assertEquals("Fail on first navegation through the first level", "", mData.order("carro", "esportivo", "preto"));
		
	}
	
	@Test
	public void testOrderFirstLevelInverse() {
		assertEquals("Fail on first navegation through the first level", "carro", mData.order(new Object[]{""}, -1));
		
		assertEquals("Fail on first navegation through the first level", "passeio", mData.order(new Object[]{"carro", ""}, -1));
		assertEquals("Fail on first navegation through the first level", "esportivo", mData.order(new Object[]{"carro", "passeio"}, -1));
		assertEquals("Fail on first navegation through the first level", "", mData.order(new Object[]{"carro", "esportivo"}, -1));
	}
}
