package org.javautil.jdbc.metadata.renderer;

//package org.javautil.jdbc.metadata;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.javautil.dataset.ColumnAttributes;
import org.javautil.jdbc.metadata.ForeignKey;
import org.javautil.jdbc.metadata.ForeignKeyColumn;
import org.javautil.jdbc.metadata.PrimaryKey;
import org.javautil.jdbc.metadata.Table;
import org.javautil.jdbc.metadata.containers.ForeignKeys;
import org.javautil.text.StringHelper;

// TODO restore this from my home version or throw it out
// TODO convert to jaxb
public class TableXML {

	private final Logger logger = Logger.getLogger(this.getClass().getName());

	private Element tableElement;

	private Table table;

	private final boolean emitCamelCase = false;

	private final boolean emitSchemaName = false;
	/**
	 * if true be verbose on elements so context is not necessary
	 */
	private final boolean dense = true;

	public TableXML(final Element el) {
		tableElement = el;
	}

	public TableXML(final Table table) {
		this(table, false);
	}

	public TableXML(final Table table, boolean emitSchema) {
		this.table = table;
	}

	public Element getAsElement() {
		tableElement = DocumentHelper.createElement(XmlMeta.ENTITY);
		if (emitCamelCase) {
			tableElement.addAttribute(XmlMeta.ENTITY_NAME,
					StringHelper.attributeName(table.getTableName()));
		}
		if (emitSchemaName) {
			tableElement.addAttribute(XmlMeta.ID, table.getSchemaName() + "."
					+ table.getTableName());
		} else {
			tableElement.addAttribute(XmlMeta.ID, table.getTableName());
		}
		tableElement.addAttribute(XmlMeta.TABLE_TYPE, table.getTableType());
		tableElement.addAttribute(XmlMeta.TABLE_NAME, table.getTableName());
		if (emitSchemaName) {
			tableElement.addAttribute(XmlMeta.SCHEMA, table.getSchemaName());
		}

		writeAttributes();
		writePrimaryKeyColumns();
		writeReferences(table.getImportedKeys(), true, emitSchemaName);
		writeReferences(table.getExportedKeys(), false, emitSchemaName);
		return tableElement;
	}

	// TODO WTF?
	// public Table getAsTable() {
	// if (!elementConstructor) {
	// throw new
	// UnsupportedOperationException("this was constructed with a Table, pointless call");
	// }
	//
	// final String schemaName = tableElement.attributeValue(XmlMeta.SCHEMA);
	// final String tableName = tableElement.attributeValue(XmlMeta.TABLE_NAME);
	// final String remarks = tableElement.attributeValue(XmlMeta.REMARKS);
	// final String tableType = tableElement.attributeValue(XmlMeta.TABLE_TYPE);
	//
	// table = new JDBCTable(schemaName, tableName, remarks, tableType);
	// readAttributes();
	// readPrimaryKeyColumns();
	// readReferences();
	//
	// return table;
	// }

	// TODO restore unmarshaller code
	// private void readAttributes() {
	//
	// final XPath xpathSelector = DocumentHelper.createXPath(XmlMeta.COLUMN);
	// final List<Element> results = xpathSelector.selectNodes(tableElement);
	// final List<ColumnAttributes> columns = new ArrayList<ColumnAttributes>();
	// for (final Iterator<Element> iter = results.iterator(); iter.hasNext();)
	// {
	// final Element element = iter.next();
	// final Column col = new Column(element);
	// columns.add(col);
	// }
	// table.setColumns(columns);
	// }

