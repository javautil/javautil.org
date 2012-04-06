package org.javautil.workbook;

import java.util.List;
import java.util.Map;

public class QueryRegion {
	String query;

	Map<String, Object> binds;

	/**
	 * Starting row number for region, starts with 0;
	 * 
	 * 
	 */
	Integer startRow;

	/**
	 * Starting column number for region.
	 */

	Integer startingCol;

	List<String> columnNames;

}
