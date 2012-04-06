package org.javautil.jdbc.metadata;

import java.util.ArrayList;

public class ForeignKeyImpl implements ForeignKey {
	private String pktableCat;
	private String pktableSchem;
	private String pktableName;

	private String fktableCat;
	private String fktableSchem;
	private String fktableName;

	// private short keySeq;
	private short updateRule;
	private DeleteRule deleteRule;
	// private short deleteRule;
	private String fkName;
	private String pkName;

	private ArrayList<ForeignKeyColumn> columns = new ArrayList<ForeignKeyColumn>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#addColumn(com
	 * .dbexperts.jdbc.DatabaseMetaData.ForeignKeyColumn)
	 */
	@Override
	public void addColumn(final ForeignKeyColumn col) {
		columns.add(col);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getColumns()
	 */
	@Override
	public ArrayList<ForeignKeyColumn> getColumns() {
		return columns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getDeleteRule()
	 */
	@Override
	public DeleteRule getDeleteRule() {
		return deleteRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getFkName()
	 */
	@Override
	public String getFkName() {
		return fkName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getFktableCat()
	 */
	@Override
	public String getFktableCat() {
		return fktableCat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getFktableName()
	 */
	@Override
	public String getFktableName() {
		return fktableName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getFktableSchem()
	 */
	@Override
	public String getFktableSchem() {
		return fktableSchem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getId()
	 */
	@Override
	public String getId() {
		if (fkName == null) {
			throw new IllegalStateException("must setFkName first");
		}
		return fktableSchem + ":" + fkName;
	}

	// /**
	// * @return the keySeq
	// */
	// public short getKeySeq() {
	// return keySeq;
	// }
	// /**
	// * @param keySeq the keySeq to set
	// */
	// public void setKeySeq(short keySeq) {
	// this.keySeq = keySeq;
	// }
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getPkName()
	 */
	@Override
	public String getPkName() {
		return pkName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getPktableCat()
	 */
	@Override
	public String getPktableCat() {
		return pktableCat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getPktableName()
	 */
	@Override
	public String getPktableName() {
		return pktableName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getPktableSchem()
	 */
	@Override
	public String getPktableSchem() {
		return pktableSchem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#getUpdateRule()
	 */
	@Override
	public short getUpdateRule() {
		return updateRule;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setColumns(java
	 * .util.ArrayList)
	 */
	@Override
	public void setColumns(final ArrayList<ForeignKeyColumn> columns) {
		this.columns = columns;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setDeleteRule
	 * (short)
	 */
	@Override
	public void setDeleteRule(final short deleteRule) {
		this.deleteRule = DeleteRule.getDeleteRule(deleteRule);
	}

	public void setDeleteRule(final String deleteRule) {
		this.deleteRule = DeleteRule.getDeleteRule(deleteRule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setFkName(java
	 * .lang.String)
	 */
	@Override
	public void setFkName(final String fkName) {
		this.fkName = fkName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setFktableCat
	 * (java.lang.String)
	 */
	@Override
	public void setFktableCat(final String fktableCat) {
		this.fktableCat = fktableCat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setFktableName
	 * (java.lang.String)
	 */
	@Override
	public void setFktableName(final String fktableName) {
		this.fktableName = fktableName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setFktableSchem
	 * (java.lang.String)
	 */
	@Override
	public void setFktableSchem(final String fktableSchem) {
		this.fktableSchem = fktableSchem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setPkName(java
	 * .lang.String)
	 */
	@Override
	public void setPkName(final String pkName) {
		this.pkName = pkName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setPktableCat
	 * (java.lang.String)
	 */
	@Override
	public void setPktableCat(final String pktableCat) {
		this.pktableCat = pktableCat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setPktableName
	 * (java.lang.String)
	 */
	@Override
	public void setPktableName(final String pktableName) {
		this.pktableName = pktableName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setPktableSchem
	 * (java.lang.String)
	 */
	@Override
	public void setPktableSchem(final String pktableSchem) {
		this.pktableSchem = pktableSchem;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.dbexperts.jdbc.DatabaseMetaData.ForeignKeyInterface#setUpdateRule
	 * (short)
	 */
	@Override
	public void setUpdateRule(final short updateRule) {
		this.updateRule = updateRule;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ForeignKey [pktableCat=" + pktableCat + ", pktableSchem="
				+ pktableSchem + ", pktableName=" + pktableName
				+ ", fktableCat=" + fktableCat + ", fktableSchem="
				+ fktableSchem + ", fktableName=" + fktableName
				+ ", updateRule=" + updateRule + ", deleteRule=" + deleteRule
				+ ", fkName=" + fkName + ", pkName=" + pkName + ", columns="
				+ columns + "]";
	}
}
