package org.javautil.collections;

import org.javautil.text.AsString;

public class SimpleTreeNode implements TreeNode<SimpleTreeNode> {
	private Integer nodeNbr;

	private Integer parentNodeNbr;

	private String description;

	public SimpleTreeNode(final Integer nodeNbr, final Integer parentNodeNbr,
			final String description) {
		super();
		this.nodeNbr = nodeNbr;
		this.parentNodeNbr = parentNodeNbr;
		this.description = description;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setNodeNbr(final Integer nodeNbr) {
		this.nodeNbr = nodeNbr;
	}

	public void setParentNodeNbr(final Integer parentNodeNbr) {
		this.parentNodeNbr = parentNodeNbr;
	}

	@Override
	public Integer getNodeNbr() {

		return nodeNbr;
	}

	@Override
	public Integer getParentNodeNbr() {

		return parentNodeNbr;
	}

	@Override
	public String toString() {
		final AsString as = new AsString();
		return as.toString(this);
	}

	@Override
	public String format() {
		return TreeNodeFormatter.format(this);
	}

}
