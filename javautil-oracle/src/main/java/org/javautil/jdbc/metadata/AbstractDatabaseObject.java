package org.javautil.jdbc.metadata;
// TODO should this be in javautil-jdbc?
public abstract class AbstractDatabaseObject implements DatabaseObject {
	private String owner;
	private String name;
	 private DatabaseObjectType objectType;
	private DDL ddl;

	public AbstractDatabaseObject(final String owner, final String name,
			final DatabaseObjectType type) {
		this.owner = owner;
		this.name = name;
		 this.objectType=type;
	}


	@Override
	public String getOwner() {
		return owner;
	}

	public void setOwner(final String owner) {
		this.owner = owner;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	 public DatabaseObjectType getObjectType() {
	 return objectType;
	 }
	 public void setObjectType(DatabaseObjectType objectType) {
	 this.objectType = objectType;
	 }
	@Override
	public DDL getDDL() {
		return ddl;
	}

	@Override
	public void setDDL(final DDL ddl) {
		this.ddl = ddl;
	}

	// public void setSource(DDL source) {
	// this.source = source;
	// }

	@Override
	public boolean isFullyQualified() {
		return owner != null && name != null;
	}

}
