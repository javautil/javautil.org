package org.javautil.workbook;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.javautil.dataset.MetadataException;
import org.javautil.lang.SystemProperties;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 * 
 *         This is really goofy. If you get some crazy messages from POI about
 *         finding a todo cam
 */
public class CellFormula {
	private static Logger logger = Logger.getLogger(CellFormula.class);

	private static final String regEx = "\\{([^\\}]*)}";

	private static final Pattern pattern = Pattern.compile(regEx);

	/**
	 * todo document A special case need to know base column for the group
	 * 
	 * @param template
	 * @param columnMap
	 * @param rowIndex
	 * @return
	 */

	public static String getAsFormulaRelative(final String template,
			final Map<String, Integer> columnMap, final int rowIndex,
			final int baseColumnIndex) {
		if (logger.isDebugEnabled()) {
			final StringBuilder sb = new StringBuilder();
			sb.append("template: '" + template + SystemProperties.newline);
			for (final String name : columnMap.keySet()) {
				sb.append("name: '" + name + "' " + columnMap.get(name)
						+ SystemProperties.newline);
			}
			sb.append("rowIndex: " + rowIndex);
			logger.debug(sb);

		}
		final Set<String> variableNames = getVariableNames(template);
		String formula = template;
		for (final String var : variableNames) {
			final String cellAddress = getVariableNameAsCellAddress(var,
					columnMap, rowIndex);
			formula = formula.replaceAll("\\{" + var + "\\}", cellAddress);
		}
		return formula;
	}

	/**
	 * todo document
	 * 
	 * @param template
	 * @param columnMap
	 * @param rowIndex
	 * @return
	 */

	public static String getAsFormula(final String template,
			final Map<String, Integer> columnMap, final int rowIndex) {
		if (logger.isDebugEnabled()) {
			final StringBuilder sb = new StringBuilder();
			sb.append("template: '" + template + SystemProperties.newline);
			for (final String name : columnMap.keySet()) {
				sb.append("name: '" + name + "' " + columnMap.get(name)
						+ SystemProperties.newline);
			}
			sb.append("rowIndex: " + rowIndex);
			logger.debug(sb);

		}
		final Set<String> variableNames = getVariableNames(template);
		String formula = template;
		for (final String var : variableNames) {
			final String cellAddress = getVariableNameAsCellAddress(var,
					columnMap, rowIndex);
			formula = formula.replaceAll("\\{" + var + "\\}", cellAddress);
		}
		return formula;
	}

	/**
	 * 
	 * 
	 * @param variableName
	 *            the variable name to be found in the map and converted
	 * @param columnMap
	 *            0 is column A in the spreadsheet , 1 is B etc.
	 * @param rowIndex
	 *            relative 0, 0 is the first row in the spreadsheet
	 * @return
	 */
	private static String getVariableNameAsCellAddress(
			final String variableName, final Map<String, Integer> columnMap,
			final int rowIndex) {
		final Integer columnIndex = columnMap.get(variableName);
		if (columnIndex == null) {
			final StringBuilder sb = new StringBuilder();
			for (final String string : columnMap.keySet()) {
				sb.append("'");
				sb.append(string);
				sb.append("' ");

			}
			throw new MetadataException("cannot locate " + variableName
					+ " in columnMap " + sb.toString());
		}

		final String retval = CellAddress.getCellAddress(rowIndex, columnIndex);
		if (logger.isDebugEnabled()) {
			logger.debug(retval);
		}
		return retval;
	}

	/**
	 * 
	 * 
	 * @param variableName
	 *            the variable name to be found in the map and converted
	 * @param columnMap
	 *            0 is column A in the spreadsheet , 1 is B etc.
	 * @param rowIndex
	 *            relative 0, 0 is the first row in the spreadsheet
	 * @return
	 * @todo drop getVariableNameAsCellAddress
	 */
	protected static String getVariableNameAsCellAddressWithOffset(
			final String variableName, final Map<String, Integer> columnMap,
			final int rowIndex, final int startingColumnIndex) {
		final Integer columnIndex = columnMap.get(variableName);
		if (columnIndex == null) {
			final StringBuilder sb = new StringBuilder();
			for (final String string : columnMap.keySet()) {
				sb.append("'");
				sb.append(string);
				sb.append("' ");

			}
			throw new MetadataException("cannot locate " + variableName
					+ " in columnMap " + sb.toString());
		}

		final String retval = CellAddress.getCellAddress(rowIndex, columnIndex
				+ startingColumnIndex);
		if (logger.isDebugEnabled()) {
			logger.debug(retval);
		}
		return retval;
	}

	/**
	 * Will return all variables names that are enclosed in { } characters.
	 * 
	 * For example "Little ${MARY} had a {little} ${lamb " returns - [MARY,
	 * little]
	 * 
	 * @param input
	 * @return
	 */
	public static Set<String> getVariableNames(final String input) {
		final Set<String> vars = new HashSet<String>();
		final Matcher m = pattern.matcher(input);
		while (m.find()) {
			final String varName = m.group(1);
			vars.add(varName);
		}
		return vars;
	}

}
