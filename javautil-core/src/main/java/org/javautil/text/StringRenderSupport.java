package org.javautil.text;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javautil.dataset.ColumnMetadata;
import org.javautil.dataset.DataType;
import org.javautil.dataset.DatasetMetadata;
import org.javautil.document.style.HorizontalAlignment;

public class StringRenderSupport {

	private final Log logger = LogFactory.getLog(getClass());

	private DateFormat defaultDateFormat = new SimpleDateFormat(
			"MM/dd/yy hh:mma");

	public String formatNumber(final Object data, final NumberFormat format) {
		String formatted = data == null ? "" : String.valueOf(data);
		if (format != null) {
			if (data != null && Number.class.isAssignableFrom(data.getClass())) {
				formatted = format.format(data);
			} else if (data != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("attempting to format " + data + " (Class: "
							+ data.getClass() + ") as number");
				}
				formatted = format.format(data);
			}
		}
		return formatted;
	}

	public String formatDate(final Object data, final DateFormat format) {
		String formatted = data == null ? "" : String.valueOf(data);
		if (format != null) {
			formatted = format.format(data);
		} else {
			formatted = defaultDateFormat.format(data);
		}
		return formatted;
	}

	public HorizontalAlignment getHorizontalAlignment(final Long columnIndex,
			final DatasetMetadata metadata, final ColumnMetadata column,
			final boolean automatic) {
		HorizontalAlignment ha = column.getHorizontalAlignment();
		if (ha == null && automatic) {
			if (columnIndex == 0) {
				ha = HorizontalAlignment.LEFT;
			} else if (columnIndex == metadata.getColumnCount() - 1) {
				ha = HorizontalAlignment.RIGHT;
			} else if (DataType.isNumberType(column.getDataType())
					|| DataType.isDateType(column.getDataType())) {
				ha = HorizontalAlignment.RIGHT;
			} else {
				ha = HorizontalAlignment.LEFT;
			}
		}
		return ha;
	}

	public DateFormat getDefaultDateFormat() {
		return defaultDateFormat;
	}

	public void setDefaultDateFormat(final DateFormat defaultDateFormat) {
		this.defaultDateFormat = defaultDateFormat;
	}

}
