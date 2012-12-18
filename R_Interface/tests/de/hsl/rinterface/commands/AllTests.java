package de.hsl.rinterface.commands;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ RBinomTestTest.class, RCorTest.class, RCovTest.class,
		RMeanTest.class, RMedianTest.class, RPlotTest.class,
		RQuantileTest.class, RSDTest.class, RSummaryTest.class,
		RTTestTest.class, RVarTest.class })
public class AllTests {

}
