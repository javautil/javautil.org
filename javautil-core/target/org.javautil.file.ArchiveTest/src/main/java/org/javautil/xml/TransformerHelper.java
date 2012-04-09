package org.javautil.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public class TransformerHelper {
	TransformerFactory tFactory = TransformerFactory.newInstance();

	public static String getXml(final StreamSource xmlSource,
			final StreamSource xslSource) throws TransformerException {
		final TransformerFactory tFactory = TransformerFactory.newInstance();

		final StringWriter writer = new StringWriter();
		final StreamResult output = new StreamResult(writer);
		final Templates templates = tFactory.newTemplates(xslSource);
		final Transformer transformer = templates.newTransformer();

		transformer.transform(xmlSource, output);
		return writer.toString();

	}

	public static String getXml(final StreamSource xmlSource,
			final StreamSource xslSource,
			final HashMap<String, String> parameters)
			throws TransformerException {
		final TransformerFactory tFactory = TransformerFactory.newInstance();

		final StringWriter writer = new StringWriter();
		final StreamResult output = new StreamResult(writer);
		final Templates templates = tFactory.newTemplates(xslSource);
		final Transformer transformer = templates.newTransformer();
		for (final Entry<String, String> param : parameters.entrySet()) {
			final String name = param.getKey();
			final String value = param.getValue();
			transformer.setParameter(name, value);
		}

		transformer.transform(xmlSource, output);
		return writer.toString();

	}

	// this method takes as input a XML source, a XSL source, and returns the
	// output of the transformation to the servlet output stream
	public void process(final StreamSource xmlSource,
			final StreamSource xslSource, final HttpServletResponse response)
			throws TransformerException, IOException {
		final Templates templates = tFactory.newTemplates(xslSource);
		final Transformer transformer = templates.newTransformer();
		transformer.transform(xmlSource,
				new StreamResult(response.getOutputStream()));
		// response
		// .
		// getOutputStream()));

	}

	public void process(final StreamSource xmlSource,
			final StreamSource xslSource, final HttpServletResponse response,
			final HashMap<String, String> parameters)
			throws TransformerException, IOException {
		final Templates templates = tFactory.newTemplates(xslSource);
		final Transformer transformer = templates.newTransformer();
		for (final Entry<String, String> param : parameters.entrySet()) {
			final String name = param.getKey();
			final String value = param.getValue();
			transformer.setParameter(name, value);
		}
		transformer.transform(xmlSource,
				new StreamResult(response.getOutputStream()));
		// response
		// .
		// getOutputStream()));

	}

	public String process(final String xmlFileName, final String xslFileName)
			throws TransformerException {

		final StreamSource xml = new StreamSource(new File(xmlFileName));
		final StreamSource xsl = new StreamSource(new File(xslFileName));
		final StringWriter writer = new StringWriter();
		final StreamResult output = new StreamResult(writer);
		final Templates templates = tFactory.newTemplates(xsl);
		final Transformer transformer = templates.newTransformer();
		transformer.transform(xml, output);
		return writer.toString();

	}

	public void transform(final File xmlFile, final File xslFile,
			final File outputFile) throws TransformerException, IOException {
		// xml
		final StreamSource xmlSource = new StreamSource(xmlFile);
		// xsl

		final StreamSource xslSource = new StreamSource(xslFile);
		//
		final FileWriter writer = new FileWriter(outputFile);
		final StreamResult outputStream = new StreamResult(writer);
		// transform
		final Templates templates = tFactory.newTemplates(xslSource);
		final Transformer transformer = templates.newTransformer();
		transformer.transform(xmlSource, outputStream);
	}

	public void transform(final File xmlFile, final String xslResourceName,
			final File outputFile) throws TransformerException, IOException {
		// xml
		final StreamSource xmlSource = new StreamSource(xmlFile);
		// xsl
		final URL xslURL = getClass().getClassLoader().getResource(
				xslResourceName);
		final InputStream xslStream = xslURL.openStream();
		final StreamSource xslSource = new StreamSource(xslStream);
		//
		final FileWriter writer = new FileWriter(outputFile);
		final StreamResult outputStream = new StreamResult(writer);
		// transform
		final Templates templates = tFactory.newTemplates(xslSource);
		final Transformer transformer = templates.newTransformer();
		transformer.transform(xmlSource, outputStream);
	}
}
