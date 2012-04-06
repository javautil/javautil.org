package org.javautil.oracle.servicerequest;

import java.util.Date;

public class ServiceRequestArgument {
	public static final int STRING = 1;
	public static final int DATE = 2;
	public static final int DOUBLE = 3;

	private final String name;
	private final Object value;
	private final int type;

	public ServiceRequestArgument(final String name, final Date value) {
		this.name = name;
		this.value = value;
		type = DATE;
	}

	public ServiceRequestArgument(final String name, final Double value) {
		this.name = name;
		this.value = value;
		type = DOUBLE;
	}

	public ServiceRequestArgument(final String name, final String value) {
		this.name = name;
		this.value = value;
		type = STRING;
	}

	public Date getDate() {
		return (Date) value;
	}

	public Double getDouble() {
		return (Double) value;
	}

	public String getName() {
		return name;
	}

	public String getString() {
		return (String) value;
	}

	public int getType() {
		return type;
	}
}
