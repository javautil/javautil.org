package org.javautil.security;

public class NoExitException extends SecurityException {
	private static final long serialVersionUID = 1L;
	public final int exitCode;

	public NoExitException(final int code) {
		super("this unit test forbids the use of System.exit");
		this.exitCode = code;
	}
}