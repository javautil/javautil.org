package org.javautil.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javautil.io.IOUtils;
import org.javautil.web.HttpResponseUtils;
import org.springframework.web.servlet.view.AbstractView;

/**
 * Handles all processing required to download a file from a View. This includes
 * setting cache headers so that the response not be cached, and so that the
 * download prompts the user with a download dialog. The dialog will use the
 * name of the file by default, and the length of the file will be reported to
 * the client to allow for the browser to report the remaining download time.
 * 
 * @author bcm
 */
public class FileDownloadView extends AbstractView {

	/**
	 * Property for the target file for download. Should be set prior to
	 * rendering the view.
	 */
	private File file;

	/**
	 * Name for the file download name to be presented in the attachment content
	 * header. This name is typically prompted to the client when saving the
	 * file. If null, then the actual file's filename is used.
	 */
	private String fileName;

	/**
	 * Default constructor for bean support. Don't forget to set file!
	 */
	public FileDownloadView() {
	}

	/**
	 * Preferred constructor setting the file. Throws an
	 * IllegalArgumentException if the file is null.
	 * 
	 * @throws IllegalArgumentException
	 * @param file
	 */
	public FileDownloadView(File file) {
		if (file == null) {
			throw new IllegalArgumentException("file is null");
		}
		this.file = file;
	}

	/**
	 * The content type is dynamically derived from the filename's extension. If
	 * the file is null, the content type of the file cannot be derived or the
	 * extension is not found on the file, an IllegalStateException will be
	 * thrown.
	 * 
	 * @throws IllegalStateException
	 */
	@Override
	public String getContentType() {
		if (file == null) {
			throw new IllegalStateException("file is null");
		}
		String contentType = HttpResponseUtils.getContentTypeForFilename(file
				.getName());
		if (contentType == null) {
			throw new IllegalStateException("unknown extension or content "
					+ "type for file: " + file.getPath());
		}
		return contentType;
	}

	/**
	 * Processes the download operation. If the file does not exist at this
	 * point, a FileNotFoundException will be thrown. If the file cannot be
	 * read, an IOException will be thrown.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void renderMergedOutputModel(Map model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (file == null) {
			throw new IllegalStateException("file is null");
		}
		if (!file.exists()) {
			throw new FileNotFoundException("file does not exist: "
					+ file.getPath());
		}
		if (!file.canRead()) {
			throw new IOException("file is not readable: " + file.getPath());
		}
		OutputStream out = response.getOutputStream();
		HttpResponseUtils.setNoCacheHeaders(response);
		String name = fileName == null ? file.getName() : fileName;
		HttpResponseUtils.setDownloadHeaders(response, name, file.length());
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			IOUtils.pump(inputStream, out);
		} catch (IOException ioe) {
			logger.warn("failed to write focus workbook "
					+ file.getCanonicalPath());
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException ioe) {
				logger.warn("failed to close input stream "
						+ file.getCanonicalPath());
			}
			out.flush();
			out.close();
			boolean deleted = file.delete();
			if (!deleted) {
				logger.warn("failed to delete " + file.getCanonicalPath());
			}
			file = null;
		}
	}

	/**
	 * Getter for the target file for download.
	 * 
	 * @return file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Setter for the target file for download.
	 * 
	 * @param file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Getter for the fileName.
	 * 
	 * @return fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * Setter for the fileName.
	 * 
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
