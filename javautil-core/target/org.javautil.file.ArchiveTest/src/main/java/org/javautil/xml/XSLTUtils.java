package org.javautil.xml;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.LocatorImpl;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * 
 * @author bcm
 * 
 */
public class XSLTUtils {

	public static final String XSLT_TRANSFORMER_FACTORY = "javax.xml.transform.TransformerFactory";

	public static final String SAXON_TRANSFORMER_FACTORY = "net.sf.saxon.TransformerFactoryImpl";

	private static LocatorImpl emptyDocumentLocator = new LocatorImpl() {
		@Override
		public String getSystemId() {
			return null;
		}
	};

	private XSLTUtils() {
		// prevent instantiation
	}

	public static void transform(final StreamSource xml, final InputSource xsl,
			final StreamResult result) {
		try {
			transformInternal(null, xml, xsl, null, result);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void transform(final URIResolver xslResolver,
			final StreamSource xml, final InputSource xsl,
			final StreamResult result) {
		try {
			transformInternal(xslResolver, xml, xsl, null, result);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void transform(final StreamSource xml, final InputSource xsl,
			final Map<String, Object> parameters, final StreamResult result) {
		try {
			transformInternal(null, xml, xsl, parameters, result);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void transform(final URIResolver xslResolver,
			final StreamSource xml, final InputSource xsl,
			final Map<String, Object> parameters, final StreamResult result) {
		try {
			transformInternal(xslResolver, xml, xsl, parameters, result);
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void transformInternal(final URIResolver xslResolver,
			final StreamSource xml, final InputSource xsl,
			final Map<String, Object> parameters, final StreamResult result)
			throws IOException, ParserConfigurationException, SAXException,
			TransformerConfigurationException, TransformerException {
		final TransformerFactory tfactory = TransformerFactory.newInstance();
		tfactory.setURIResolver(xslResolver);

		// Does this factory support SAX features?
		if (tfactory.getFeature(SAXSource.FEATURE)) {
			// if so, we can safely cast
			final SAXTransformerFactory stfactory = ((SAXTransformerFactory) tfactory);

			// create a Templates ContentHandler to handle parsing of the
			// stylesheet
			final javax.xml.transform.sax.TemplatesHandler templatesHandler = stfactory
					.newTemplatesHandler();
			templatesHandler.setDocumentLocator(emptyDocumentLocator);

			final XMLFilter filter = new XMLFilterImpl();
			filter.setParent(makeXMLReader());
			filter.setContentHandler(templatesHandler);

			// parse the stylesheet
			templatesHandler.setSystemId(xsl.getSystemId());
			filter.parse(xsl);

			// set xslt parameters
			final Transformer autobot = templatesHandler.getTemplates()
					.newTransformer();
			if (parameters != null) {
				final Iterator<String> keys = parameters.keySet().iterator();
				while (keys.hasNext()) {
					final String name = keys.next();
					final Object value = parameters.get(name);
					autobot.setParameter(name, value);
				}
			}

			// set saxon parameters
			if (parameters != null) {
				final Iterator<String> keys = parameters.keySet().iterator();
				while (keys.hasNext()) {
					String name = keys.next();
					if (name.startsWith("saxon-")) {
						final String value = parameters.get(name).toString();
						name = name.replaceFirst("saxon\\-", "");
						autobot.setOutputProperty(name, value);
					}
				}
			}

			// do the transform
			// logger.debug("SAX resolving systemIDs relative to: " +
			// templatesHandler.getSystemId());
			autobot.transform(xml, result);

		} else {
			throw new IllegalStateException(
					"Factory doesn't implement SAXTransformerFactory");
		}
	}

	private static XMLReader makeXMLReader()
			throws ParserConfigurationException, SAXException {
		final SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		final XMLReader reader = factory.newSAXParser().getXMLReader();
		return reader;
	}

	public static void setUseSaxonTransformFactory() {
		if (System.getProperty(XSLT_TRANSFORMER_FACTORY) == null) {
			System.setProperty(XSLT_TRANSFORMER_FACTORY,
					SAXON_TRANSFORMER_FACTORY);
		} else {
			throw new IllegalStateException(XSLT_TRANSFORMER_FACTORY
					+ " is already set");
		}
	}

}
