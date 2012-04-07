package org.javautil.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class ElementHelper {

	public static String getPretty(final Element el) {
		final OutputFormat of = OutputFormat.createPrettyPrint();
		final StringWriter sw = new StringWriter();
		final XMLWriter xw = new XMLWriter(sw, of);
		try {
			xw.write(el);
		} catch (final IOException e) {
			throw new IllegalStateException(e);
		}
		return sw.toString();
	}

	public static String getPretty(final String xml) throws DocumentException {
		final Document doc = DocumentHelper.parseText(xml);
		final Element root = doc.getRootElement();
		return getPretty(root);
	}

	/**
	 * @param element
	 * @param attributeName
	 * @param attributeValue
	 */
	public static void setAttribute(final Element element,
			final String attributeName, final String attributeValue) {
		final Attribute attribute = element.attribute(attributeName);
		if (attribute != null) {
			attribute.setValue(attributeValue);
		} else {
			element.addAttribute(attributeName, attributeValue);
		}
	}

	/**
	 * Useful to set the value of attributes that are composed of a map of
	 * attributes name and value pairs. The delimiter character appears between
	 * name and value pairs. The nameValueSeparator character appears between
	 * the name and its value.
	 * 
	 * @param element
	 * @param attributeName
	 * @param delimiter
	 * @param nameValueSeparator
	 * @param values
	 */
	public static void setAttribute(final Element element,
			final String attributeName, final char delimiter,
			final char nameValueSeparator, final Map<String, String> values) {
		if (delimiter == nameValueSeparator) {
			throw new IllegalStateException("unhandled case: delimiter"
					+ " == nameValueSeparator");
		}
		final Attribute attribute = element.attribute(attributeName);
		if (attribute != null) {
			final Map<String, String> newValues = new LinkedHashMap<String, String>();
			final String[] nameAndValuePairs = attribute.getText().split(
					"" + delimiter);
			newValues.putAll(values);
			for (final String nameAndValueUnseparated : nameAndValuePairs) {
				if (nameAndValueUnseparated.trim().length() != 0) {
					final String[] nameAndValue = nameAndValueUnseparated
							.split("" + nameValueSeparator);
					if (nameAndValue.length != 2) {
						throw new IllegalStateException(
								"unexpected name/value pair: "
										+ nameAndValueUnseparated);
					}
					final String name = nameAndValue[0].trim();
					final String value = nameAndValue[1].trim();
					if (!values.containsKey(name)) {
						newValues.put(name, value);
					}
				}
			}
			final String attributeValue = asAttributeValue(newValues,
					delimiter, nameValueSeparator);
			attribute.setText(attributeValue);
		} else {
			final String attributeValue = asAttributeValue(values, delimiter,
					nameValueSeparator);
			element.addAttribute(attributeName, attributeValue);
		}
	}

	/**
	 * @param element
	 * @param attributeName
	 * @param delimiter
	 * @param nameValueSeparator
	 * @param name
	 * @param value
	 */
	public static void setAttribute(final Element element,
			final String attributeName, final char delimiter,
			final char nameValueSeparator, final String name, final String value) {
		final Map<String, String> values = new LinkedHashMap<String, String>();
		values.put(name, value);
		setAttribute(element, attributeName, delimiter, nameValueSeparator,
				values);
	}

	/**
	 * @param values
	 * @param delimiter
	 * @param nameValueSeparator
	 * @return
	 */
	public static String asAttributeValue(final Map<String, String> values,
			final char delimiter, final char nameValueSeparator) {
		final StringBuilder sb = new StringBuilder();
		for (final String name : values.keySet()) {
			final String value = values.get(name);
			sb.append(name);
			sb.append("" + nameValueSeparator);
			sb.append(value);
			sb.append(delimiter);
		}
		return sb.toString();
	}

	/**
	 * @param tagName
	 * @param attributes
	 * @return
	 */
	public static Element createElement(final String tagName,
			final Map<String, String> attributes) {
		final Element element = DocumentHelper.createElement(tagName);
		for (final String attributeName : attributes.keySet()) {
			final String attributeValue = attributes.get(attributeName);
			element.addAttribute(attributeName, attributeValue);
		}
		return element;
	}

}
