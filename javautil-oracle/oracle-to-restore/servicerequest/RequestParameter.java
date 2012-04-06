package com.dbexperts.oracle.servicerequest;

public class RequestParameter {

    public static int VARCHAR = 1;
    public static int DOUBLE = 2;
    public static int DATE = 3;

    String name = null;
    String value = null;
    int type = -1;

    public RequestParameter(final String name, final String value, final String type)
            throws java.lang.IllegalArgumentException {
        this.name = name;
        this.value = value;
        if (type.equals("V")) {
            this.type = VARCHAR;
        } else if (type.equals("N")) {
            this.type = DOUBLE;
        } else if (type.equals("D")) {
            this.type = DATE;
        } else {
            throw new java.lang.IllegalArgumentException(name + ": has unsupported type: " + type);
        }

    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
	public String toString() {
        return name + ": " + value;
    }

    /*
    public String toXml()
    {
        return
            "<RequestParameter>\n" +
"  <name>" + name + "<name>\n"  +
"  <value>" + value + "<value>\n" +
            "</RequestParameter>\n";

    }
    */
    public String toXml() {
        return "  <" + name + " '" + value + "' />\n";

    }
}
