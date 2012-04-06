package commandline;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.ResourceBundle;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.javautil.commandline.CommandLineHandler;


/**
 * an annotated command line example
 * 
 * provides utility to escape a file's contents and echo it to System.out or
 * another file with specified characters escaped
 * 
 * @author jjs
 */
public class CommandLineMain {

    public static final String revision = "$Revision: 1.1 $";

    /**
     * stub for getting revision from build
     */
    public static String getBuildIdentifier() {
	String[] words = revision.split(" ");
	return words[1];
    }

    private void doRun(CommandLineArguments arguments) throws IOException {
	File file = arguments.getFile();
	if (file == null) {
	    throw new IllegalStateException(
		    "file is null, but File is a required argument");
	}
	BufferedReader reader = new BufferedReader(new FileReader(arguments
		.getFile()));

	Collection<File> files = arguments.getFiles();
	reader.close();

    }

    public static void main(String[] javaArgs) throws Exception {

	BasicConfigurator.configure();
	Logger logger = Logger.getRootLogger();
	logger.setLevel(Level.DEBUG);
	CommandLineMain invocation = new CommandLineMain();
	CommandLineArguments arguments = new CommandLineArguments();

	CommandLineHandler cmd = new CommandLineHandler();
	// todo jjs should arguments and resource bundle be part of the
	// constructor?
	cmd.setResourceBundle(ResourceBundle
		.getBundle("commandline/CommandLineArguments"));
	cmd.setArguments(arguments);
	cmd.setApplicationVersion(getBuildIdentifier());

	cmd.parse(javaArgs);

	invocation.doRun(arguments);
    }
}
