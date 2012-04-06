package org.javautil.jdbc.metadata;

import org.javautil.collections.NullCompare;

public class DatabaseObjectId implements Comparable<DatabaseObjectId>{
	private String schemaName;
	
	private String objectName;
	
	private static NullCompare nullCompare = new NullCompare();

	public DatabaseObjectId(String schemaName, String objectName) {
		super();
		this.schemaName = schemaName;
		this.objectName = objectName;
	}

	/**
	 * @return the schemaName
	 */
	public String getSchemaName() {
		return schemaName;
	}

	/**
	 * @param schemaName the schemaName to set
	 */
	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	/**
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((objectName == null) ? 0 : objectName.hashCode());
		result = prime * result
				+ ((schemaName == null) ? 0 : schemaName.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseObjectId other = (DatabaseObjectId) obj;
		if (objectName == null) {
			if (other.objectName != null)
				return false;
		} else if (!objectName.equals(other.objectName))
			return false;
		if (schemaName == null) {
			if (other.schemaName != null)
				return false;
		} else if (!schemaName.equals(other.schemaName))
			return false;
		return true;
	}

	public int compareTo(DatabaseObjectId otherId ) {
		int returnValue = nullCompare.compare(this,otherId);
		
		if (returnValue == 0) {
			returnValue = nullCompare.compare(this.getSchemaName(),otherId.getSchemaName());
		}
		if (returnValue == 0) {
			returnValue = nullCompare.compare(this.getObjectName(),otherId.getObjectName());
		}
		return returnValue;
	}
}
