package org.javautil.oracle.dto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.javautil.jdbc.metadata.DDL;
import org.javautil.jdbc.metadata.DatabaseObject;
import org.javautil.jdbc.metadata.DatabaseObjectType;
import org.javautil.oracle.dao.OracleObjectDAO;

/**
 * @author jjs
 */
public class OracleObject implements DatabaseObject {
	private final Logger logger = Logger.getLogger(getClass());
	private static final String newline = System.getProperty("line.separator");
	/** Container for the data persisted in OWNER. varchar2(30) */
	private String owner = null;

	/** Container for the data persisted in OBJECT_NAME. varchar2(128) */
	private String objectName = null;

	/** Container for the data persisted in SUBOBJECT_NAME. varchar2(30) */
	private String subobjectName = null;

	/** Container for the data persisted in OBJECT_ID. number(22) */
	private long objectId;

	/** Container for the data persisted in DATA_OBJECT_ID. number(22) */
	private long dataObjectId;

	/** Container for the data persisted in OBJECT_TYPE. varchar2(19) */
	private String objectType = null;

	/** Container for the data persisted in CREATED. date */
	private Timestamp created = null;

	/** Container for the data persisted in LAST_DDL_TIME. date */
	private Timestamp lastDdlTime = null;

	/** Container for the data persisted in TIMESTAMP. varchar2(19) */
	private String timestamp = null;

	/** Container for the data persisted in STATUS. varchar2(7) */
	private String status = null;

	/** Container for the data persisted in TEMPORARY. varchar2(1) */
	private String temporary = null;

	/** Container for the data persisted in GENERATED. varchar2(1) */
	private String generated = null;

	/** Container for the data persisted in SECONDARY. varchar2(1) */
	private String secondary = null;

	private DDL ddl;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#setOwner(java.lang.String)
	 */
	public void setOwner(final String val) {
		owner = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getOwner()
	 */
	@Override
	public String getOwner() {
		return owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setObjectName(java.lang.String)
	 */
	public void setObjectName(final String val) {
		objectName = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getObjectName()
	 */
	public String getObjectName() {
		return objectName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setSubobjectName(java.lang.String
	 * )
	 */
	public void setSubobjectName(final String val) {
		subobjectName = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getSubobjectName()
	 */
	public String getSubobjectName() {
		return subobjectName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#setObjectId(long)
	 */
	public void setObjectId(final long val) {
		objectId = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getObjectId()
	 */
	public long getObjectId() {
		return objectId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#setDataObjectId(long)
	 */
	public void setDataObjectId(final long val) {
		dataObjectId = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getDataObjectId()
	 */
	public long getDataObjectId() {
		return dataObjectId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setObjectType(java.lang.String)
	 */
	public void setObjectType(final String val) {
		objectType = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getObjectType()
	 */
	public String getObjectType() {
		return objectType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setCreated(java.sql.Timestamp)
	 */
	public void setCreated(final Timestamp val) {
		created = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getCreated()
	 */
	public Timestamp getCreated() {
		return created;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setLastDdlTime(java.sql.Timestamp
	 * )
	 */
	public void setLastDdlTime(final Timestamp val) {
		lastDdlTime = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getLastDdlTime()
	 */
	public Timestamp getLastDdlTime() {
		return lastDdlTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setTimestamp(java.lang.String)
	 */
	public void setTimestamp(final String timestamp) {
		this.timestamp = timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getTimestamp()
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#setStatus(java.lang.String)
	 */
	public void setStatus(final String val) {
		status = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getStatus()
	 */
	public String getStatus() {
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setTemporary(java.lang.String)
	 */
	public void setTemporary(final String val) {
		temporary = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getTemporary()
	 */
	public String getTemporary() {
		return temporary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setGenerated(java.lang.String)
	 */
	public void setGenerated(final String val) {
		generated = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getGenerated()
	 */
	public String getGenerated() {
		return generated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setSecondary(java.lang.String)
	 */
	public void setSecondary(final String val) {
		secondary = val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getSecondary()
	 */
	public String getSecondary() {
		return secondary;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.oracle.dto.DatabaseObject#getDictionaryType()
	 */
	public DatabaseObjectType getDictionaryType() {
		return DatabaseObjectType.getDictionaryType(objectType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.oracle.dto.DatabaseObject#setValues(java.sql.ResultSet)
	 */
	public void setValues(final ResultSet rset) throws SQLException {
		OracleObjectDAO.getRow(rset, this);
	}

	@Override
	public String getName() {

		return objectName;
	}

	public DDL populateDDL(final Connection conn) throws SQLException {
		ddl = new DDL(this, conn);

		if (logger.isDebugEnabled()) {
			final String message = this.toString() + newline
					+ ddl.getTrimmedText();
			logger.debug(message);
		}

		return ddl;

	}

	@Override
	public DDL getDDL() {

		return ddl;
	}

	@Override
	public boolean isFullyQualified() {
		final boolean retval = owner != null && objectName != null;
		return retval;
	}

	@Override
	public DatabaseObjectType getDatabaseObjectType() {
		final DatabaseObjectType dot = DatabaseObjectType
				.getDictionaryType(objectType);
		return dot;
	}

	@Override
	public String toString() {
		return objectType + " " + "\"" + owner + "\".\"" + objectName + "\"";
	}

	@Override
	public void setDDL(final DDL ddl) {
		this.ddl = ddl;

	}

}
