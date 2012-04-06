package org.javautil.jdbc.metadata;

public interface DatabaseObjects {

	public String getOwner();

	public String getName();

	public DatabaseObjectType getObjectType();

	public DDL getSource();

}
