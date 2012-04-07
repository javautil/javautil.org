package org.javautil.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Zip file archive.
 * 
 * @author tim@softwareMentor.com
 * 
 *         TODO document, rename as this is a zipArchive file compare to Zip
 *         Helper and ArchiveFileUtils
 */
public final class Archive {
	final Logger logger = LoggerFactory.getLogger(getClass());
	private final File archiveFile;
	private static final int BUFFER_SIZE = 1024 * 8;
	private final byte buffer[] = new byte[BUFFER_SIZE];
	private ZipOutputStream zos;

	public Archive(final File archiveFile) {
		this.archiveFile = archiveFile;
	}

	@SuppressWarnings("unchecked")
	public void extract(final File targetDirectory) throws IOException {
		ZipFile zipFile = null;
		try {
			zipFile = new ZipFile(archiveFile, ZipFile.OPEN_READ);
			final Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				final ZipEntry entry = entries.nextElement();
				final File entryFile = new File(targetDirectory,
						entry.getName());
				final File entryDir = entryFile.getParentFile();
				entryDir.mkdirs();
				if (!entry.isDirectory()) {
					BufferedInputStream bis = null;
					BufferedOutputStream bos = null;
					try {
						bis = new BufferedInputStream(
								zipFile.getInputStream(entry));
						int currentByte;
						bos = new BufferedOutputStream(new FileOutputStream(
								entryFile));
						while ((currentByte = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
							bos.write(buffer, 0, currentByte);
						}
					} finally {
						if (bis != null) {
							bis.close();
						}
						if (bos != null) {
							bos.close();
						}
					}
				}
			}
		} finally {
			if (zipFile != null) {
				zipFile.close();
			}
		}
	}

	public void add(final File target) throws IOException {
		try {
			zos = new ZipOutputStream(new FileOutputStream(archiveFile));
			addRecursive(target);
		} finally {
			if (zos != null) {
				zos.close();
			}
		}
	}

	// todo jjs document

	private void addRecursive(final File target) throws IOException {
		BufferedInputStream bis = null;
		try {
			if (target.isDirectory()) {
				final File[] files = target.listFiles();
				for (final File file : files) {
					addRecursive(file);
				}
			} else {
				final ZipEntry entry = new ZipEntry(target.getPath());
				zos.putNextEntry(entry);
				bis = new BufferedInputStream(new FileInputStream(target));
				int count;
				while ((count = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
					zos.write(buffer, 0, count);
				}
				if (logger.isDebugEnabled()) {
					logger.debug("added: " + target.getPath());
				}
			}
		} finally {
			if (bis != null) {
				bis.close();
			}
		}
	}

	public void add(final Map<File, String> files) throws IOException {
		try {
			zos = new ZipOutputStream(new FileOutputStream(archiveFile));
			for (final File file : files.keySet()) {
				final ZipEntry zipEntry = new ZipEntry(files.get(file));
				zos.putNextEntry(zipEntry);
				final BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(file));
				int count = 0;
				while ((count = bis.read(buffer, 0, BUFFER_SIZE)) != -1) {
					zos.write(buffer, 0, count);
				}
			}
		} finally {
			if (zos != null) {
				zos.close();
			}
		}
	}

	public File getArchiveFile() {
		return archiveFile;
	}

}
