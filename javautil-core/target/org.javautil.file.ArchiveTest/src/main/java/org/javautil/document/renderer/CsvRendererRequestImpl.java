package org.javautil.document.renderer;

/**
 * 
 * @author jjs@javautil.org
 * 
 */
public class CsvRendererRequestImpl extends AbstractRendererRequestImpl
		implements CsvRendererRequest {
	private String columnSeparator = ",";
	private boolean emitHeader = true;
	private String newline = System.getProperty("line.separator");
	private String dateFormat;

	private boolean useDefaultDateFormatForAllDates = false;

	public boolean isUseDefaultDateFormatForAllDates() {
		return useDefaultDateFormatForAllDates;
	}

	@Override
	public void setUseDefaultDateFormatForAllDates(
			final boolean useDefaultDateFormatForAllDates) {
		this.useDefaultDateFormatForAllDates = useDefaultDateFormatForAllDates;
	}

	@Override
	public String getDateFormat() {
		return dateFormat;
	}

	@Override
	public void setDateFormat(final String dateFormat) {
		this.dateFormat = dateFormat;
	}

	@Override
	public String getNewline() {
		return newline;
	}

	@Override
	public void setNewline(final String newline) {
		this.newline = newline;
	}

	@Override
	public String getColumnSeparator() {
		return columnSeparator;
	}

	@Override
	public void setColumnSeparator(final String val) {
		this.columnSeparator = val;
	}

	@Override
	public boolean isEmitHeader() {
		return emitHeader;
	}

	@Override
	public void setEmitHeader(final boolean val) {
		emitHeader = val;
	}

	public Renderer getRenderer() {
		return new CsvRenderer();
	}

}
