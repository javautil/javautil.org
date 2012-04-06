package org.javautil.commandline;

import jcmdline.CmdLineHandler;

public class DefaultCommandLineErrorHandler implements CommandLineErrorHandler {
	private CmdLineHandler cmdLineHandler;

	private boolean throwIllegalArgumentException = true;

	@Override
	public void setCmdLineHandler(final CmdLineHandler cmdLineHandler) {
		this.cmdLineHandler = cmdLineHandler;
	}

	@Override
	public void handleException(final String message) {
		if (throwIllegalArgumentException) {
			throw new IllegalArgumentException(message);
		} else {
			cmdLineHandler.exitUsageError(message);
		}
	}

	public boolean isThrowIllegalArgumentException() {
		return throwIllegalArgumentException;
	}

	// TODO describe
	public void setThrowIllegalArgumentException(
			final boolean throwIllegalArgumentException) {
		this.throwIllegalArgumentException = throwIllegalArgumentException;
	}

}
