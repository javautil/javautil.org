package org.javautil.java;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class StackTraceHelper {

	private static final String newline = System.getProperty("line.separator");

	public static String getStackTraceAsString(final Throwable aThrowable) {
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		aThrowable.printStackTrace(printWriter);
		return result.toString();
	}

	/**
	 * Use getStackTraceAsString instead.
	 * 
	 * 
	 * @author bcm
	 * @param stack
	 * @return
	 */
	@Deprecated
	public static String getCallStackAsString(final StackTraceElement[] stack) {
		final StringBuilder b = new StringBuilder();
		for (final StackTraceElement ste : stack) {
			b.append(ste.getClassName());
			b.append(" ");
			b.append(ste.getMethodName());
			b.append(" ");
			b.append(ste.getLineNumber());
			b.append(newline);
		}
		return b.toString();
	}
}
