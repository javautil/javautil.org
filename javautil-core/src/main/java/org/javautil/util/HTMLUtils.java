package org.javautil.util;

import java.io.InputStream;
import java.util.Map;

import org.dom4j.Element;
import org.javautil.io.IOUtils;
import org.javautil.xml.ElementHelper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class HTMLUtils {

	public static void setStyleAttribute(final Element element,
			final Map<String, String> values) {
		ElementHelper.setAttribute(element, "style", ';', ':', values);
	}

	public static void setStyleAttribute(final Element element,
			final String name, final String value) {
		ElementHelper.setAttribute(element, "style", ';', ':', name, value);
	}

	public static String asStylesheetTag(final Resource resource) {
		final StringBuilder s = new StringBuilder();
		s.append("<!-- " + resource.getFilename() + " -->\n");
		if (ClassPathResource.class.isAssignableFrom(resource.getClass())
				|| FileSystemResource.class.isAssignableFrom(resource
						.getClass())) {
			s.append("<style type=\"text/css\">\n");
			InputStream input = null;
			try {
				input = resource.getInputStream();
				s.append(IOUtils.readStringFromStream(input));
				s.append("\n");
			} catch (final Exception e) {
				throw new RuntimeException("error while reading resource: "
						+ resource.getDescription(), e);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (final Exception e) {
						throw new RuntimeException(
								"error while close resource: "
										+ resource.getDescription(), e);
					}
				}
			}
			s.append("</style>");
		} else {
			s.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
			try {
				s.append(resource.getURI().toString());
			} catch (final Exception e) {
				throw new RuntimeException("error while getting resource uri: "
						+ resource.getDescription(), e);
			}
			s.append("/>");
		}
		return s.toString();
	}

	public static String asJavascriptTag(final Resource resource) {
		final StringBuilder s = new StringBuilder();
		s.append("<!-- " + resource.getFilename() + " -->\n");
		if (ClassPathResource.class.isAssignableFrom(resource.getClass())
				|| FileSystemResource.class.isAssignableFrom(resource
						.getClass())) {
			s.append("<script type=\"text/javascript\">\n");
			InputStream input = null;
			try {
				input = resource.getInputStream();
				s.append(IOUtils.readStringFromStream(input));
				s.append("\n");
			} catch (final Exception e) {
				throw new RuntimeException("error while reading resource: "
						+ resource.getDescription(), e);
			} finally {
				if (input != null) {
					try {
						input.close();
					} catch (final Exception e) {
						throw new RuntimeException(
								"error while close resource: "
										+ resource.getDescription(), e);
					}
				}
			}
			s.append("</script>");
		} else {
			s.append("<script type=\"text/javascript\" src=\"");
			try {
				s.append(resource.getURI().toString());
			} catch (final Exception e) {
				throw new RuntimeException("error while getting resource uri: "
						+ resource.getDescription(), e);
			}
			s.append("\"></script>");
		}
		return s.toString();
	}

}