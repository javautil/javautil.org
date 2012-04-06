package org.javautil.commandline;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import org.javautil.commandline.annotations.MultiValue;
import org.javautil.commandline.annotations.Optional;
import org.javautil.commandline.annotations.RequiredUnless;

public class ArgumentBean extends BaseArgumentBean {

	@Optional
	@MultiValue(type = ParamType.DATE)
	private Collection<Date> postingDates;

	@MultiValue(type = ParamType.FILE)
	@Optional
	private Collection<File> files;

	@MultiValue(type = ParamType.INTEGER)
	@Optional
	private Collection<Integer> integers;

	public Collection<Integer> getIntegers() {
		return integers;
	}

	public void setIntegers(final Collection<Integer> integers) {
		this.integers = integers;
	}

	public Collection<String> getSecretWords() {
		return secretWords;
	}

	public void setSecretWords(final Collection<String> secretWords) {
		this.secretWords = secretWords;
	}

	@Optional
	@MultiValue(type = ParamType.STRING)
	private Collection<String> words;

	@RequiredUnless(property = "words")
	private Collection<String> secretWords;

	@Optional
	private Date date;

	/**
	 * @return the files
	 */
	public Collection<File> getFiles() {
		return files;
	}

	/**
	 * @param files
	 *            the files to set
	 */
	public void setFiles(final Collection<File> files) {
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
	 *            the words to set
	 */
	public void setWords(final Collection<String> words) {
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
	 *            the date to set
	 */
	public void setDate(final Date date) {
		this.date = date;
	}

	public Collection<Date> getPostingDates() {
		return postingDates;
	}

	public void setPostingDates(final Collection<Date> postingDates) {
		this.postingDates = postingDates;
	}

}
