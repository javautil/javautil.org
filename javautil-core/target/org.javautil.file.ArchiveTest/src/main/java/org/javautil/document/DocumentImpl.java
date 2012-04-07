package org.javautil.document;

import java.util.Arrays;
import java.util.List;

// TODO jjs why
public class DocumentImpl implements Document {

	private List<DocumentRegion> regions;

	public DocumentImpl(final DocumentRegion... documentRegions) {
		regions = Arrays.asList(documentRegions);
	}

	public DocumentImpl(final List<DocumentRegion> documentRegions) {
		regions = documentRegions;
	}

	@Override
	public List<DocumentRegion> getRegions() {
		return regions;
	}

	public void setRegions(final List<DocumentRegion> regions) {
		this.regions = regions;
	}
}
