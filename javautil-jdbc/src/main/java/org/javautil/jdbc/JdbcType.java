package org.javautil.jdbc;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.HashMap;

/**
 * @todo this duplicates a whole bunch of shit.
 * 
 * 
 * @author jim
 * 
 */
public enum JdbcType {
	STRING, INT, LONG, DOUBLE, FLOAT, NUMERIC, SQLDATE, TIMESTAMP, INTEGER, DOUBLE_OBJECT, LONG_OBJECT, BIG_DECIMAL, CLOB, VARBINARY, DATE;

	private static HashMap<String, JdbcType> mapping;

	static {
		mapping = new HashMap<String, JdbcType>();
		mapping.put("STRING", STRING);
		mapping.put("INT", INT);
		mapping.put("LONG", LONG);
		mapping.put("FLOAT", FLOAT);
		mapping.put("DOUBLE", DOUBLE);
		mapping.put("SQLDATE", SQLDATE);
		mapping.put("TIMESTAMP", TIMESTAMP);
		mapping.put("NUMERIC", NUMERIC);
		mapping.put("DATE", DATE);
		mapping.put("VARCHAR2", STRING);
		mapping.put("VARCHAR", STRING);
		mapping.put("NUMBER", NUMERIC);
	}

	// private HashMap<String, JdbcType> mapping = {
	// {"STRING",this.STRING},
	// {"INT",JdbcType.INT},
	// {"LONG",JdbcType.LONG},
	// {"FLOAT",JdbcType.FLOAT},
	// {"DOUBLE",JdbcType.DOUBLE},
	// {"SQLDATE",JdbcType.SQLDATE},
	// {"TIMESTAMP",JdbcType.TIMESTAMP}
	// };

	// TODO is this duplicated in JdbcToJavaImpl?
	@SuppressWarnings("incomplete-switch")
	public static String getJavaObjectType(final String columnName,
			final String typeName, final short dataType, final int columnSize,
			final int decimalDigits) {
		String returnValue = null;

		if (typeName.equals("LONG")) {
			returnValue = "CLOB";
		}
		if (typeName.equals("NUMBER")) {

			switch (getNumberType(columnSize, decimalDigits)) {
			case INT:
				returnValue = "Integer";
				break;
			case LONG:
				returnValue = "Long";
				break;
			case DOUBLE:
				returnValue = "Double";
				break;
			case BIG_DECIMAL:
				returnValue = "BigDecimal";
				break;
			}

		} else {
			switch (dataType) {

			case Types.CHAR:
			case Types.VARCHAR:
				returnValue = "String";
				break;
			case Types.TIMESTAMP:
				returnValue = "Timestamp";
				break;
			case Types.DATE:
				returnValue = "Date";
				break;
			case Types.CLOB:
				returnValue = "CLOB";
				break;
			case Types.VARBINARY:
				returnValue = "VARBINARY";
				break;
			default:
				throw new java.lang.IllegalArgumentException("unknown type "
						+ dataType);
			}
		}
		if (returnValue == null) {

			throw new java.lang.IllegalArgumentException("unknown type type: "
					+ dataType);
		}

		// logger.debug("column: " + label + " precision: " + precision +
		// " scale: " + scale + " type " + typeDescription);
		return returnValue;
	}

	public static JdbcType getJdbcType(final String typeName) {
		final String upper = typeName.toUpperCase();
		String normalized = upper;
		final int openParenIndex = upper.indexOf("(");
		if (openParenIndex > -1) {
			normalized = upper.substring(0, openParenIndex);
		}
		final JdbcType returnValue = mapping.get(normalized);
		if (returnValue == null) {
			throw new IllegalArgumentException("unmapped type " + typeName);
		}
		return returnValue;
	}

