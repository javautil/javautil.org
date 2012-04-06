package org.javautil.web;

import javax.servlet.http.HttpServletResponse;

public class HttpResponseUtils {

	public static final String CONTENT_TYPE_EXCEL = "application/vnd.ms-excel";
	
	public static final String CONTENT_TYPE_PDF = "application/pdf";

	public static final String CONTENT_TYPE_ZIP = "application/zip";
	
	public static void setDownloadHeaders(HttpServletResponse response,
			String fileName, Number contentLength) {
		response.setContentType(getContentTypeForFilename(fileName));
		String header = "content-disposition";
		String fn = fileName.replaceAll("[^A-z_0-9\\-\\.]", "_");
		response.setHeader(header, "attachment; filename=\"" + fn + "\"");
		if (contentLength != null) {
			response.setContentLength(contentLength.intValue());
		}
	}

	public static String getContentTypeForExtension(String _fileExtension) {
		String ret = null;
		String fileExtension = _fileExtension.toLowerCase();
		if (fileExtension.equals("zip")) {
			ret = CONTENT_TYPE_ZIP;
		} else if (fileExtension.equals("pdf")) {
			ret = CONTENT_TYPE_PDF;
		} else if (fileExtension.equals("xls")) {
			ret = CONTENT_TYPE_EXCEL;
		}
		return ret;
	}

	public static String getContentTypeForFilename(String fileName) {
		String ret = null;
		if (fileName.indexOf(".") > -1) {
			String[] parts = fileName.split("\\.");
			String extension = parts[parts.length - 1];
			ret = getContentTypeForExtension(extension);
		}
		if (ret == null) {
			ret = "text/plain";
		}
		return ret;
	}

	public static void setNoCacheHeaders(HttpServletResponse response) {
		response.setHeader("cache-control", "no-cache"); // HTTP 1.1
		response.setHeader("pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("expires", 0); // for proxy server caching
	}

}
