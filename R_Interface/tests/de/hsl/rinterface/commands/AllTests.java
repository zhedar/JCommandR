package de.hsl.rinterface.commands;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import de.hsl.rinterface.ConsoleConnectionTest;
import de.hsl.rinterface.ParseMatrixTest;
import de.hsl.rinterface.TestSchablone;
import de.hsl.rinterface.objects.RMatrixTest;
import de.hsl.rinterface.objects.RTableTest;

@RunWith(Suite.class)
@SuiteClasses({ RBinomTestTest.class, RCorTest.class, RCovTest.class,
		RMeanTest.class, RMedianTest.class, RPlotTest.class,
		RQuantileTest.class, RSDTest.class, RSummaryTest.class,
		RTTestTest.class, RVarTest.class,  RMatrixTest.class, RTableTest.class, ConsoleConnectionTest.class, ParseMatrixTest.class })
public class AllTests {

}