	/**
	 * @deprecated use JdbcToJavaImpl
	 */
	@Deprecated
	public static JdbcType getNativeType(final String typeName,
			final short dataType, final int columnSize, final int decimalDigits) {
		JdbcType returnValue = null;

		if (typeName.equals("LONG")) {
			returnValue = CLOB;
		}
		if (typeName.equals("NUMBER")) {
			returnValue = getNumberType(columnSize, decimalDigits);
		} else {
			switch (dataType) {
			case Types.CHAR:
			case Types.VARCHAR:
				returnValue = STRING;
				break;
			case Types.TIMESTAMP:
				returnValue = TIMESTAMP;
				break;
			case Types.DATE:
				returnValue = SQLDATE;
				break;
			case Types.CLOB:
				returnValue = CLOB;
				break;
			case Types.VARBINARY:
				returnValue = VARBINARY;
				break;
			case Types.NUMERIC:
				break;
			default:
				throw new java.lang.IllegalArgumentException("unknown type "
						+ dataType);
			}
		}
		if (returnValue == null) {

			throw new java.lang.IllegalArgumentException("unknown type type: "
					+ dataType);
		}

		// logger.debug("column: " + label + " precision: " + precision +
		// " scale: " + scale + " type " + typeDescription);
		return returnValue;
	}

	/**
	 * @deprecated use JdbcToJavaImpl
	 * @param columnSize
	 * @param decimalDigits
	 * @return
	 */

	@Deprecated
	public static JdbcType getNumberType(final int columnSize,
			final int decimalDigits) {
		JdbcType returnValue;

		if (columnSize == 0 && decimalDigits == 0 || columnSize == 22) {
			returnValue = BIG_DECIMAL;
		} else if (decimalDigits != 0) {
			returnValue = DOUBLE;
		} else {
			if (columnSize < 10) {
				returnValue = INT;
			} else {
				if (columnSize < 19) {
					returnValue = LONG;
				} else {
					returnValue = BIG_DECIMAL;
				}
			}
		}

		return returnValue;

	}

	public static String getSqlDataType(final String columnName,
			final String typeName, final int columnSize, final int decimalDigits) {
		String returnValue = null;
		if (typeName.equals("NUMBER")) {
			if (decimalDigits == 0) {
				if (columnSize == 0) {
					returnValue = "number";
				} else {
					returnValue = "number(" + columnSize + ")";
				}
			} else {
				returnValue = " number(" + columnSize + "," + decimalDigits
						+ ")";
			}
		}
		if (typeName.equals("VARCHAR2") || typeName.equals("VARCHAR")
				|| typeName.equals("CHAR")) {
			returnValue = typeName.toLowerCase() + "(" + columnSize + ")";
		}
		if (typeName.equals("DATE") || typeName.equals("TIMESTAMP")
				|| typeName.equals("CLOB") || typeName.equals("VARBINARY")) {
			returnValue = typeName.toLowerCase();
		}
		if (returnValue == null) {

			throw new java.lang.IllegalArgumentException(columnName
					+ " is an unsupported variable type '" + typeName + "'");

		}
		return returnValue;
	}

	public Object getAsObject(final String value) {
		switch (this) {
		case CLOB:
		case STRING:
			return value;
		case INT:
		case INTEGER:
			return new Integer(value);
		case LONG_OBJECT:
		case LONG:
			return new Long(value);
		case DOUBLE_OBJECT:
		case DOUBLE:
			return new Double(value);
		case NUMERIC:
			try {
				return new BigDecimal(value);
			} catch (final NumberFormatException nfe) {
				throw new IllegalArgumentException(
						"number format exception while processing '" + value
								+ "' ");
			}
		case SQLDATE:
			return Date.valueOf(value);
		case TIMESTAMP:
			return Timestamp.valueOf(value);
		case BIG_DECIMAL:
			return new BigDecimal(value);
		case DATE:
			return Date.valueOf(value);
		default:
			throw new IllegalStateException("unmapped enum value " + this);

		}
	}

	public int getSqlType() {
		switch (this) {
		case STRING:
			return Types.VARCHAR;
		case INT:
			return Types.INTEGER;
		case LONG:
			return Types.NUMERIC;
		case DOUBLE:
			return Types.DOUBLE;
		case SQLDATE:
			return Types.DATE;
		case TIMESTAMP:
			return Types.TIMESTAMP;
		case INTEGER:
			return Types.INTEGER;
		case DOUBLE_OBJECT:
			return Types.DOUBLE;
		case LONG_OBJECT:
			return Types.NUMERIC;
		case BIG_DECIMAL:
			return Types.NUMERIC;
		case CLOB:
			return Types.CLOB;
		case VARBINARY:
			return Types.VARBINARY;
		default:
			throw new IllegalStateException("unmapped enum value " + this);
		}
	}
}
