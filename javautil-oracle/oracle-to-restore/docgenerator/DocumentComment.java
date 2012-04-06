package com.dbexperts.oracle.docgenerator;

public class DocumentComment {
	private String leadIn;
	private String directive;
	private String whiteSpace;
	private String payload;
	private int lineNumber;
	private String rawText;
	
	public DocumentComment(int lineNumber,String rawText,String leadIn, String whiteSpace, String directive, String payload) {
		super();
		this.lineNumber = lineNumber;
		this.rawText = rawText;
		this.leadIn = leadIn;
		this.whiteSpace = whiteSpace;
		this.directive = directive;
		this.payload = payload;
	}
	
	/**
	 * @return the directive
	 */
	public String getDirective() {
		return directive;
	}

	/**
	 * @return the leadIn
	 */
	public String getLeadIn() {
		return leadIn;
	}
	/**
	 * @return the lineNumber
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	/**
	 * @return the payload
	 */
	public String getPayload() {
		return payload;
	}

	/**
	 * @return the whiteSpace
	 */
	public String getWhiteSpace() {
		return whiteSpace;
	}
	
	public String getPayloadWithWhitespace() {
		return whiteSpace + payload;
	}

	/**
	 * Constructs a <code>String</code> with all attributes
	 * in name = value format.
	 *
	 * @return a <code>String</code> representation 
	 * of this object.
	 */
	public String toString()
	{
	    final String TAB = System.getProperty("line.separator");
	   
	    String retValue  = "DocumentComment ( "
	        + super.toString() + TAB
	        + "lineNumber = " + this.lineNumber  + TAB
	        + "directive = " + ((this.directive == null) ? "null" : ("'" + this.directive + "'")) + TAB
	        + "leadIn = " + ((this.leadIn == null) ? "null" : ("'" + this.leadIn + "'")) + TAB
	        + "payload = " + ((this.payload == null) ? "null" : ("'" + this.payload + "'")) + TAB
	        + " )";
	 
	    return retValue;
	}
}
