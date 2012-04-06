package org.javautil.jdbc.metadata;

public class PackageSpecification extends AbstractDatabaseObject {

	public PackageSpecification(final String owner, final String name) {
		super(owner, name, DatabaseObjectType.PACKAGE_SPECIFICATION);

	}

	@Override
	public DatabaseObjectType getDatabaseObjectType() {
		return DatabaseObjectType.PACKAGE_SPECIFICATION;
	}

}
