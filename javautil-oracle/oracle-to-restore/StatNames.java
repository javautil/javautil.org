package com.dbexperts.oracle;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeMap;

import com.dbexperts.oracle.performance.StatName;

public class StatNames implements Iterable<StatName> {
	private TreeMap<Integer, StatName> byStatNumber = new TreeMap<Integer,StatName>();
	
	public StatNames(TreeMap<Integer,StatName> stats) {
		this.byStatNumber = stats;
	}
	
	
	
	public StatNames(Collection<StatName> statNames) {
		for (StatName stat : statNames) {
			byStatNumber.put(stat.getStatisticNbr(), stat);
		}
	}
	
	public String getNameOfStatNumber(Integer statNumber) {
		if (statNumber == null) {
			throw new IllegalArgumentException("statNumber is null");
		}
		StatName sn = byStatNumber.get(statNumber);
		if (sn == null) {
			throw new IllegalArgumentException("no such statNumber " + statNumber);
		}
		return sn.getName();
	}
	
	public StatName getStatName(Integer statNumber) {
		if (statNumber == null) {
			throw new IllegalArgumentException("statNumber is null");
		}
		StatName sn = byStatNumber.get(statNumber);
		if (sn == null) {
			throw new IllegalArgumentException("no such statNumber " + statNumber);
		}
		return sn;
	}




	public Iterator<StatName> iterator() {
		return  byStatNumber.values().iterator();
	}
}