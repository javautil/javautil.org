package org.javautil.file;

import java.io.File;
import java.math.BigInteger;

public class FileInfo {
	private String directoryName;
	private String fileBaseName;
	private String extension;
	private long length;
	private BigInteger md5Digest;
	private long lastModTime;
	private String fileName;

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileInfo() {

	}

	public FileInfo(final File f) {

	}

	public void setFile(final File f) {
		if (f == null) {
			throw new IllegalArgumentException("file is null");
		}
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(final String directoryName) {
		this.directoryName = directoryName;
	}

	public String getFileBaseName() {
		return fileBaseName;
	}

	public void setFileBaseName(final String fileBaseName) {
		this.fileBaseName = fileBaseName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	public long getLength() {
		return length;
	}

	public void setLength(final long bytes) {
		// checkFileIsNotNull();
		this.length = bytes;
	}

	public BigInteger getMd5Digest() {
		return md5Digest;
	}

	public void setMd5Digest(final BigInteger md5) {
		md5Digest = md5;
	}

	public String getMD5DigestAsString() {
		return md5Digest == null ? null : md5Digest.toString(16);
	}

	public long getLastModTime() {
		return lastModTime;
	}

	public void setLastModTime(final long lastModTime) {
		this.lastModTime = lastModTime;
	}

}
