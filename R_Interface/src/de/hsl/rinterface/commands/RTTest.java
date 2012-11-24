package de.hsl.rinterface.commands;

import de.hsl.rinterface.objects.RObject;

/***********************************************************************
 * Module:  RTTest.java
 * Author:  Peggy Kübe
 * Purpose: Defines the Class RTTest
 ***********************************************************************/
/** t.test(x, y = NULL,
       alternative = c("two.sided", "less", "greater"),
       mu = 0, paired = FALSE, var.equal = FALSE,
       conf.level = 0.95, ...)
 * x - a (non-empty) numeric vector of data values.
 * y - an optional (non-empty) numeric vector of data values.
 * alternative - a character string specifying the alternative hypothesis,
 * must be one of "two.sided" (default), "greater" or "less". You can specify just the initial letter.
 * mu - a number indicating the true value of the mean (or difference in means if you are performing a two sample test).
 * paired - a logical indicating whether you want a paired t-test.
 * var.equal - a logical variable indicating whether to treat the two variances as being equal.
 * If TRUE then the pooled variance is used to estimate the variance otherwise the Welch (or Satterthwaite) approximation to the degrees of freedom is used.
 * conf.level - confidence level of the interval.
 * 
 * Beispiel:
 * t.test(test)
 */
public class RTTest implements RCommand{

	private RObject x;
	private RObject y;
	private String alternative;
	private boolean mu;
	private String paired;
	private String var_equal;
	private double conf_level;
	
	public RTTest(RObject x) {
		this.x = x;
	}


	public RObject getX() {
		return x;
	}


	public void setX(RObject x) {
		this.x = x;
	}


	public RObject getY() {
		return y;
	}


	public void setY(RObject y) {
		this.y = y;
	}


	public String getAlternative() {
		return alternative;
	}


	public void setAlternative(String alternative) {
		this.alternative = alternative;
	}


	public boolean getMu() {
		return mu;
	}


	public void setMu(boolean mu) {
		this.mu = mu;
	}
	
	public String getPaired() {
		return paired;
	}


	public void setPaired(String paired) {
		this.paired = paired;
	}


	public String getVar_equal() {
		return var_equal;
	}


	public void setVar_equal(String var_equal) {
		this.var_equal = var_equal;
	}


	public double getConf_level() {
		return conf_level;
	}


	public void setConf_level(double conf_level) {
		this.conf_level = conf_level;
	}


	@Override
	public String prepareForSending() {
		return "t.test(" + x.toRString() + 
				(y !=null ? ", y = " + y.toRString() : "")+
				(alternative!=null ? ", alternative = \"" + alternative+ "\"" : "")+
				(mu !=false ? ", mu = " + mu : "")+
				(paired !=null ? ", paired = \"" + paired+ "\"" : "")+
				(var_equal !=null ? ", var.equal = \"" + var_equal+ "\"" : "")+
				(conf_level !=0d ? ", conf.level = " + conf_level : "")
				 +")";
	}

}
