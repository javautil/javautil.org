package commandline;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ResourceBundle;

import org.javautil.commandline.CommandLineHandler;


/**
 * an annotated command line example
 * 
 * provides utility to escape a file's contents and echo it to System.out or
 * another file with specified characters escaped
 * 
 * @author bcm
 */
public class Escape {

    public static final String revision = "$Revision: 1.1 $";

    public static String getRevision() {
	String[] words = revision.split(" ");
	return words[1];
    }

    private void doRunRun(EscapeArguments arguments) throws IOException {
	BufferedReader reader = null;
	Writer writer = null;
	final String ENDL = System.getProperty("line.separator");
	try {
	    reader = new BufferedReader(
		    new FileReader(arguments.getInputFile()));
	    String line = null;
	    writer = new BufferedWriter(new OutputStreamWriter(arguments
		    .getOutputFile() != null ? new FileOutputStream(arguments
		    .getOutputFile()) : System.out));
	    while ((line = reader.readLine()) != null) {
		if (!arguments.isLineFeedEscape()) {
		    for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (arguments.getTarget().contains(
				new String(new char[] { c }))) {
			    writer.append(arguments.getEscapeChar());
			}
			writer.append(c);
		    }
		} else {
		    writer.append(line);
		    writer.append(arguments.getEscapeChar());
		}
		writer.append(ENDL);
	    }
	    writer.flush();
	} finally {
	    if (reader != null) {
		reader.close();
	    }
	    if (writer != null && arguments.getOutputFile() != null) {
		writer.close();
	    }
	}
    }

    public static void main(String[] javaArgs) throws Exception {
	Escape invocation = new Escape();
	EscapeArguments arguments = new EscapeArguments();
	CommandLineHandler cmd = new CommandLineHandler(ResourceBundle
		.getBundle("commandline/escape"), arguments);
	cmd.setApplicationName("Escape");
	cmd.setApplicationDescription("A utility to escape characters");
	cmd.setApplicationVersion(getRevision());
	cmd.setApplicationHelpText("specify an inputFile to be read and "
		+ "any optional arguments");
	cmd.parse(javaArgs);
	invocation.doRunRun(arguments);
    }
}
