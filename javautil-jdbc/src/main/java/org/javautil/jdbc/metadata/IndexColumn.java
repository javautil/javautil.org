package org.javautil.jdbc.metadata;

/**
 * 
 * @author jim
 */
public class IndexColumn {
	private String tableCat;
	private String tableSchem;
	private String tableName;
	private boolean nonUnique;
	private String indexQualifier;
	private String indexName;
	private short type;
	private short ordinalPosition;
	private String columnName;
	/**
	 * @todo need to convert to boolean value
	 */
	private String ascOrDesc;
	private int cardinality;
	private int pages;
	private String filterCondition;

	public IndexColumn() {
	}

	public String getAscOrDesc() {
		return ascOrDesc;
	}

	public int getCardinality() {
		return cardinality;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public String getIndexName() {
		return indexName;
	}

	public String getIndexQualifier() {
		return indexQualifier;
	}

	public short getOrdinalPosition() {
		return ordinalPosition;
	}

	public int getPages() {
		return pages;
	}

	public String getTableCat() {
		return tableCat;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTableSchem() {
		return tableSchem;
	}

	public short getType() {
		return type;
	}

	public boolean isNonUnique() {
		return nonUnique;
	}

	public void setAscOrDesc(final String ascOrDesc) {
		this.ascOrDesc = ascOrDesc;
	}

	public void setCardinality(final int cardinality) {
		this.cardinality = cardinality;
	}

	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	public void setFilterCondition(final String filterCondition) {
		this.filterCondition = filterCondition;
	}

	public void setIndexName(final String indexName) {
		this.indexName = indexName;
	}

	public void setIndexQualifier(final String indexQualifier) {
		this.indexQualifier = indexQualifier;
	}

	public void setNonUnique(final boolean nonUnique) {
		this.nonUnique = nonUnique;
	}

	public void setOrdinalPosition(final short ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}

	public void setPages(final int pages) {
		this.pages = pages;
	}

	public void setTableCat(final String tableCat) {
		this.tableCat = tableCat;
	}

	public void setTableName(final String tableName) {
		this.tableName = tableName;
	}

	public void setTableSchem(final String tableSchem) {
		this.tableSchem = tableSchem;
	}

	public void setType(final short type) {
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndexColumn [tableCat=" + tableCat + ", tableSchem="
				+ tableSchem + ", tableName=" + tableName + ", nonUnique="
				+ nonUnique + ", indexQualifier=" + indexQualifier
				+ ", indexName=" + indexName + ", type=" + type
				+ ", ordinalPosition=" + ordinalPosition + ", columnName="
				+ columnName + ", ascOrDesc=" + ascOrDesc + ", cardinality="
				+ cardinality + ", pages=" + pages + ", filterCondition="
				+ filterCondition + "]";
	}

}
