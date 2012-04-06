package com.dbexperts.oracle.trace;

import org.dom4j.Element;

public interface CursorFormatter {
	public Element asElement();
	public String asString();

}