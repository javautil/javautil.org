package org.javautil.jdbc.metadata;

import java.util.HashMap;

public enum DbObjectType {

	FUNCTION, INDEX, LOB, PACKAGE, PACKAGE_BODY, PROCEDURE, SEQUENCE, TABLE, TYPE;

	public static final String revision = "$Revision: 1.1 $";

	private static HashMap<String, DbObjectType> mapping = new HashMap<String, DbObjectType>();
	static {

		mapping.put("FUNCTION", FUNCTION);
		mapping.put("INDEX", INDEX);
		mapping.put("PACKAGE", PACKAGE);
		mapping.put("PACKAGE_BODY", PACKAGE_BODY);
		mapping.put("PROCEDURE", PROCEDURE);
		mapping.put("SEQUENCE", SEQUENCE);
		mapping.put("TABLE", TABLE);
		mapping.put("TYPE", TYPE);

	}

	public static DbObjectType getCommandType(final String token) {
		DbObjectType returnValue;
		String command = null;
		if (token == null) {
			throw new IllegalArgumentException("token is null");
		}

		command = token.toUpperCase();

		if (command.endsWith(";")) {
			command = command.substring(0, command.indexOf(";"));
			// logger.info("command trimmed to " + command);
		}

		returnValue = mapping.get(command);

		if (returnValue == null) {
			throw new IllegalArgumentException("unknown command " + token);
		}
		return returnValue;
	}

}
