package org.javautil.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/**
 * Maps urls of type http://dbexperts.com/xsd http://dbexperts.com/xsl
 * http://dbexperts.com/
 * 
 * TODO this needs to go away
 * 
 * TODO figure out how to make this externalized map could be a property map
 * based on a definition
 */
public class DbexpertsEntityResolver implements EntityResolver {
	// TODO jjs what is this?
	private static final String dbexpertsPath = "http://dbexperts.com/";
	private static final String newline = System.getProperty("line.separator");
	private static Logger logger = Logger
			.getLogger(DbexpertsEntityResolver.class.getName());

	@Override
	public InputSource resolveEntity(final String publicId,
			final String systemId) throws IOException {
		InputSource in = null;
		logger.info("resolving public '" + publicId + "' system '" + systemId
				+ "'");
		if (systemId.startsWith(dbexpertsPath)) {
			// todo resolve with class resolver
			// todo this only works if current dir is project root
			// TODO JJS WTF
			final String resourcePath = "./src/resources/com/dbexperts/resources";
			final String filePartialPath = systemId.substring(dbexpertsPath
					.length());
			final String fileFullPath = resourcePath + "/" + filePartialPath;
			final File f = new File(fileFullPath);

			final String rmessage = "reading '" + fileFullPath + " as '"
					+ f.getAbsolutePath() + "'";
			logger.info(rmessage);
			if (!f.canRead()) {
				throw new IllegalArgumentException("cannot read '"
						+ fileFullPath + "'");
			}

			FileReader fr;
			try {
				fr = new FileReader(f);
			} catch (final FileNotFoundException e) {
				final String message = "can't read '" + f.getAbsolutePath()
						+ "'" + newline + "resourcePath '" + resourcePath + "'"
						+ newline + "filePartialPath '" + filePartialPath + "'"
						+ newline + e.getMessage();
				throw new FileNotFoundException(message);
			}
			in = new InputSource(fr);
		}
		return in;

	}
}