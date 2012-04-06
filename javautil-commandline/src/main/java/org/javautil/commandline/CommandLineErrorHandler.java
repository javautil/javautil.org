package org.javautil.commandline;

import jcmdline.CmdLineHandler;

public interface CommandLineErrorHandler {
	public void setCmdLineHandler(CmdLineHandler cmdLineHandler);

	public void handleException(String message);
}
