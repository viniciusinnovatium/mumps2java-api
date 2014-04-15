package com.disclinc.netmanager.function.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CharacterFunctionTest.class, ExtractFunctionTest.class,
		FindFunctionTest.class, ListBuildFunctionTest.class,
		PieceFunctionTest.class, ReplaceFunctionTest.class,
		SelectFunctionTest.class })
public class FunctionAPISuitTests {

}
