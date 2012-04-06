package org.javautil.jdbc.metadata;

import org.dom4j.Element;
import org.javautil.jdbc.metadata.renderer.XmlMeta;

public class ForeignKeyColumn {

	private String pkcolumnName;

	private String fkcolumnName;
	private short keySeq;

	public ForeignKeyColumn() {

	}

	public ForeignKeyColumn(final Element el) {
		fkcolumnName = el.attributeValue(XmlMeta.FK_COLUMN_NAME);
		pkcolumnName = el.attributeValue(XmlMeta.PK_COLUMN_NAME);

	}

	public String getFkcolumnName() {
		return fkcolumnName;
	}

	public short getKeySeq() {
		return keySeq;
	}

	public String getPkcolumnName() {
		return pkcolumnName;
	}

	public void setFkcolumnName(final String fkcolumnName) {
		this.fkcolumnName = fkcolumnName;
	}

	public void setKeySeq(final short keySeq) {
		this.keySeq = keySeq;
	}

	public void setPkcolumnName(final String pkcolumnName) {
		this.pkcolumnName = pkcolumnName;
	}

}
