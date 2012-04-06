/**
 * @(#) RecordFactory.java
 */
package com.dbexperts.oracle.trace;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.dbexperts.misc.EventHelper;
import com.dbexperts.oracle.trace.record.Exec;
import com.dbexperts.oracle.trace.record.Fetch;
import com.dbexperts.oracle.trace.record.Parse;
import com.dbexperts.oracle.trace.record.Parsing;
import com.dbexperts.oracle.trace.record.Record;
import com.dbexperts.oracle.trace.record.RecordType;
import com.dbexperts.oracle.trace.record.Stat;
import com.dbexperts.oracle.trace.record.Wait;
import com.dbexperts.oracle.trace.record.Xctend;

/**
 *
 */
public class TraceFileReader {
	private static EventHelper events = new EventHelper();
	private static final Integer LOG_PARSED_RECORD = Integer.valueOf(1);
	private static final Integer LOG_RAW_RECORD = Integer.valueOf(2);
	private final String					fileName;
	//
	private final ArrayList<String>      filePreamble = new ArrayList<String>();
	//
	private LineNumberReader		br;
	private final Logger					logger				= Logger.getLogger(this.getClass().getName());
	private boolean					endOfFile			= false;
	private String					line;
	//private boolean					needsRead;
	private final HashMap<String, String>	waitTypes			= new HashMap<String, String>();
	private boolean					logSkippedRecords	= true;
	private boolean					logSkippedTimestamp	= false;
    private boolean					logSkippedSeparator	= false;
    private Record				record				= null;
    // log events
//	private boolean logRead = false;
    private final boolean logParsingFound = false;
    private final boolean logSkipsInBind = false;
    private boolean logInitialSkips = false;
    private int getNextCount = 0;
    private int nonSkipCount = 0;
    private int bindCount = 0;
    private int xctendCount = 0;
    private int parsingCount = 0;
    private int parseCount;
    private int execCount;
    private int waitCount;
    private int fetchCount;
    private int statCount;
    private int endOfStatementCount;
    private int ignoredCount;
    static {
    	events.addEvent(LOG_PARSED_RECORD);
    	events.addEvent(LOG_RAW_RECORD);
    }

	public TraceFileReader(final String fileName) throws IOException {
		this.fileName = fileName;
		initResources();
	}

	public void dispose() throws IOException {
		br.close();
	}

	public Record getNext() throws IOException {
		getNextCount++;
		record = null;
		while (!endOfFile && record == null) {
			final RecordType recordType = RecordType.getRecordType(line);
			if (recordType != null) {
				switch (recordType) {
					case BIND:
						processBinds();
						bindCount++;
						break;
					case XCTEND:
						record = new Xctend(line,br.getLineNumber());
						xctendCount++;
						break;
					case PARSING:
						record = new Parsing(line,br.getLineNumber());
						final Parsing stmt = (Parsing) record;
						while ((line = readLine()) != null) {
							if (line.equals("END OF STMT")) {
								break;
							}
							stmt.addLine(line);
						}
						parsingCount++;
						break;
					case PARSE:
						record = new Parse(line,br.getLineNumber());
						parseCount++;
						break;
					case EXEC:
						record = new Exec(line,br.getLineNumber());
						execCount++;
						break;
					case WAIT:
						record = new Wait(line,br.getLineNumber());
						final Wait wait = (Wait) record;
						wait.setWaitType(getWaitType(wait.getWaitType()));
						wait.clean();
						waitCount++;
						break;
					case FETCH:
						record = new Fetch(line,br.getLineNumber());
						fetchCount++;
						break;
					case STAT:
						record = new Stat(line,br.getLineNumber());
						statCount++;
						break;
					case END_OF_STATEMENT:
						endOfStatementCount++;
						break;
					case IGNORED:
						ignoredCount++;
						break;
				}
			}
			if (record == null  && ! endOfFile) {
				logSkip();
			} else {
				nonSkipCount++;
				if (events.exists(LOG_PARSED_RECORD)) {
					logger.debug(record.toString());
				}
			}

			readLine();
		}
		if (record != null) {
		    logRecordType(record);
		}
		if (record == null) {
			logger.info("returning null record");
		} else 	if (events.exists(LOG_PARSED_RECORD)) {
			logger.debug(record.toString());
		}
		return record;
	}


	private String getWaitType(final String type) {
		String returnValue = waitTypes.get(type);
		if (returnValue == null) {
			returnValue = type;
			waitTypes.put(type, type);
		}
		return returnValue;
	}
	private void initResources() throws IOException {
		try {
			br = new LineNumberReader(new BufferedReader(new FileReader(fileName)));
			readPreamble();
		} catch (final FileNotFoundException fnfe) {
			logger.error("Cannot locate input file! " + fnfe.getMessage());
			throw fnfe;
		}
	}


	private void logRecordType(final Record rec) {
		if (rec.getRecordType() != null) {
			switch (rec.getRecordType()) {
			case PARSING:
				if (logParsingFound) {
					logger.info("found parsing");
				}
				break;
			default:
				break;
				case BIND:


			}
		}
	}
	private void logSkip() {
		if (logSkippedRecords) {
			if (nonSkipCount == 0 && ! logInitialSkips) {
				return;
			}
			if (line == null) {
				throw new IllegalStateException("line is null");
			}
			while (true) {
				if (line.startsWith("*** 20") && !logSkippedTimestamp) {
					break;
				}
				if (line.startsWith("=====") && !logSkippedSeparator) {
					break;
				}
				logger.warn("skipped " + line);
				break;
			}
		}
	}

	private String readLine() throws IOException {

		line = br.readLine();
		if (line == null) {
			endOfFile = true;
		} else {
			if (events.exists(LOG_PARSED_RECORD)) {
				logger.info("line # " + br.getLineNumber() + ": " + line);
			}
		}
		return line;
	}

	private void readPreamble() throws IOException {
		while (true) {
		line = br.readLine();
		if (line.startsWith("==")) {
			break;
		}
		logger.info("preamble " + line);
		filePreamble.add(line);
		}
	}

	void processBinds() throws IOException {
		final boolean saveLogSkipsInBind = logSkipsInBind;
		logSkippedRecords  = logSkipsInBind;
		while ((line = readLine()) != null) {
			if (line.charAt(0) != ' ') {
				break;
			}

		}
		logSkippedRecords = saveLogSkipsInBind;
		//logger.info("stopped skipping at " + br.getLineNumber() + " with text " + line);
	}
}