	// /**
	// * <primary-key column-name="DS_DATASOURCE_NBR"/>
	// * private String schema;
	// private String tableName;
	// private String primaryKeyName;
	// private TreeMap<Integer,String> columns = new TreeMap<Integer,String>();
	// */
	// @SuppressWarnings("unchecked")
	// private void readPrimaryKeyColumns() {
	// PrimaryKey pk = null;
	// final XPath xpathSelector =
	// DocumentHelper.createXPath(XmlMeta.PRIMARY_KEY);
	// final List<Element> results = xpathSelector.selectNodes(tableElement);
	// final ArrayList<Column> columns = new ArrayList<Column>();
	// for (final Iterator<Element> iter = results.iterator(); iter.hasNext();)
	// {
	// final Element element = iter.next();
	// final String schemaName = element.attributeValue(XmlMeta.SCHEMA);
	// final String pkName = element.attributeValue(XmlMeta.CONSTRAINT_NAME);
	//
	// pk = new PrimaryKey(schemaName, table.getTableName(), pkName);
	// int colOrder = 1;
	// for (final Element column : (List<Element>) element.elements()) {
	// final String colName = column.attributeValue(XmlMeta.COLUMN_NAME);
	// pk.addColumn(colName, colOrder++);
	// }
	//
	// // Column col = new Column(element);
	// // columns.add(col);
	// }
	// table.setPrimaryKey(pk);
	//
	// }

	// private void readReferences() {
	// final ForeignKeys imported = new JDBCForeignKeys(); // todo this is goofy
	// should use something else
	// final ForeignKeys exported = new JDBCForeignKeys();
	//
	//
	// final XPath xpathSelector = DocumentHelper.createXPath(XmlMeta.RELATION);
	// final List<Element> results = xpathSelector.selectNodes(tableElement);
	//
	// for (final Iterator<Element> iter = results.iterator(); iter.hasNext();)
	// {
	// final Element fkElement = iter.next();
	// final ForeignKey fk = new ForeignKey();
	//
	// fk.setFktableSchem(fkElement.attributeValue(XmlMeta.FK_TABLE_SCHEMA));
	// fk.setFkName(fkElement.attributeValue(XmlMeta.FK_NAME));
	// fk.setPktableSchem(fkElement.attributeValue(XmlMeta.PK_TABLE_SCHEMA));
	// fk.setPktableName(fkElement.attributeValue(XmlMeta.PK_TABLE_NAME));
	// fk.setPktableCat(fkElement.attributeValue(XmlMeta.PK_TABLE_CAT));
	//
	// fk.setFktableName(fkElement.attributeValue(XmlMeta.FK_TABLE_NAME));
	// fk.setFktableCat(fkElement.attributeValue(XmlMeta.FK_TABLE_CAT));
	//
	// final String relationshipType =
	// fkElement.attributeValue(XmlMeta.RELATIONSHIP_TYPE);
	// final ArrayList<ForeignKeyColumn> columns = new
	// ArrayList<ForeignKeyColumn>();
	//
	// for (final Element colEl : (List<Element>) fkElement.elements()) {
	// final String columnName = colEl.attributeValue(XmlMeta.COLUMN_NAME);
	//
	// final ForeignKeyColumn fkc = new ForeignKeyColumn(colEl);
	// fk.setColumns(columns);
	// }
	// if (XmlMeta.IMPORTED_RELATION.equals(relationshipType)) {
	// imported.addForeignKey(fk);
	// } else if (XmlMeta.EXPORTED_RELATION.equals(relationshipType)) {
	// exported.addForeignKey(fk);
	// }
	//
	// }
	// table.setImportedKeys(imported);
	// table.setExportedKeys(exported);
	// }

	/**
	 * @todo convert dom4j in all functions
	 * @param output
	 * @param table
	 * @throws IOException
	 */
	private void writeAttributes() {

		for (final ColumnAttributes column : table.getColumns()) {

			final Element e = tableElement.addElement(XmlMeta.COLUMN);
			if (emitCamelCase) {
				e.addAttribute(XmlMeta.FIELD_NAME,
						StringHelper.attributeName(column.getColumnName()));
			}
			// String xmlType;
			//
			// try {
			// xmlType = column.getXmlType();
			// } catch (UnsupportedDataTypeException ude) {
			// xmlType = ude.getMessage();
			// }
			e.addAttribute(XmlMeta.ID,
					table.getSchemaName() + "." + table.getTableName() + "."
							+ column.getColumnName());
			e.addAttribute(XmlMeta.COLUMN_NAME, column.getColumnName());
			e.addAttribute(XmlMeta.DATA_TYPE, column.getColumnTypeName());
			e.addAttribute(XmlMeta.SQL_TYPE,
					Integer.toString(column.getColumnType()));
			if (column.getColumnSize() != null) {
				e.addAttribute(XmlMeta.COLUMN_SIZE, column.getColumnSize()
						.toString());
			}
			if (column.isNullable() != null) {
				e.addAttribute(XmlMeta.NULLABLE,
						column.isNullable() ? "not null" : null);
			}
			// logger.info(e.asXML());

		}
	}

