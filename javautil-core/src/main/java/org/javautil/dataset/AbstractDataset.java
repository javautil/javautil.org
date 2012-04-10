package org.javautil.dataset;

/**
 * 
 * @author jjs@dbexperts.com
 * 
 */
public abstract class AbstractDataset implements Dataset {
	private String name;

	private DatasetMetadata metadata;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String _name) {
		this.name = _name;
	}

	@Override
	public DatasetMetadata getMetadata() {
		return metadata;
	}

	protected void setMetadata(final DatasetMetadata metadata) {
		this.metadata = metadata;
	}

	public boolean hasFooterValues() {
		return false;
	}

	public Object[] getFooterValues() {
		throw new UnsupportedOperationException();
	}

}
