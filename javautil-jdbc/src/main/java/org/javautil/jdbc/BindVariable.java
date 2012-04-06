package org.javautil.jdbc;

import java.sql.Types;

public class BindVariable {
	private String name = null;
	private String typeText = "string";
	private int sqlType;
	private JdbcType jdbcType;

	public BindVariable(final String varName, final JdbcType type) {
		this.name = varName.toUpperCase();
		this.jdbcType = type;

	}

	BindVariable(final String varName) {
		this.name = varName.toUpperCase();
	}

	// public BindVariable(String varName,String type) {
	// this.name = varName;
	// setSqlType(type);
	// }
	BindVariable(final String varName, final int type) {
		this.name = varName.toUpperCase();
		setSqlType(type);
	}

	public String getBindName() {
		return name;
	}

	public JdbcType getJdbcType() {
		return jdbcType;
	}

	public String getTypeText() {
		return typeText;
	}

	public void setJdbcType(final JdbcType type) {
		if (type == null) {
			throw new IllegalArgumentException("type is null");
		}
		jdbcType = type;

	}

	public int getSqlType() {
		return sqlType;
	}

	public void setSqlType(final int v) {
		switch (v) {
		case Types.DATE:
		case Types.TIMESTAMP:
		case Types.VARCHAR:
		case Types.NUMERIC:
			break;
		default:
			throw new java.lang.IllegalArgumentException(
					"unsupported bind type " + v);
		}
	}

	public void setTypeText(final String typeText) {
		if (typeText == null) {
			throw new java.lang.IllegalArgumentException("typeText is null");
		}
		this.typeText = typeText;
	}
}
