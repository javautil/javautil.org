/*
 * NonexistantTableException.java
 *
 * Created on December 25, 2005, 3:10 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.javautil.jdbc.metadata;

/**
 * 
 * @author jim
 */
@SuppressWarnings("serial")
public class NonexistantTableException extends RuntimeException {

	/**
	 * Creates a new instance of <code>NonexistantTableException</code> without
	 * detail message.
	 */
	public NonexistantTableException() {
	}

	/**
	 * Constructs an instance of <code>NonexistantTableException</code> with the
	 * specified detail message.
	 * 
	 * @param msg
	 *            the detail message.
	 */
	public NonexistantTableException(final String msg) {
		super(msg);
	}
}
