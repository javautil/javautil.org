package com.dbexperts.oracle.DatabaseMetaData;

import java.sql.Connection;

import org.apache.log4j.Logger;

import com.dbexperts.jdbc.DatabaseMetaData.DatabaseObject;
import com.dbexperts.jdbc.DatabaseMetaData.DatabaseObjectType;
import com.dbexperts.jdbc.DatabaseMetaData.PackageSpecification;

public class OracleDatabaseObjectFactory {
    private Logger logger = Logger.getLogger(getClass());
    public DatabaseObject getOracleDatabaseObject(Connection conn, String owner, String objectName, String typeName) {
	DatabaseObject object = null;
	DatabaseObjectType dot = DatabaseObjectType.getDictionaryType(typeName);
	if (dot == null) {
	    throw new UnsupportedOperationException("type " + typeName + " is not supported");
	}
	switch (dot) {
	case PACKAGE_SPECIFICATION:
	    PackageSpecification ps = new PackageSpecification(owner,objectName);
	    object = ps;
	    break;
	default:
	    logger.warn("unsupported object type " + typeName);
	}
	return object;
    } 
    
   
}
