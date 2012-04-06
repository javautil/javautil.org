package commandline;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import org.javautil.commandline.ParamType;
import org.javautil.commandline.annotations.MultiValue;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.Required;


public class CommandLineArguments extends CommandLineArgumentsBase {
    // private Integer number;
    @Required
    private File file;

    @Optional
    private boolean verbose;
    @MultiValue(type=ParamType.FILE)
    @Optional
    private Collection<File> files;

    @Optional
    @MultiValue(type=ParamType.STRING)
    private Collection<String> words;
    @Optional
    private Date date;

    /**
     * @return the file
     */
    public File getFile() {
	return file;
    }

    /**
     * @param file
     *                the file to set
     */
    public void setFile(File file) {
	this.file = file;
    }

    /**
     * @return the files
     */
    public Collection<File> getFiles() {
	return files;
    }

    /**
     * @param files
     *                the files to set
     */
    public void setFiles(Collection<File> files) {
	this.files = files;
    }

    /**
     * @return the words
     */
    public Collection<String> getWords() {
	return words;
    }

    /**
     * @param words
     *                the words to set
     */
    public void setWords(Collection<String> words) {
	this.words = words;
    }

    /**
     * @return the date
     */
    public Date getDate() {
	return date;
    }

    /**
     * @param date
     *                the date to set
     */
    public void setDate(Date date) {
	this.date = date;
    }

    /**
     * @return the verbose
     */
    public boolean isVerbose() {
	return verbose;
    }

    /**
     * @param verbose
     *                the verbose to set
     */
    public void setVerbose(boolean verbose) {
	this.verbose = verbose;
    }

}
