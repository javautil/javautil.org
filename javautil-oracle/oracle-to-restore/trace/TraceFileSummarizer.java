/**
 * @(#) RecordFactory.java
 */
package com.dbexperts.oracle.trace;

import java.io.IOException;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.sql.Connection;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.dbexperts.oracle.trace.record.CursorEvent;
import com.dbexperts.oracle.trace.record.Record;
import com.dbexperts.oracle.trace.record.Xctend;
import com.dbexperts.ut.dao.UtXmlS;

/**
 * @todo put fileName in trace File
 */
public class TraceFileSummarizer {
	private final TraceFileReader	reader;
	private Record		record	= null;
	private final Logger			logger	= Logger.getLogger(this.getClass().getName());
	private int				recordCount;
	private final Cursors			cursors	= new Cursors();
	private long			startTime;
	private long			endTime;
	private double			elapsedSeconds;
	public TraceFileSummarizer(final String fileName) throws IOException {
		reader = new TraceFileReader(fileName);
		logger.info("processing file " + fileName);
	}

	/**
	 * Takes each statement and associates it with a cursor.
	 *
	 * Loops through the input file add adds each statment to
	 * the corresponding cursor, which is created if necessary.
	 * @throws IOException
	 */
	public void associateStatementsToCursors() throws IOException {
		startTime = System.nanoTime();
		while ((record = reader.getNext()) != null) {
			if (record instanceof CursorEvent) {
				cursors.addEvent((CursorEvent) record);
			} else {
				if (! (record instanceof Xctend )) {
			    	logger.info("not a cursor event " + record.getLineNumber() + record.getClass().getName());
				}
			}
			recordCount++;
		}
		logger.info("records read " + recordCount);
		endTime = System.nanoTime();
		elapsedSeconds = (endTime - startTime) / 1e9;
		logger.info("elapsed seconds " + elapsedSeconds);

	}

	public void persistDocument(final Connection conn, final Document doc) throws IOException   {
		final PipedOutputStream pos = new PipedOutputStream();

		XMLWriter writer;
		try {
			writer = new XMLWriter(pos, OutputFormat.createPrettyPrint() );
		}
		catch (final UnsupportedEncodingException e) {
			throw new IOException(e.getMessage());
		}
	    final UtXmlS ux = new UtXmlS(conn,pos);
	    final Thread t = new Thread(ux);
	    t.start();
	    writer.write(doc);
	    writer.close();
	    Exception x = null;
	    if (ux.isDone()) {
	        x = ux.getException();
	    }
	    if (x != null) {
	    	logger.error(x.getMessage());
	    	x.printStackTrace();
//	    	throw x;
	    }
	}

//
//	public Document getAggregatesDocument() {
//		return cursors.getAggregatesDocument();
//	}
//
//	public void writeAggregatesDocument(String fileName) throws IOException {
//		cursors.writeAggregatesDocument(fileName);
//	}
//
//	public void verboseAggregateDump() {
//		cursors.verboseAggregateDump();
//	}

	public void process() throws IOException {
		associateStatementsToCursors();
//		verboseAggregateDump();
//		cursors.group();
//		cursors.dumpGroups();
//		cursors.dumpAggregates();
	}
	/**
	 * Save a bunch of memory by converting each element to xml and writing to the
	 * writer rather than build a dom.
	 * @param w
	 * @throws IOException
	 */
	public void writeAggregates(final Writer w) throws IOException {
		final String elementName = "aggregates";
		w.write("<");
		w.write(elementName);
		w.write(">");
	//ArrayList<AggregateCursor> aggs = cursors. getAggregateCursorsById()
	for (final AggregateCursor cursor : cursors. getAggregateCursorsById()) {
		final CursorXmlFormatter cxf = new CursorXmlFormatter(cursor);
		final Element el = cxf.asElement();
		final String xml = el.asXML();
		w.write(xml);
	}
	w.write("</");
	w.write(elementName);
	w.write(">");
}
}
