package org.javautil.util;

public class Timer {
	
	private long startTime = System.nanoTime();
	
	public double getElapsedMillis() {
		long now = System.nanoTime();
		double elapsed = (now - startTime) / 1e6;
		return elapsed;
	}
	
	public void reset() {
		startTime = System.nanoTime();
	}

}
