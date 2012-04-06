package org.javautil.mp3;

import java.io.File;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.javautil.mp3.persistence.Mp3Persistence;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class MP3SpringMetadataExtractor implements InitializingBean {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private FileIterator fileIterator;
	@Autowired
	private final Mp3MetadataProcessor populator = new Mp3MetadataPopulatorImpl();
	@Autowired
	private Mp3Persistence persistence;

	private Collection<File> directories;

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

		if (directories == null) {
			throw new IllegalStateException("directories is null");
		}

		// TODO probably shouldn't be here
		fileIterator = new FileIterator(new Mp3FileFilter());
		fileIterator.setDirectories(directories);
	}

	public void process() {
		afterPropertiesSet();
		File file;
		while ((file = fileIterator.next()) != null) {
			final Mp3Metadata meta = new Mp3Metadata();
			String canonicalPath = null;
			try {
				canonicalPath = file.getCanonicalPath();
				meta.setFileName(canonicalPath);
				populator.process(meta);
				persistence.save(meta);
			} catch (final Exception ex) {
				logger.error(ex); // TODO support handler
			}
		}
	}

	public void setPersistence(final Mp3Persistence persister) {
		this.persistence = persister;
	}
}
