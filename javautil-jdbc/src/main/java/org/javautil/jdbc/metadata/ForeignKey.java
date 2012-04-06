package org.javautil.jdbc.metadata;

import java.util.ArrayList;

public interface ForeignKey {

	public abstract void addColumn(final ForeignKeyColumn col);

	/**
	 * @return the columns
	 */
	public abstract ArrayList<ForeignKeyColumn> getColumns();

	/**
	 * @return the deleteRule
	 */
	public abstract DeleteRule getDeleteRule();

	/**
	 * @return the fkName
	 */
	public abstract String getFkName();

	/**
	 * @return the fktableCat
	 */
	public abstract String getFktableCat();

	/**
	 * @return the fktableName
	 */
	public abstract String getFktableName();

	/**
	 * @return the fktableSchem
	 */
	public abstract String getFktableSchem();

	public abstract String getId();

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
	/**
	 * @return the pkName
	 */
	public abstract String getPkName();

	/**
	 * @return the pktableCat
	 */
	public abstract String getPktableCat();

	/**
	 * @return the pktableName
	 */
	public abstract String getPktableName();

	/**
	 * @return the pktableSchem
	 */
	public abstract String getPktableSchem();

	/**
	 * @return the updateRule
	 */
	public abstract short getUpdateRule();

	/**
	 * @param columns
	 *            the columns to set
	 */
	public abstract void setColumns(final ArrayList<ForeignKeyColumn> columns);

	/**
	 * @param deleteRule
	 *            the deleteRule to set
	 */
	public abstract void setDeleteRule(final short deleteRule);

	/**
	 * @param fkName
	 *            the fkName to set
	 */
	public abstract void setFkName(final String fkName);

	/**
	 * @param fktableCat
	 *            the fktableCat to set
	 */
	public abstract void setFktableCat(final String fktableCat);

	/**
	 * @param fktableName
	 *            the fktableName to set
	 */
	public abstract void setFktableName(final String fktableName);

	/**
	 * @param fktableSchem
	 *            the fktableSchem to set
	 */
	public abstract void setFktableSchem(final String fktableSchem);

	/**
	 * @param pkName
	 *            the pkName to set
	 */
	public abstract void setPkName(final String pkName);

	/**
	 * @param pktableCat
	 *            the pktableCat to set
	 */
	public abstract void setPktableCat(final String pktableCat);

	/**
	 * @param pktableName
	 *            the pktableName to set
	 */
	public abstract void setPktableName(final String pktableName);

	/**
	 * @param pktableSchem
	 *            the pktableSchem to set
	 */
	public abstract void setPktableSchem(final String pktableSchem);

	/**
	 * @param updateRule
	 *            the updateRule to set
	 */
	public abstract void setUpdateRule(final short updateRule);

}