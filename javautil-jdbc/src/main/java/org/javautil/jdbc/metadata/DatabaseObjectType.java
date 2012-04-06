package org.javautil.jdbc.metadata;

import org.apache.log4j.Logger;

public enum DatabaseObjectType {
	CLUSTER, CONTEXT, DIMENSION, DIRECTORY, EVALUATION_CONTEXT, FUNCTION, GRANT, INDEX, INDEX_SUBPARTITION, INDEXTYPE, JAVA_CLASS, JAVA_RESOURCE, JAVA_SOURCE, JOB, JOB_CLASS, LIBRARY, LOB, LOB_SUBPARTITION, MATERIALIZED_VIEW, PACKAGE_SPECIFICATION, PACKAGE_BODY, PROCEDURE, PROGRAM, RESOURCE_PLAN, RULE_SET, SEQUENCE, SYNONYM, TABLE, TABLE_PARTITION, TABLE_SUBPARTITION, TRIGGER, TYPE, TYPE_BODY, UNDEFINED, VIEW, WINDOW_GROUP, XML_SCHEMA;

	private static Logger logger = Logger.getLogger(DatabaseObjectType.class);

	public static DatabaseObjectType getDictionaryType(final String in) {
		if (in == null) {
			throw new IllegalArgumentException("in is null");
		}
		String subst = in.replace(" ", "_");
		return DatabaseObjectType.valueOf(subst.toUpperCase());
		// DatabaseObjectType retval = null;
		// while (true) {
		// if ("CLUSTER".equalsIgnoreCase(in)) {
		// retval = CLUSTER;
		// break;
		// }
		//
		// if ("CONTEXT".equalsIgnoreCase(in)) {
		// retval = CONTEXT;
		// break;
		// }
		//
		// if ("DIMENSION".equalsIgnoreCase(in)) {
		// retval = DIMENSION;
		// break;
		// }
		//
		// if ("DIRECTORY".equalsIgnoreCase(in)) {
		// retval = DIRECTORY;
		// break;
		// }
		//
		// if ("EVALUATION CONTEXT".equalsIgnoreCase(in)
		// || "EVALUATION_CONTEXT".equalsIgnoreCase(in)) {
		// retval = EVALUATION_CONTEXT;
		// break;
		// }
		//
		// if ("FUNCTION".equalsIgnoreCase(in)) {
		// retval = FUNCTION;
		// break;
		// }
		//
		// if ("INDEX".equalsIgnoreCase(in)) {
		// retval = INDEX;
		// break;
		// }
		//
		// if ("INDEX SUBPARTITION".equalsIgnoreCase(in)
		// || "INDEX_SUBPARTITION".equalsIgnoreCase(in)) {
		// retval = INDEX_SUBPARTITION;
		// break;
		// }
		//
		// if ("INDEXTYPE".equalsIgnoreCase(in)) {
		// retval = INDEXTYPE;
		// break;
		// }
		//
		// if ("JAVA CLASS".equalsIgnoreCase(in)
		// || "JAVA_CLASS".equalsIgnoreCase(in)) {
		// retval = JAVA_CLASS;
		// break;
		// }
		//
		// if ("JAVA RESOURCE".equalsIgnoreCase(in)
		// || "JAVA_RESOURCE".equalsIgnoreCase(in)) {
		// retval = JAVA_RESOURCE;
		// break;
		// }
		//
		// if ("JAVA SOURCE".equalsIgnoreCase(in)
		// || "JAVA_SOURCE".equalsIgnoreCase(in)) {
		// retval = JAVA_SOURCE;
		// break;
		// }
		//
		// if ("JOB".equalsIgnoreCase(in)) {
		// retval = JOB;
		// break;
		// }
		//
		// if ("JOB CLASS".equalsIgnoreCase(in)
		// || "JOB_CLASS".equalsIgnoreCase(in)) {
		// retval = JOB_CLASS;
		// break;
		// }
		//
		// if ("LIBRARY".equalsIgnoreCase(in)) {
		// retval = LIBRARY;
		// break;
		// }
		//
		// if ("LOB".equalsIgnoreCase(in)) {
		// retval = LOB;
		// break;
		// }
		//
		// if ("LOB SUBPARTITION".equalsIgnoreCase(in)
		// || "LOB_SUBPARTITION".equalsIgnoreCase(in)) {
		// retval = LOB_SUBPARTITION;
		// break;
		// }
		//
		// if ("MATERIALIZED VIEW".equalsIgnoreCase(in)
		// || "MATERIALIZED_VIEW".equalsIgnoreCase(in)) {
		// retval = MATERIALIZED_VIEW;
		// break;
		// }
		//
		// if ("PACKAGE".equalsIgnoreCase(in)) {
		// retval = PACKAGE_SPECIFICATION;
		// break;
		// }
		//
		// if ("PACKAGE BODY".equalsIgnoreCase(in)
		// || "PACKAGE_BODY".equalsIgnoreCase(in)) {
		// retval = PACKAGE_BODY;
		// break;
		// }
		//
		// if ("PROCEDURE".equalsIgnoreCase(in)) {
		// retval = PROCEDURE;
		// break;
		// }
		// if ("PROGRAM".equalsIgnoreCase(in)) {
		// retval = PROGRAM;
		// break;
		// }
		//
		// if ("RESOURCE PLAN".equalsIgnoreCase(in)
		// || "RESOURCE_PLAN".equalsIgnoreCase(in)) {
		// retval = RESOURCE_PLAN;
		// break;
		// }
		//
		// if ("RULE SET".equalsIgnoreCase(in)
		// || "RULE_SET".equalsIgnoreCase(in)) {
		// retval = RULE_SET;
		// break;
		// }
		//
		// if ("SEQUENCE".equalsIgnoreCase(in)) {
		// retval = SEQUENCE;
		// break;
		// }
		// if ("SYNONYM".equalsIgnoreCase(in)) {
		// retval = SYNONYM;
		// break;
		// }
		//
		// if ("TABLE".equalsIgnoreCase(in)) {
		// retval = TABLE;
		// break;
		// }
		//
		// if ("TABLE SUBPARTITION".equalsIgnoreCase(in)) {
		// retval = TABLE_SUBPARTITION;
		// break;
		// }
		// if ("TRIGGER".equalsIgnoreCase(in)) {
		// retval = TRIGGER;
		// break;
		// }
		//
		// if ("TYPE".equalsIgnoreCase(in)) {
		// retval = TYPE;
		// break;
		// }
		//
		// if ("TYPE BODY".equalsIgnoreCase(in)
		// || "TYPE_BODY".equalsIgnoreCase(in)) {
		// retval = TYPE_BODY;
		// break;
		// }
		//
		// if ("UNDEFINED".equalsIgnoreCase(in)) {
		// retval = UNDEFINED;
		// break;
		// }
		//
		// if ("VIEW".equalsIgnoreCase(in)) {
		// retval = VIEW;
		// break;
		// }
		//
		// if ("WINDOW GROUP".equalsIgnoreCase(in)
		// || "WINDOW_GROUP".equalsIgnoreCase(in)) {
		// retval = WINDOW_GROUP;
		// break;
		// }
		//
		// if ("XML SCHEMA".equalsIgnoreCase(in)
		// || "XML_SCHEMA".equalsIgnoreCase(in)) {
		// retval = XML_SCHEMA;
		// break;
		// }
		// logger.warn("unknown type " + in);
		// break;
		// }
		// return retval;
	}

	/**
	 * 
	 * @return the string that corresponds to the entry in dba_source
	 */
	public String getDbaSourceType() {
		String retval = this.toString().replace("_", " ");
		// TODO delete
		// String retval = null;
		// switch (this) {
		// case PROCEDURE:
		// retval = "PROCEDURE";
		// break;
		// case PACKAGE_SPECIFICATION:
		// retval = "PACKAGE";
		// break;
		// case PACKAGE_BODY:
		// retval = "PACKAGE BODY";
		// break;
		// case TYPE_BODY:
		// retval = "TYPE BODY";
		// break;
		// case TRIGGER:
		// retval = "TRIGGER";
		// break;
		// case FUNCTION:
		// retval = "FUNCTION";
		// break;
		// case JAVA_SOURCE:
		// retval = "JAVA SOURCE";
		// break;
		// }
		return retval;
	}

	public boolean hasSource() {
		boolean retval = false;
		switch (this) {

		case FUNCTION:
		case JAVA_SOURCE:
		case PACKAGE_SPECIFICATION:
		case PACKAGE_BODY:
		case PROCEDURE:
		case TRIGGER:
		case TYPE:
		case TYPE_BODY:
			retval = true;
		}
		return retval;
	}
}
