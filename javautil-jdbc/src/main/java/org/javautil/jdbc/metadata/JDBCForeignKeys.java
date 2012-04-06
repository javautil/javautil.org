package org.javautil.jdbc.metadata;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.javautil.jdbc.metadata.containers.ForeignKeys;

public class JDBCForeignKeys implements ForeignKeys, Iterable<ForeignKeyImpl> {
	private String schemaPattern;
	private String table;

	private final HashMap<String, ForeignKeyImpl> keys = new HashMap<String, ForeignKeyImpl>();

	public JDBCForeignKeys() {
	}

	/**
	 * TODO normalize replace with DAO and bean
	 */
	public JDBCForeignKeys(final DatabaseMetaData meta, final String catalog,
			final String schemaPattern, final String table,
			final boolean exportedKeys) throws SQLException {

		this.schemaPattern = schemaPattern;
		this.table = table;
		// boolean found = false;
		ResultSet rs;
		if (exportedKeys) {
			rs = meta.getExportedKeys(catalog, schemaPattern, table);
		} else {
			rs = meta.getImportedKeys(catalog, schemaPattern, table);
		}
		while (rs.next()) {
			// found = true;
			// System.out.println("found a foreign key");
			final ForeignKeyImpl fkey = new ForeignKeyImpl();
			fkey.setFkName(rs.getString("fk_name"));

			fkey.setPktableCat(rs.getString("pktable_cat"));
			fkey.setPktableSchem(rs.getString("pktable_schem"));
			fkey.setPktableName(rs.getString("pktable_name"));

			fkey.setFktableCat(rs.getString("fktable_cat"));
			fkey.setFktableSchem(rs.getString("fktable_schem"));
			fkey.setFktableName(rs.getString("fktable_name"));

			fkey.setUpdateRule(rs.getShort("update_rule"));
			fkey.setDeleteRule(rs.getShort("delete_rule"));
			fkey.setPkName(rs.getString("pk_name"));

			ForeignKey oldKey = keys.get(fkey.getId());
			if (oldKey == null) {
				oldKey = fkey;
				keys.put(fkey.getId(), fkey);
			}

			final ForeignKeyColumn col = new ForeignKeyColumn();
			col.setKeySeq(rs.getShort("key_seq"));
			col.setFkcolumnName(rs.getString("fkcolumn_name"));
			col.setPkcolumnName(rs.getString("pkcolumn_name"));

			oldKey.addColumn(col);

		}
		rs.close();

	}

	public void addForeignKey(final ForeignKeyImpl key) {
		keys.put(key.getId(), key);
	}

	/**
	 * @return Returns the schemaPattern.
	 */
	public String getSchemaPattern() {
		return schemaPattern;
	}

	/**
	 * @return Returns the table.
	 */
	public String getTable() {
		return table;
	}

	public Collection<ForeignKeyImpl> getValues() {
		return keys.values();
	}

	@Override
	public Iterator<ForeignKeyImpl> iterator() {
		return keys.values().iterator();
	}

	/**
	 * @param schemaPattern
	 *            The schemaPattern to set.
	 */
	public void setSchemaPattern(final String schemaPattern) {
		this.schemaPattern = schemaPattern;
	}

	/**
	 * @param table
	 *            The table to set.
	 */
	public void setTable(final String table) {
		this.table = table;
	}
}
