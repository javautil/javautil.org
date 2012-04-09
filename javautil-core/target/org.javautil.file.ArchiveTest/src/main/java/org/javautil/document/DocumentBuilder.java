package org.javautil.document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.stream.StreamResult;

import org.javautil.dataset.render.Dom4jHtmlTable;
import org.javautil.dataset.render.typewriter.TypewriterContent;
import org.javautil.dataset.render.typewriter.TypewriterContents;
import org.javautil.document.renderer.IncrementalDocumentRenderer;
import org.javautil.document.renderer.IncrementalRendererRequest;
import org.springframework.beans.factory.InitializingBean;

public class DocumentBuilder implements InitializingBean {

	private String defaultDateFormat;

	private List<DocumentRegion> regions;

	private boolean deleteExistingOutputFile = false;

	private File outputFile;

	private String format;

	private Class<?> contentsTypeClass;

	private TypewriterContents<?, ?> contents;

	@Override
	@SuppressWarnings("unchecked")
	public void afterPropertiesSet() throws Exception {
		final IncrementalRendererRequest request = new IncrementalRendererRequest();

		// content type
		if (getContentsTypeClass() == null && getFormat() == null) {
			throw new IllegalStateException("either format or "
					+ "contentTypeClass must be specified");
		}
		if (getContentsTypeClass() != null && getFormat() != null) {
			throw new IllegalStateException("both format and "
					+ "contentTypeClass cannot be specified");
		}
		final String format = getFormat() == null ? "" : getFormat()
				.toLowerCase().trim();
		if (format.equals("html")) {
			contentsTypeClass = Dom4jHtmlTable.class;
		} else if (format.equals("xls")) {
			try {
				contentsTypeClass = Class
						.forName("org.javautil.poi.POIWorkbookRenderer"); //
			} catch (final Exception e) {
				// jjs this hurts my head. Who is doing this to me?
				throw new IllegalStateException("A suitable Excel "
						+ "TypewriterContent class was not found in the "
						+ "classpath; please include the javautil-poi "
						+ "jar file in the classpath", e);
			}
		}
		if (!TypewriterContents.class.isAssignableFrom(contentsTypeClass)) {
			throw new IllegalStateException("contentTypeClass "
					+ contentsTypeClass.getName()
					+ " does not implement TypewriterContent");
		}
		final Class<TypewriterContent> clazz = (Class<TypewriterContent>) getContentsTypeClass();
		contents = (TypewriterContents) clazz.newInstance();
		request.setContents(contents);

		// date format
		if (getDefaultDateFormat() != null) {
			request.setDateFormatter(new SimpleDateFormat(
					getDefaultDateFormat()));
		}

		// document
		if (getRegions() == null) {
			throw new IllegalStateException("regions is null");
		}
		final DocumentImpl document = new DocumentImpl(getRegions());
		request.setDocument(document);

		// output file and render
		final StreamResult streamResult = new StreamResult();
		if (outputFile.exists()) {
			if (!isDeleteExistingOutputFile()) {
				throw new IllegalStateException("outputFile "
						+ outputFile.getAbsolutePath() + " already exists; "
						+ "to delete this file automatically, specify the "
						+ "property deleteExistingOutputFile as true");
			} else {
				if (!outputFile.delete()) {
					throw new IllegalStateException("unable to delete "
							+ "outputFile " + outputFile.getAbsolutePath());
				}
			}
		}
		OutputStream os = null;
		try {
			if (!outputFile.createNewFile()) {
				throw new IllegalStateException("outputFile "
						+ outputFile.getAbsolutePath()
						+ " could not be created");
			}
			os = new FileOutputStream(outputFile);
			streamResult.setOutputStream(os);
			request.setStreamResult(streamResult);
			new IncrementalDocumentRenderer().render(request);
			os.flush();
		} catch (final Exception e) {
			if (os != null) {
				os.close();
			}
			if (outputFile.exists()) {
				try {
					outputFile.delete();
				} catch (final Exception x) {
				}
			}
			throw new RuntimeException(e);
		}
	}

	public List<DocumentRegion> getRegions() {
		return regions;
	}

	public void setRegion(final DocumentRegion region) {
		regions = new ArrayList<DocumentRegion>();
		regions.add(region);
	}

	public void setRegions(final List<DocumentRegion> regions) {
		this.regions = regions;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(final File outputFile) {
		this.outputFile = outputFile;
	}

	public String getDefaultDateFormat() {
		return defaultDateFormat;
	}

	public void setDefaultDateFormat(final String defaultDateFormat) {
		this.defaultDateFormat = defaultDateFormat;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(final String format) {
		this.format = format;
	}

	public boolean isDeleteExistingOutputFile() {
		return deleteExistingOutputFile;
	}

	public void setDeleteExistingOutputFile(
			final boolean deleteExistingOutputFile) {
		this.deleteExistingOutputFile = deleteExistingOutputFile;
	}

	public Class<?> getContentsTypeClass() {
		return contentsTypeClass;
	}

	public void setContentsTypeClass(final Class<?> contentsTypeClass) {
		this.contentsTypeClass = contentsTypeClass;
	}

	public TypewriterContents<?, ?> getContents() {
		return contents;
	}

}