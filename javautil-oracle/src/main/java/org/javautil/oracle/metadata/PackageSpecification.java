package org.javautil.oracle.metadata;

import org.javautil.jdbc.metadata.AbstractDatabaseObject;
import org.javautil.jdbc.metadata.DatabaseObjectType;


public class PackageSpecification extends AbstractDatabaseObject {

    public PackageSpecification(String owner, String name) {
	super(owner, name, DatabaseObjectType.PACKAGE_SPECIFICATION);

    }

    
    @Override
    public DatabaseObjectType getDatabaseObjectType() {
	return DatabaseObjectType.PACKAGE_SPECIFICATION;
    }


   

}
