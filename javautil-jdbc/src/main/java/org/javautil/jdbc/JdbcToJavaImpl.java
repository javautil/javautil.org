package org.javautil.jdbc;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.javautil.dataset.ColumnAttributes;

public class JdbcToJavaImpl implements JdbcToJavaMapper {

	private final JdbcJavaNameImpl namer = new JdbcJavaNameImpl();

	private static Map<String, String> getterMap = new HashMap<String, String>() {
		{
			put("Integer", "getInt");
			put("Long", "getLong");
			put("Date", "getDate");
			put("Timestamp", "getTimestamp");
			put("Double", "getDouble");
			put("BigDecimal", "getBigDecimal");
			put("VARBINARY", "getVarBinary");
			put("String", "getString");
		}
	};

	private static Map<String, String> importMap = new HashMap<String, String>() {
		{
			put("Integer", "");
			put("Long", "");
			put("Date", "java.util.Date");
			put("Timestamp", "java.sql.TimeStamp");
			put("Double", "");
			put("BigDecimal", "");
			put("String", "");
			put("VARBINARY", "");
		}
	};

	@Override
	public String getImportClass(ColumnAttributes column) {
		String objectType = getJavaObjectType(column);
		String retval = getImportClass(objectType);
		return retval;
	}

	/**
	 * Returns the class to be imported or null if no import is necessary.
	 * 
	 * @param javaType
	 * @return
	 */
	public String getImportClass(String javaType) {
		if (javaType == null) {
			throw new IllegalArgumentException("javaType is null");
		}
		String retval = importMap.get(javaType);
		if (retval == null) {
			throw new IllegalArgumentException("unsupported javaType "
					+ javaType);
		}
		if (retval == "") {
			retval = null;
		}
		return retval;
	}

	String argsToString(final String columnName, final String typeName,
			final Integer dataType, final Integer columnSize,
			final Integer decimalDigits) {
		return "columnName: " + columnName + " typeName: " + typeName
				+ " dataType: " + dataType + " columnSize: " + columnSize
				+ " decimalDigits: " + decimalDigits;
	}

	@Override
	public String getJavaObjectType(final String columnName,
			final String typeName, final Integer dataType,
			final Integer columnSize, final Integer decimalDigits) {
		String returnValue = null;

		if (typeName == null) {
			throw new IllegalArgumentException("typeName is null");
		}
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
			default:
				throw new IllegalArgumentException("unsupported type in"
						+ argsToString(columnName, typeName, dataType,
								columnSize, decimalDigits));
			}

		} else {
			switch (dataType) {
			case Types.LONGNVARCHAR:
			case Types.LONGVARCHAR:
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
				throw new IllegalArgumentException("unsupported type: "
						+ dataType
						+ " "
						+ argsToString(columnName, typeName, dataType,
								columnSize, decimalDigits));
			}
		}

		// logger.debug("column: " + label + " precision: " + precision +
		// " scale: " + scale + " type " + typeDescription);
		return returnValue;
	}

	public static JdbcType getNumberType(final int columnSize,
			final int decimalDigits) {
		JdbcType returnValue;

		if (columnSize == 0 && decimalDigits == 0 || columnSize == 22) {
			returnValue = JdbcType.BIG_DECIMAL;
		} else if (decimalDigits != 0) {
			returnValue = JdbcType.DOUBLE;
		} else {
			if (columnSize < 10) {
				returnValue = JdbcType.INT;
			} else {
				if (columnSize < 19) {
					returnValue = JdbcType.LONG;
				} else {
					returnValue = JdbcType.BIG_DECIMAL;
				}
			}
		}

		return returnValue;

	}

	@Override
	public String getJavaObjectType(ColumnAttributes column) {
		if (column == null) {
			throw new IllegalArgumentException("column is null");
		}

		// try {
		String columnName = column.getColumnName();
		String typeName = column.getColumnTypeName();
		Integer columnType = column.getColumnType();
		Integer columnSize = column.getColumnSize();
		Integer scale = column.getScale();
		return getJavaObjectType(columnName, typeName, columnType, columnSize,
				scale);
		// } catch (NullPointerException npe) {
		// npe.printStackTrace();
		// throw new NullPointerException(column.toString());
		// }

	}

	@Override
	public String getJdbcType(ColumnAttributes column) {
		new StringBuilder();
		String objectType = getJavaObjectType(column);
		String type = getterMap.get(objectType);

		if (type == null) {
			throw new IllegalStateException("unknown type: '" + objectType
					+ "'");
		}
		return type;

	}

	@Override
	public String getGetter(ColumnAttributes column) {
		StringBuilder sb = new StringBuilder();
		String objectType = getJavaObjectType(column);
		String type = getterMap.get(objectType);
		sb.append(type);
		if (type == null) {
			throw new IllegalStateException("unknown type: '" + objectType
					+ "'");
		}
		sb.append(" get");
		sb.append(namer.attributeNameInitCap(column.getColumnName()));
		sb.append("() {\n");
		sb.append("return ");
		sb.append(namer.getAttributeName(column.getColumnName()));
		sb.append(";\n");
		sb.append("  }");
		return sb.toString();

	}

}
