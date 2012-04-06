package com.dbexperts.oracle;

import java.util.ArrayList;

import com.dbexperts.oracle.dto.OracleSessionStat;

public class OracleSessionStatSet {
	private int sid = -1;
	
	private double[] statArray;
	
	private static final String newline = System.getProperty("line.separator");

	private OracleSessionStatSet() {
		
	}
	public OracleSessionStatSet(ArrayList <OracleSessionStat> stats) {

		int maxStatNbr = 0;
		if (stats == null) {
			throw new IllegalArgumentException("stats is null");
		}
		if (stats.size() > 0) {
			sid = stats.get(0).getSid();
		}
		/*
		 * Save some space
		 */
		for (int i = 0; i < stats.size(); i++) {
			OracleSessionStat stat = stats.get(i);
			int statNbr = stat.getStatisticNbr();
			if (statNbr > maxStatNbr) {
				maxStatNbr = statNbr;
			}	
		}
		statArray = new double[maxStatNbr + 1];
//		for (int i = 0; i <= maxStatNbr; i++) {
//			statArray[i] = Double.NaN;
//		}
		for (int i = 0 ; i < stats.size(); i++) {
			OracleSessionStat stat = stats.get(i);
			if (stat.getSid() != sid) {
				throw new IllegalArgumentException("different sids found");
			}
			statArray[stat.getStatisticNbr()] = stat.getValue();
		}
	}
	
	public double getValue(int statNbr) {
		
		double retval = 0;
		if (statNbr < statArray.length) {
			retval = statArray[statNbr];
		}
		return retval;
	}
	
	public String asString(StatNames names) {
		StringBuilder b= new StringBuilder();
		for (int i = 0; i < statArray.length ; i++) {
			if (statArray[i] != 0) {
				b.append(i);
				b.append(" '" );
				b.append(names.getNameOfStatNumber(i));
				b.append("' ");
				b.append(statArray[i]);
				b.append(newline);
			}
		}
		return b.toString();
	}
	
	public OracleSessionStatSet getDelta(OracleSessionStatSet other) {
		OracleSessionStatSet newSet = new OracleSessionStatSet();
		int newSize = other.statArray.length > statArray.length ? other.statArray.length : statArray.length;
		newSet.statArray = new double[newSize];
		for (int i = 0; i < statArray.length && i < other.statArray.length; i++) {
			double valueOther = other.statArray[i];
			double valueThis = statArray[i];
			newSet.statArray[i] = valueThis - valueOther;
		}
	
		return newSet;
	}
}
