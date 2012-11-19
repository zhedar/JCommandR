package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RBinomTest.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RBinomTest
 ***********************************************************************/
/*
 * binom.test(x, n, p = 0.5,
           alternative = c("two.sided", "less", "greater"),
           conf.level = 0.95)
 * x - number of successes, or a vector of length 2 giving the numbers of successes and failures, respectively.
 * n - number of trials; ignored if x has length 2.
 * p - hypothesized probability of success.
 * alternative - indicates the alternative hypothesis and must be one of "two.sided", "greater" or "less". You can specify just the initial letter.
 * conf.level - confidence level for the returned confidence interval.
 */
// binom.test(5,20)
// test<-c(1,2) binom.test(test)
public class RBinomTest implements RCommand {
	
	private RObject x;
	private int n;
	private double p;
	private String alternative;
	private double conf_level;
	
	
	public RBinomTest(RObject x) {
		this.x = x;
	}

	
	public RObject getX() {
		return x;
	}


	public void setX(RObject x) {
		this.x = x;
	}


	public int getN() {
		return n;
	}


	public void setN(int n) {
		this.n = n;
	}


	public double getP() {
		return p;
	}


	public void setP(double p) {
		this.p = p;
	}


	public String getAlternative() {
		return alternative;
	}


	public void setAlternative(String alternative) {
		this.alternative = alternative;
	}


	public double getConf_level() {
		return conf_level;
	}


	public void setConf_level(double conf_level) {
		this.conf_level = conf_level;
	}


	@Override
	public String prepareForSending() {
		return "binom.test(" + x.toRString() + 
				(n != 0 ? ", n = " +n : "")+
				(p !=0d ? ", p = " + p : "")+
				(alternative!=null ? ", alternative = \"" + alternative+ "\"" : "")+
				(conf_level !=0d ? ", conf.level = " + conf_level : "")
				 +")";
	}

}
