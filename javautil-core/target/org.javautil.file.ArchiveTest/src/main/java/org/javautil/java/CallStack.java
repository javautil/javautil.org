package org.javautil.java;

/**
 * Contains an array of StackTraceElement from the instantiator to the top of
 * the stack.
 * 
 * Useful when trying to keep the context of an event.
 * 
 * An example usage is Used specifically in when a connection is obtained from a
 * connection pool and not returned to log information about where the
 * connection was obtained.
 * 
 * TODO test
 * 
 * @author jjs
 * 
 */
public class CallStack {

	private final StackTraceElement[] stack;
	private static final String newline = System.getProperty("line.separator");

	public CallStack() {
		stack = Thread.currentThread().getStackTrace();
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		for (final StackTraceElement element : stack) {
			b.append(element.toString());
			b.append(newline);
		}
		return b.toString();
	}

	public StackTraceElement[] getStack() {
		return stack;
	}
}
