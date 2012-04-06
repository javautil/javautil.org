package org.javautil.workbook;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.javautil.io.ClassPathResourceResolver;
import org.javautil.io.IOUtils;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class ImageLoader {

	public void loadImage(final HSSFWorkbook workbook, final HSSFSheet sheet,
			final String resourceName, final Class<?> resourceClass,
			final int col1, final int row1, final int col2, final int row2)
			throws IOException {
		final ClassPathResourceResolver resolver = new ClassPathResourceResolver(
				resourceClass);
		try {
			resolver.afterPropertiesSet();
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
		final InputStream resource = resolver.getResource(resourceName)
				.getInputStream();

		final byte[] picData = IOUtils.readBytesFromStream(resource, 1024 * 4,
				true); // 128k
		// buffer
		final int indx = workbook
				.addPicture(picData, Workbook.PICTURE_TYPE_PNG);
		final HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		final HSSFClientAnchor anchor = new HSSFClientAnchor(0, // the x
																// coordinate
				// within the first
				// cell.
				0, // the y coordinate within the first cell.
				0, // the x coordinate within the second cell.
				0, // the y coordinate within the second cell.
				(short) col1, // the column (0 based) of the first cell.
				row1, // the row (0 based) of the first cell.
				(short) col2, // the column (0 based) of the second cell.
				row2 // the row (0 based) of the second cell.

		);
		anchor.setAnchorType(2);
		patriarch.createPicture(anchor, indx);
	}
}
