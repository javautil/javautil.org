package com.dbexperts.oracle.trace.record;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public enum RecordType {
    PARSING, PARSE, BIND, EXEC, WAIT, FETCH, STAT, END_OF_STATEMENT, IGNORED, TIMESTAMP, STARS, ERROR,
    SEPARATOR, XCTEND, APP_NAME, PARSE_ERROR, UNMAP;

    @SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(RecordType.class.getName());

    private static HashMap<String,RecordType> textMap = new HashMap<String,RecordType>();
    private static final Pattern pattern = Pattern.compile("(^[^#]*)");

    static {
    	textMap.put("PARSING IN CURSOR",PARSING);
    	textMap.put("EXEC",EXEC);
    	textMap.put("FETCH",FETCH);
        textMap.put("WAIT",WAIT);
		textMap.put("STAT",STAT);
	    textMap.put("BINDS",BIND);
	    textMap.put("ERROR",ERROR);
	    textMap.put("*** 20", TIMESTAMP);
    }

    public static RecordType getRecordType(final String record) {
    	if (record == null) {
    		throw new IllegalArgumentException("record is null");
    	}
    	final Matcher matcher = pattern.matcher(record);
    	RecordType returnValue = null;

    	if (record.startsWith("XCTEND")) {
    		returnValue = XCTEND;
    	} else if (record.startsWith("PARSE")) {
    		returnValue = PARSE;
    	} else
     	if (record.startsWith("***")) {
     		if (record.startsWith("*** 20")) {
        		returnValue = TIMESTAMP;
        	} else {
    		returnValue = STARS;
        	}
    	} else if (record.startsWith("===")) {
    		returnValue = SEPARATOR;
    	} else 	if (matcher.find() && matcher.groupCount() > 0) {
    		final String text = matcher.group(1).trim();
    		returnValue = textMap.get(text);
    	}
    	if (returnValue == null) {
    		returnValue = IGNORED;
    	}
    	return returnValue;
    }
}
