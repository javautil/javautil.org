package commandline;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;

import org.javautil.commandline.ParamType;
import org.javautil.commandline.annotations.Exclusive;
import org.javautil.commandline.annotations.MultiValue;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;


/**
 * for use with Escape.java
 * 
 * @author bcm
 */
public class EscapeArguments {
    @Required
    private File inputFile;

    @Optional
    private File outputFile;

    @Optional
    private String escapeChar = "\\";

    @Exclusive(property = "escapeChar")
    private boolean lineFeedEscape = false;

    @Optional
    @MultiValue(type=ParamType.STRING)
    private Collection<String> target = Arrays
	    .asList(new String[] { "\"", "'" });

    public File getInputFile() {
	return inputFile;
    }

    public void setInputFile(File inputFile) {
	this.inputFile = inputFile;
    }

    public File getOutputFile() {
	return outputFile;
    }

    public void setOutputFile(File outputFile) {
	this.outputFile = outputFile;
    }

    public String getEscapeChar() {
	return escapeChar;
    }

    public void setEscapeChar(String escapeChar) {
	this.escapeChar = escapeChar;
    }

    public Collection<String> getTarget() {
	return target;
    }

    public void setTarget(Collection<String> target) {
	this.target = target;
    }

    public boolean isLineFeedEscape() {
	return lineFeedEscape;
    }

    public void setLineFeedEscape(boolean lineFeedEscape) {
	this.lineFeedEscape = lineFeedEscape;
    }
}