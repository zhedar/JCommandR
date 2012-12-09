package de.hsl.rinterface.commands;

/**
 * @author Tobias Steinmetzer, Peggy Kübe
 */


import de.hsl.rinterface.objects.RMatrix;
import de.hsl.rinterface.objects.RObject;
import de.hsl.rinterface.objects.RObjectTypes;
import de.hsl.rinterface.objects.RTable;
import de.hsl.rinterface.objects.RVector;

/***********************************************************************
 * Module:  RBinomTest.java
 * Author:  Peggy K�be
 * Purpose: Defines the Class RBinomTest
 ***********************************************************************/
/**
 * binom.test(x, n, p = 0.5, alternative = c("two.sided", "less", "greater"),
 * conf.level = 0.95) x - number of successes, or a vector of length 2 giving
 * the numbers of successes and failures, respectively. n - number of trials;
 * ignored if x has length 2. p - hypothesized probability of success.
 * alternative - indicates the alternative hypothesis and must be one of
 * "two.sided", "greater" or "less". You can specify just the initial letter.
 * conf.level - confidence level for the returned confidence interval.
 * 
 * Beispiel: binom.test(5,20) test<-c(1,2) binom.test(test)
 * @param <T>
 */

public class RBinomTest<T> implements RCommand {

	private RObject x;
	private int n;
	private double p;
	private String alternative;
	private double conf_level;

	public RBinomTest(RObject x, int n) {
		if (n >= 0) {
			switch (x.getType()) {
			case VALUE:
				if (n > 0) {
					this.n=n;
					this.x=x;
				}
				break;
			case MATRIX:
				RMatrix rm = (RMatrix) x;
				if(rm.getColLength()==2 && rm.getRowLength()==1){
					this.n=n;
					this.x=x;
				}
				if(rm.getColLength()==1 && rm.getRowLength()==1 && n>0){
					this.n=n;
					this.x=x;
				}
				break;
			case VECTOR:
				RVector<T> rv = (RVector<T>) x;
				if(rv.size()==2){
					this.x=x;
					this.n=n;
				}
				if(rv.size()==1 && n>0){
					this.x=x;
					this.n=n;
				}
				break;
			case TABLE:
				RTable rt = (RTable) x;
				if(rt.getColLength()==2 && rt.getRowLength()==1){
					this.n=n;
					this.x=x;
				}
				if(rt.getColLength()==1 && rt.getRowLength()==1 && n>0){
					this.n=n;
					this.x=x;
				}
				break;
			default:
				throw new IllegalArgumentException();
			}
		}
	}

	public RObject getX() {
		return x;
	}

	public void setX(RObject x) {
		switch (x.getType()) {
		case VALUE:
			if (n > 0) {
				this.x=x;
			}
			break;
		case MATRIX:
			RMatrix rm = (RMatrix) x;
			if(rm.getColLength()==2 && rm.getRowLength()==1){
				this.x=x;
			}
			if(rm.getColLength()==1 && rm.getRowLength()==1 && n>0){
				this.x=x;
			}
			break;
		case VECTOR:
			RVector<T> rv = (RVector<T>) x;
			if(rv.size()==2){
				this.x=x;
			}
			if(rv.size()==1 && n>0){
				this.x=x;
			}
			break;
		case TABLE:
			RTable rt = (RTable) x;
			if(rt.getColLength()==2 && rt.getRowLength()==1){
				this.x=x;
			}
			if(rt.getColLength()==1 && rt.getRowLength()==1 && n>0){
				this.x=x;
			}
			break;
		default:
			throw new IllegalArgumentException();
		}
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		if(x.getType()==RObjectTypes.VALUE && n<1)
			throw new IllegalArgumentException();
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
		return "binom.test("
				+ x.toRString()
				+ (n != 0 ? ", n = " + n : "")
				+ (p != 0d ? ", p = " + p : "")
				+ (alternative != null ? ", alternative = \"" + alternative
						+ "\"" : "")
				+ (conf_level != 0d ? ", conf.level = " + conf_level : "")
				+ ")";
	}
}