package org.javautil.xml;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * 
 * @author bcm
 * 
 */
public abstract class JAXBUtils {
	/**
	 * Marshall this object into XML
	 * 
	 * @param jaxb
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static String marshall(final JAXBElement<? extends Object> jaxb)
			throws JAXBException, IOException {
		final JAXBContext jc = JAXBContext.newInstance(jaxb.getValue()
				.getClass().getPackage().getName());
		final Marshaller marshaller = jc.createMarshaller();
		final StringWriter sw = new StringWriter();
		marshaller.marshal(jaxb, sw);
		sw.close();
		return sw.toString();
	}

	/**
	 * Unmarshall the xml to a JAXB object
	 * 
	 * @param jaxbPackage
	 * @param jaxb
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	public static String marshall(final String jaxbPackage,
			final JAXBElement<? extends Object> jaxb) throws JAXBException,
			IOException {
		final JAXBContext jc = JAXBContext.newInstance(jaxbPackage);
		final Marshaller marshaller = jc.createMarshaller();
		final StringWriter sw = new StringWriter();
		marshaller.marshal(jaxb, sw);
		sw.close();
		final String xml = sw.toString();
		return xml;
	}
}
