package com.dbexperts.oracle.cache;

import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import oracle.jdbc.dcn.RowChangeDescription;
import oracle.jdbc.dcn.TableChangeDescription;
import oracle.jdbc.dcn.DatabaseChangeEvent.EventType;
import oracle.jdbc.dcn.RowChangeDescription.RowOperation;
import oracle.sql.ROWID;

import org.apache.log4j.Logger;

class ChangeLogger implements DatabaseChangeListener {

	private final Logger logger = Logger.getLogger(this.getClass());
	private final Object eventSubscriber;
	private static final String newline = System.getProperty("line.separator");
	ChangeLogger(final Object dem) {
		eventSubscriber = dem;
	}

	public void onDatabaseChangeNotification(final DatabaseChangeEvent e) {
		System.out.println(e.toString());
//		logger.info(e.toString());

		final String connectionInformation = e.getConnectionInformation();
		final String databaseName = e.getDatabaseName();
		final EventType type = e.getEventType();
		final String typeName = type.name();
		final TableChangeDescription[] tcd = e.getTableChangeDescription();
		for (int i = 0 ; i < tcd.length; i++) {
			final TableChangeDescription d = tcd[i];
			final String tableName = d.getTableName();
			final RowChangeDescription[] rcd =  d.getRowChangeDescription();
			for (int j = 0; j < rcd.length; j++) {
				final RowChangeDescription changeDescription = rcd[i];
				final ROWID rowid = changeDescription.getRowid();
				final RowOperation ro = changeDescription.getRowOperation();
				final int opCode = ro.getCode();
				final String operation = ro.toString();
				final StringBuilder b = new StringBuilder();
				b.append("ROWID " + rowid.stringValue() + newline);
				b.append("connectionInformation " + connectionInformation + newline);
				b.append("databaseName " + databaseName + newline);
				b.append("typeName " + typeName + newline);
				b.append("tableName " + tableName + newline);
				b.append("rowChangeDescription " + changeDescription + newline);
				b.append("opCode " + opCode + newline);
				b.append("operation " + operation);
				System.out.println(b.toString());
			}
		}
		//e.getConnectionInformation();
		synchronized (eventSubscriber) {
			eventSubscriber.notify();
		}
	}
}