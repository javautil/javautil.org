package com.dbexperts.oracle.docgenerator;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.dbexperts.text.TabExpander;

public class DocumentCommentParser {
	/**
	 * Group 1 - everything before the '--%'
	 * Group 2 - All spaces and tabs before '@'
	 * Group 3 - '@directive'
	 * Group 4 - 'directive'
	 * Group 5 - the rest of the line
	 */
	private static final Pattern commentPattern = Pattern.compile("(.*)--%([ \t]*)(@([^ ]*))?(.*)");
	private final Logger logger = Logger.getLogger(getClass());
	private LineNumberReader reader;
	private static final String newline = System.getProperty("line.separator");
	
	public DocumentCommentParser(Reader in) {
		if (in == null) {
			throw new IllegalArgumentException("in is null");
		}
		reader = new LineNumberReader(in);
	}


	
	/**
	 * 
	 * @return the next document comment or null if end of file
	 * @throws IOException
	 */
	public DocumentComment getDocumentComment() throws IOException {
		String raw = null;
		String line = null;
		DocumentComment dc = null;
		while ((raw = reader.readLine()) != null ) {
			line =  TabExpander.expand(raw,8);
			logger.debug("line# " + reader.getLineNumber() + " '" + line + "'" +  newline);
			int lineNumber = reader.getLineNumber();
//			if (line.indexOf("@") > -1) {
//				logger.debug("line # " + lineNumber + ": " + line);
//			}
			Matcher m = commentPattern.matcher(line);
			if (m.matches()) {
				String leadIn = m.group(1);
				String whiteSpace = m.group(2);
				String directive = m.group(4);
				String payload = m.group(5);
				dc = new DocumentComment(lineNumber,line,leadIn, whiteSpace, directive, payload);
				
				logger.debug("line: " + lineNumber + " " +   directive + " " + line);
				break;
			}
			logger.debug("line: " + lineNumber + " not a comment: " + line);
		}
		return dc;

	}
	
	
}
