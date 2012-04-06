package org.javautil.jdbc.metadata.renderer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.javautil.jdbc.metadata.Messages;
import org.javautil.jdbc.metadata.dao.TableJdbc;

/**
 * @todo create OracleTable that extends this.
 */

public class TableXMLMarshaller {

	private static final String newline = System.getProperty("line.separator");

	// todo migrate xml representation out of this class
	@SuppressWarnings("unchecked")
	public static String getCreatePrimaryKey(final Element table) {
		final StringBuilder b = new StringBuilder();
		String result = null;
		final Element pk = (Element) table
				.selectSingleNode("./" + XmlMeta.PRIMARY_KEY); //$NON-NLS-1$
		if (pk != null) {
			b.append("alter table ");
			b.append(table.attributeValue(XmlMeta.TABLE_NAME));
			b.append(" add constraint ");
			b.append(pk.attributeValue(XmlMeta.CONSTRAINT_NAME));
			b.append(" primary key (\n");
			final XPath xpathSelector = DocumentHelper.createXPath("./"
					+ XmlMeta.COLUMN);
			final List<Element> columns = xpathSelector.selectNodes(pk);

			boolean first = true;
			for (final Element column : columns) {
				if (!first) {
					b.append(",\n");
				}
				b.append("   ");
				final String columnName = getAttributeValue(column,
						XmlMeta.COLUMN_NAME);
				b.append(columnName);
				first = false;
			}
			b.append("\n)");
			result = b.toString();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	public static String getCreateStatement(final Element el) {
		final Logger logger = Logger.getLogger(TableJdbc.class.getName());
		final StringBuilder b = new StringBuilder();
		final XPath xpathSelector = DocumentHelper.createXPath(Messages
				.getString("Table.fieldXpath")); //$NON-NLS-1$
		final List<Element> columns = xpathSelector.selectNodes(el);
		b.append("create table ");
		b.append(getAttributeValue(el, XmlMeta.TABLE_NAME));
		b.append(" ( ");
		b.append(newline);
		boolean first = true;
		String attribute = null;
		for (final Element column : columns) {

			if (!first) {
				b.append(",");
				b.append(newline);
			}
			b.append("   ");
			attribute = getAttributeValue(column, XmlMeta.COLUMN_NAME);
			b.append(attribute);
			b.append(" ");
			b.append(getAttributeValue(column, XmlMeta.SQL_TYPE));
			attribute = column.attributeValue(XmlMeta.NULLABLE);
			if (attribute != null) {
				b.append(" ");
				b.append(attribute);
			}
			first = false;

		}
		b.append(newline);
		b.append(")");
		final String result = b.toString();
		logger.info(result);
		return result;

	}

	// @todo should use resource for relation and type and cardinality
	public static String getImportedRelationXpath() {
		return "./relation[@type='one']";
	}

	public static String getTableName(final Element el) {
		if (el == null) {
			throw new IllegalArgumentException("el is null");
		}
		return getAttributeValue(el, XmlMeta.TABLE_NAME);
	}

	// todo this is nasty create separate class for this
	private static String getAttributeValue(final Element el,
			final String attributeName) {

		if (el == null) {
			throw new IllegalArgumentException("el is null"); //$NON-NLS-1$
		}
		final String value = el.attributeValue(attributeName);
		if (value == null) {
			throw new IllegalArgumentException(
					"no attribute " + attributeName + " in element " + el.asXML()); //$NON-NLS-1$ //$NON-NLS-2$
		}

		return value;
	}

	public static ArrayList<String> getCreateForeignKeys(final Element table) {
		final Logger logger = Logger.getLogger(TableJdbc.class.getName());
		final ArrayList<String> sqlTexts = new ArrayList<String>();

		final XPath fkPath = DocumentHelper
				.createXPath(getImportedRelationXpath());
		final XPath xpathSelector = DocumentHelper.createXPath("./"
				+ XmlMeta.KEYMAP);
		final List<Element> fks = fkPath.selectNodes(table);
		for (final Element fk : fks) {
			final StringBuilder fkb = new StringBuilder();
			fkb.append("alter table ");
			fkb.append(table.attributeValue(XmlMeta.TABLE_NAME));
			fkb.append(" add constraint ");
			fkb.append(fk.attributeValue(XmlMeta.CONSTRAINT_NAME));
			fkb.append(" foreign key (\n");

			final List<Element> columns = xpathSelector.selectNodes(fk);

			boolean first = true;
			for (final Element column : columns) {
				if (!first) {
					fkb.append(",\n");
				}
				fkb.append("   ");
				final String columnName = getAttributeValue(column,
						XmlMeta.FK_COLUMN_NAME);
				fkb.append(columnName);
				first = false;
			}
			fkb.append("\n) references  ");
			fk.attribute(XmlMeta.FOREIGN_TABLE_NAME);
			fkb.append(" ( ");
			first = true;
			for (final Element column : columns) {
				if (!first) {
					fkb.append(",\n");
				}
				fkb.append("   ");
				final String columnName = getAttributeValue(column,
						XmlMeta.PK_COLUMN_NAME);
				fkb.append(columnName);
				first = false;
			}
			fkb.append(")");
			final String result = fkb.toString();
			logger.info(result);
			sqlTexts.add(result);
		}
		return sqlTexts;
	}

}
