package org.javautil.document.renderer;

import org.javautil.dataset.Dataset;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public interface CsvRendererRequest extends DatasetRendererRequest {
	public String getColumnSeparator();

	public void setColumnSeparator(String val);

	@Override
	public void setDataset(Dataset ds);

	public boolean isEmitHeader();

	public void setEmitHeader(boolean val);

	public String getNewline();

	public void setNewline(String newline);

	@Override
	public void setDateFormat(String string);

	public void setUseDefaultDateFormatForAllDates(boolean value);

	@Override
	public String getDateFormat();

}
