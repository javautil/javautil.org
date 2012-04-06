package commandline;

import org.javautil.commandline.annotations.Optional;

public class CommandLineArgumentsBase {

    @Optional
    private boolean verbose;

    public boolean isVerbose() {
	return verbose;
    }

    public void setVerbose(boolean verbose) {
	this.verbose = verbose;
    }
}