	// private String getRelationshipPath(String type) {
	// String retval = XmlMeta.RELATION + "[@" + XmlMeta.RELATIONSHIP_TYPE +
	// "='" + type + "'])";
	// return retval;
	// }
	// <relation type="referenced-by" fk-table-schema="JAVAUTIL"
	// fk-name="UQ_DT_FK">
	// <key-map column-name="SCHEMA"/>
	// <key-map column-name="TABLE_NAME"/>
	// </relation>

	private void writePrimaryKeyColumns() {

		final PrimaryKey pk = table.getPrimaryKey();
		if (pk != null) {
			final Element pkEl = tableElement.addElement(XmlMeta.PRIMARY_KEY);

			pkEl.addAttribute(XmlMeta.CONSTRAINT_NAME, pk.getPrimaryKeyName());
			if (pk != null) {
				for (final String columnName : pk) {
					final Element colEl = pkEl.addElement(XmlMeta.COLUMN)
							.addAttribute(XmlMeta.COLUMN_NAME, columnName);
					if (emitCamelCase) {

						colEl.addAttribute(XmlMeta.ATTRIBUTE_NAME,
								StringHelper.attributeName(columnName));
					}

				}
			}
		} else {
			logger.warn("table " + table.getTableName() + " type "
					+ table.getTableType() + " has no primary key");
		}
	}

	private void writeReferences(ForeignKeys references, boolean imported,
			boolean emitSchemaName) {

		// ForeignKeys references = table.getImportedKeys();

		if (references != null) {
			for (final ForeignKey key : references.getValues()) {

				final Element relEl = tableElement.addElement(XmlMeta.RELATION);

				if (imported) {
					relEl.addAttribute(XmlMeta.RELATIONSHIP_TYPE,
							XmlMeta.IMPORTED_RELATION);
				} else {
					relEl.addAttribute(XmlMeta.RELATIONSHIP_TYPE,
							XmlMeta.EXPORTED_RELATION);
				}

				if (dense || imported) {

					relEl.addAttribute(XmlMeta.PK_TABLE_SCHEMA,
							key.getPktableSchem());
					relEl.addAttribute(XmlMeta.PK_TABLE_CAT,
							key.getPktableCat());
					relEl.addAttribute(XmlMeta.PK_TABLE_NAME,
							key.getPktableName());

				}

				if (dense || !imported) {

					relEl.addAttribute(XmlMeta.FK_TABLE_SCHEMA,
							key.getFktableSchem());
					relEl.addAttribute(XmlMeta.FK_TABLE_NAME,
							key.getFktableName());
					relEl.addAttribute(XmlMeta.FK_TABLE_CAT,
							key.getFktableCat());
				}

				relEl.addAttribute(XmlMeta.FK_NAME, key.getFkName());

				if (emitCamelCase) {
					relEl.addAttribute(XmlMeta.REL_ENTITY_NAME, StringHelper
							.attributeNameInitCap(key.getPktableName()));
				}

				for (final ForeignKeyColumn column : key.getColumns()) {

					final Element fieldEl = relEl.addElement(XmlMeta.KEY_MAP);

					if (emitCamelCase) {
						fieldEl.addAttribute(XmlMeta.FIELD_NAME, StringHelper
								.attributeName(column.getPkcolumnName()));
						fieldEl.addAttribute(XmlMeta.FIELD_NAME, StringHelper
								.attributeName(column.getFkcolumnName()));
					}
					fieldEl.addAttribute(XmlMeta.FK_COLUMN_NAME,
							column.getFkcolumnName());
					fieldEl.addAttribute(XmlMeta.PK_COLUMN_NAME,
							column.getPkcolumnName());
				}

			}

		}
	}
}
