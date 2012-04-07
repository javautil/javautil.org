package org.javautil.document.style;

/**
 * Parses a StyleDefinition into a Style. More specifically, this class takes a
 * StyleDefinition object, a string representation of a Style that is more human
 * readable, and translates it into a Style object, a java object that is
 * third-party api friendly. Generally a style must be transformed into another
 * object before it can be rendered in a document.
 * 
 * @author bcm
 * 
 */
public interface StyleParser {

	/**
	 * Performs the parsing of a style from a definition
	 * 
	 * @param definition
	 * @return
	 */
	public Style parse(StyleDefinition definition);

}
