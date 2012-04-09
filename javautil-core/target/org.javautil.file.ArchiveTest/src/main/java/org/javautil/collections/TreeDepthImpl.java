package org.javautil.collections;

// TODO javadoc What the hell is this?
public class TreeDepthImpl implements TreeDepth {

	private int depth;

	private String name;

	public TreeDepthImpl(final int depth, final String name) {
		super();
		this.depth = depth;
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.collections.TreeDepth#getDepth()
	 */
	@Override
	public int getDepth() {
		return depth;
	}

	public void setDepth(final int depth) {
		this.depth = depth;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javautil.collections.TreeDepth#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

}
