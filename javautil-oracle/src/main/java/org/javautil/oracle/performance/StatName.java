package org.javautil.oracle.performance;

public class StatName {
	/**
	 * sys.v_$statname.statistic#
	 */
	private int statisticNbr;
	
	/**
	 * Sys.v_$statname.name
	 */
	private String name;
	
	/**
	 * From v_$statname.class
	 */
	private int statisticClass;

	public StatName(int statisticNbr, String name, int statisticClass) {
		this.statisticNbr = statisticNbr;
		this.name = name;
		this.statisticClass = statisticClass;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the statisticClass
	 */
	public int getStatisticClass() {
		return statisticClass;
	}

	/**
	 * @param statisticNbr the statisticNbr to set
	 */
	public int getStatisticNbr() {
		return statisticNbr;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	@Override
	public String toString()
	{
	    final String TAB = " ";
	   
	    String retValue  = "StatName ( "
	        + "name = '" + this.name + "'" + TAB
	        + "statisticClass = '" + this.statisticClass + "'" + TAB
	        + "statisticNbr = '" + this.statisticNbr + "'" + TAB
	        + " )";
	
	    return retValue;
	}
	
	
}
