package org.javautil.document.crosstab;

import java.util.List;

/**
 * When the column identifier is compound
 * 
 * @author jjs@dbexperts.com
 * 
 */
public interface CompoundCrosstabColumns {
	public List<String> getCellIdentifiers();

	public List<String> getColumnIdentifiers();

	public List<String> getRowIdentifiers();

	// /**
	// * Checks for internal consistency, everything required, no dupes.
	// */
	// public void validate();

}