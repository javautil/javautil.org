package org.javautil.mp3;

import java.io.File;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.javautil.lang.ExceptionHandler;
import org.javautil.mp3.persistence.Mp3Persistence;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class MetadataExtractor implements InitializingBean {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	// TODO wire
	private FileIterator fileIterator;
	@Autowired
	private Mp3MetadataProcessor populator; // = new MP3MetadataPopulator();

	@Autowired
	private Mp3Persistence persistence;

	private Collection<File> directories;

	private ExceptionHandler exceptionHandler;

	/**
	 * @return the directories
	 */
	public Collection<File> getDirectories() {
		return directories;
	}

	/**
	 * @param directories
	 *            the directories to set
	 */
	public void setDirectories(final Collection<File> directories) {
		this.directories = directories;
	}

	@Override
	public void afterPropertiesSet() {
		if (persistence == null) {
			throw new IllegalStateException("persistence is null");
		}

		if (populator == null) {
			throw new IllegalStateException("populator is null");
		}
		//
		// if (exceptionHandler == null) {
		// throw new IllegalStateException("exceptionHandler is null");
		// }

	}

	public void process() {
		afterPropertiesSet();
		File file;
		fileIterator = new FileIterator(new Mp3FileFilter());
		fileIterator.setDirectories(directories);
		int fileNumber = 1;
		while ((file = fileIterator.next()) != null) {
			final Mp3Metadata meta = new Mp3Metadata();

			String canonicalPath = null;
			try {
				canonicalPath = file.getCanonicalPath();
				if (logger.isInfoEnabled()) {
					logger.info("processing " + fileNumber++ + " '"
							+ canonicalPath + "'");
				}
				meta.setFileName(canonicalPath);
				populator.process(meta);
				persistence.save(meta);
			} catch (final Exception ex) {
				if (exceptionHandler != null) {
					final String message = "while processing '" + canonicalPath
							+ "' " + ex.getMessage();
					final Exception e = new Exception(message, ex);
					exceptionHandler.handleException(e, false);
				} else {
					logger.error(ex);
				}
			}
		}
		persistence.flushAndCommit();
	}

	public void setPersistence(final Mp3Persistence persister) {
		this.persistence = persister;
	}

	/**
	 * @return the persistence
	 */
	public Mp3Persistence getPersistence() {
		return persistence;
	}

	/**
	 * @return the populator
	 */
	public Mp3MetadataProcessor getPopulator() {
		return populator;
	}

	/**
	 * @param populator
	 *            the populator to set
	 */
	public void setPopulator(final Mp3MetadataProcessor populator) {
		this.populator = populator;
	}

	public void dispose() {
		persistence.dispose();
		if (exceptionHandler != null) {
			exceptionHandler.dispose();
		}
	}

	public void setExceptionHandler(final ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

}
