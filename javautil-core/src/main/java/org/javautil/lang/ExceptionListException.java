package org.javautil.lang;

import java.util.ArrayList;

public class ExceptionListException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3702168128235972538L;
	private ArrayList<Exception> exceptions = new ArrayList<Exception>();
	
	public void add(Exception exception) {
		exceptions.add(exception);
	}
	
	public void throwIfWarranted() throws ExceptionListException {
		if (exceptions.size() > 0) {
			throw this;
		}
	}
	
	public void throwRuntimeExceptionIfWarranted()  {
		if (exceptions.size() > 0) {
			throw new RuntimeException(this);
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Exception exception : exceptions) {
			sb.append(exception.toString());
			sb.append("\n");
		}
		return sb.toString();
	}
	
	public ArrayList<Exception> getExceptions() {
		return exceptions;
	}
	
	public boolean hasExceptions() {
		return exceptions.size() > 0;
	}
}
