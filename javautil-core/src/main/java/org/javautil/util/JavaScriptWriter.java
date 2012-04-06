package org.javautil.util;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class JavaScriptWriter extends PrintWriter {

	public JavaScriptWriter(final OutputStream out) {
		super(out);
	}

	public JavaScriptWriter(final Writer out) {
		super(out);
	}

	public void printclosure(final String before, final String content,
			final String after) {
		if (content == null) {
			throw new IllegalStateException("content is null");
		}
		if (before != null) {
			super.print(before);
		}
		super.print("{");
		super.println();
		super.print("\t");
		final StringBuilder s = new StringBuilder(content.replace("\n", "\n\t"));
		while (Character.isWhitespace(s.charAt(s.length() - 1))) {
			s.setLength(s.length() - 1);
		}
		super.print(s.toString());
		super.println();
		super.print("}");
		if (after != null) {
			super.print(after);
		}
	}

	public void printstmt(final String statement) {
		if (statement == null) {
			throw new IllegalStateException("statement is null");
		}
		super.print(statement);
		super.print(";");
		super.println();
	}

	public static JavaScriptWriter getInstance(final StringWriter stringWriter) {
		if (stringWriter == null) {
			throw new IllegalStateException("stringWriter is null");
		}
		final JavaScriptWriter jsWriter = new JavaScriptWriter(stringWriter);
		return jsWriter;
	}

}
