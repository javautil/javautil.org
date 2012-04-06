package org.javautil.jdbc.metadata.renderer;

import org.javautil.jdbc.metadata.Index;
import org.javautil.jdbc.metadata.IndexColumn;
import org.javautil.jdbc.metadata.NameEscaper;

public class IndexToSQL {
	private static final String newline = System.getProperty("line.separator");
	private static final String statementEnd = ";" + newline + newline;
	private boolean prependSchema = false;
	private final SQLGenerator gen = new SQLGenerator();

	public String toSQLString(final Index index) {
		final StringBuilder b = new StringBuilder();
		final String create = gen.getReserved("create index ");
		b.append(create);

		if (prependSchema) {
			final String schemaName = gen.getAttribute(index.getSchemaName());
			b.append(schemaName);
			b.append(".");
		}
		// String indexName =
		// gen.escape(gen.getAttribute(index.getIndexName()));
		b.append(gen.getEscapedPaddedAttribute(index.getIndexName()));
		// b.append(indexName);
		b.append(gen.getReserved(" on "));
		// b.append(newline);
		// b.append(tab);
		b.append(gen.getAttribute(index.getTableName()));
		b.append(" (");

		// Collection<IndexColumn> columns = index.getColumnsById().values();

		boolean first = true;
		for (final IndexColumn col : index.getColumnsById().values()) {
			if (!first) {
				b.append(", ");
			}
			b.append(NameEscaper.escape(col.getColumnName()));
			// b.append(" ");
			if ("DESC".equals(col.getAscOrDesc().toUpperCase())) {
				b.append(gen.getReserved("desc "));
			}
			first = false;

		}
		b.append(")");
		// todo need column comments
		return b.toString();
	}

	public boolean isPrependSchema() {
		return prependSchema;
	}

	public void setPrependSchema(final boolean prependSchema) {
		this.prependSchema = prependSchema;
	}

	public static String getStatementEnd() {
		return statementEnd;
	}
}
