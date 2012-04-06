package org.javautil.jdbc.metadata.containers;

import java.util.Collection;
import java.util.Iterator;

import org.javautil.jdbc.metadata.ForeignKeyImpl;

public interface ForeignKeys {

	public void addForeignKey(final ForeignKeyImpl key);

	/**
	 * @return Returns the schemaPattern.
	 */
	public String getSchemaPattern();

	/**
	 * @return Returns the table.
	 */
	public String getTable();

	public Collection<ForeignKeyImpl> getValues();

	public Iterator<ForeignKeyImpl> iterator();

	/**
	 * @param schemaPattern
	 *            The schemaPattern to set.
	 */
	public void setSchemaPattern(final String schemaPattern);

	/**
	 * @param table
	 *            The table to set.
	 */
	public void setTable(final String table);

}
