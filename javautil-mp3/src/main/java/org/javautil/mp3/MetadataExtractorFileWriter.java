package org.javautil.mp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.javautil.mp3.persistence.Mp3OutputStreamPersistence;

public class MetadataExtractorFileWriter extends MetadataExtractor {

	// TODO this needs to be document
	public void setOutputFile(final File file) throws FileNotFoundException {
		final Mp3OutputStreamPersistence persister = (Mp3OutputStreamPersistence) getPersistence();
		final FileOutputStream fos = new FileOutputStream(file);
		persister.setOutputStream(fos);
	}
}
