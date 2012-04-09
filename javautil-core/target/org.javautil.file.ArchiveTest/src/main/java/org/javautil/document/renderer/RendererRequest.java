package org.javautil.document.renderer;

import javax.xml.transform.stream.StreamResult;

import org.javautil.dataset.Dataset;
import org.javautil.document.MimeType;

/**
 * 
 * @author jjs
 * 
 */
public interface RendererRequest {

	/**
	 * Get the MimeType of which the workbook will be rendered.
	 * 
	 * @return
	 */
	public MimeType getMimeType();

	/**
	 * Set the MimeType to which the workbook will be rendered.
	 * 
	 * @param mimeType
	 */
	public void setMimeType(final MimeType mimeType);

	// TODO is this good to delet
	// public Renderer getRenderer();

	public StreamResult getStreamResult();

	public void setStreamResult(StreamResult sr);

	public Dataset getDataset();

	/**
	 * 
	 * @param DateFormat
	 *            a String compatible with SimpleDateFormat
	 */
	public void setDateFormat(String DateFormat);

	/**
	 * A String compatible with SimpleDateFormat
	 * 
	 * @return
	 */
	public String getDateFormat();

}
