package org.javautil.file;

import java.io.IOException;
import java.io.Writer;
import java.sql.Date;

import org.apache.log4j.Logger;
import org.javautil.lang.SystemProperties;
import org.javautil.text.CSVWriter;
import org.javautil.text.CommonDateFormat;
import org.javautil.text.SimpleDateFormatter;

public class FileInfoMarshallerDefault implements FileInfoListener,
		FileInfoMarshaller {
	private final CSVWriter csv = new CSVWriter();
	private final Writer writer;
	private final Logger logger = Logger.getLogger(getClass());
	private SimpleDateFormatter dateFormatter = new SimpleDateFormatter(
			CommonDateFormat.ISO_DATE);
	private SimpleDateFormatter timeFormatter = new SimpleDateFormatter(
			CommonDateFormat.SECONDS);
	private String sourceName = null;

	public FileInfoMarshallerDefault(final Writer w) {
		if (w == null) {
			throw new IllegalArgumentException("writer is null");
		}
		this.writer = w;
		final SimpleDateFormatter sdf = new SimpleDateFormatter(
				CommonDateFormat.ISO_SECOND);

		csv.setDateFormatter(sdf);
		csv.setSuppressTrailingNulls(false);
	}

	/**
	 * @see org.javautil.file.FileInfoMarshaller#processFileInfo(org.javautil.file.FileInfo)
	 **/
	@Override
	public void processFileInfo(final FileInfo fileInfo) throws IOException {
		final String rec = csv.asString(sourceName,
				fileInfo.getDirectoryName(), fileInfo.getFileName(),
				fileInfo.getFileBaseName(), fileInfo.getExtension(),
				dateFormatter.format(new Date(fileInfo.getLastModTime())),
				timeFormatter.format(new Date(fileInfo.getLastModTime())),
				fileInfo.getLength(), fileInfo.getMD5DigestAsString());
		writer.write(rec);
		writer.write(SystemProperties.newline);

	}

	/**
	 * @return the sourceName
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName
	 *            the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * @return the fieldSeparator
	 */
	public String getFieldSeparator() {
		return csv.getFieldSeparator();
	}

	/**
	 * @param fieldSeparator
	 *            the fieldSeparator to set
	 */
	public void setFieldSeparator(String fieldSeparator) {

		csv.setFieldSeparator(fieldSeparator);
	}
}
